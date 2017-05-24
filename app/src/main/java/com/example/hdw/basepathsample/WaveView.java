package com.example.hdw.basepathsample;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by Administrator on 2017/05/24 0024.
 * 水波纹效果
 */

public class WaveView extends View {
    private final int WAVE_LENGTH = 1000;
    private Paint mPaint;
    private Path mPath;
    private int mDeltaX;

    public WaveView(Context context) {
        super(context);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float halfWaveLength = WAVE_LENGTH / 2;
        mPath.reset();
        mPath.moveTo(-WAVE_LENGTH + mDeltaX, 500);
        for (int i = -WAVE_LENGTH; i <= getWidth() + WAVE_LENGTH; i += WAVE_LENGTH) {
            int WAVE_HEIGHT = 200;
            mPath.rQuadTo(halfWaveLength / 2, WAVE_HEIGHT, halfWaveLength, 0);
            mPath.rQuadTo(halfWaveLength / 2, -WAVE_HEIGHT, halfWaveLength, 0);
        }
        mPath.lineTo(getWidth(), getHeight());
        mPath.lineTo(0, getHeight());
        mPath.close();

        canvas.drawPath(mPath, mPaint);
    }

    public void doAnimation() {
        mDeltaX = 0;
        ValueAnimator animator = ValueAnimator.ofInt(0, Integer.MAX_VALUE)
                .setDuration((Integer.MAX_VALUE / WAVE_LENGTH) * 500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDeltaX = (int) animation.getAnimatedValue() % WAVE_LENGTH;
                invalidate();
            }
        });
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();
    }
}
