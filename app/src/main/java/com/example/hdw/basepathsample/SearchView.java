package com.example.hdw.basepathsample;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;

/**
 * Created by Administrator on 2017/05/24 0024.
 */

public class SearchView extends View {
    private Path mDstPath;
    private PathMeasure mPathMeasure;
    private Paint mPaint;
    private Path mPath;
    private final int mRadius=50;
    private float mCircleStartD;
    private float mCircleStopD;
    private int mCircleLen;
    private float mLineStartD;
    private float mLineStopD;
    private int mLineLen;
    private int mRotate;
    private int mSingleCircleTime;
    public SearchView(Context context) {
        super(context);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPath = new Path();
        mDstPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

        mPath.addCircle(0,0,mRadius, Path.Direction.CW);
        mPath.moveTo(mRadius,0);
        mPath.rLineTo(mRadius,0);
        mPathMeasure = new PathMeasure(mPath,true);
        mCircleLen = (int) mPathMeasure.getLength();
        mPathMeasure.nextContour();
        mLineLen = (int) mPathMeasure.getLength();
        mCircleStopD = mCircleLen;
        mLineStopD = mLineLen;
        mRotate = 45;
        mSingleCircleTime = 1000;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(canvas.getWidth()/2,canvas.getHeight()/2);
        canvas.rotate(mRotate);

        mPathMeasure.setPath(mPath,true);


        mPathMeasure.getSegment(mCircleStartD,mCircleStopD,mDstPath,true);
        mPathMeasure.nextContour();


        mPathMeasure.getSegment(mLineStartD,mLineStopD,mDstPath,true);
        canvas.drawPath(mDstPath,mPaint);
        mDstPath.reset();
    }

    public void doAnimation(){
        ValueAnimator oneAnimator =ValueAnimator.ofInt(0,360)
                .setDuration(mSingleCircleTime);
        oneAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int angle = (int) animation.getAnimatedValue();
                mCircleStartD = mCircleLen* angle*1.0f/360;
                mCircleStopD = mCircleLen;
                mLineStartD = 0;
                mLineStopD = mLineLen;
                invalidate();
            }
        });

        ValueAnimator twoAnimator =ValueAnimator.ofInt(0,mLineLen)
                .setDuration((long) (mSingleCircleTime*(mLineLen*1.0f/mCircleLen)));
        twoAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int len = (int) animation.getAnimatedValue();
                mCircleStartD = 0;
                mCircleStopD = len;
                mLineStartD = 0;
                mLineStopD = mLineLen - len;
                invalidate();
            }
        });

        ValueAnimator threeAnimator =ValueAnimator.ofInt(mRotate,360+mRotate)
                .setDuration(mSingleCircleTime);
        threeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int rotate = (int) animation.getAnimatedValue();
                mRotate=rotate;
                invalidate();
            }
        });
        threeAnimator.setRepeatCount(2);

        ValueAnimator fourAnimator =ValueAnimator.ofInt(mLineLen,mCircleLen)
                .setDuration(mSingleCircleTime);
        fourAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int len = (int) animation.getAnimatedValue();
                mCircleStartD = 0;
                mCircleStopD = len;
                invalidate();
            }
        });

        ValueAnimator fiveAnimator =ValueAnimator.ofInt(0,mLineLen)
                .setDuration(mSingleCircleTime);
        fiveAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int len = (int) animation.getAnimatedValue();
                mLineStartD = 0;
                mLineStopD = len;
                invalidate();
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(oneAnimator,twoAnimator,threeAnimator,fourAnimator,fiveAnimator);
        animatorSet.start();
    }
}
