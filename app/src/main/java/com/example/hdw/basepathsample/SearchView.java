package com.example.hdw.basepathsample;

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

/**
 * Created by Administrator on 2017/05/24 0024.
 */

public class SearchView extends View {
    private Path mDstPath;
    private PathMeasure mPathMeasure;
    private Paint mPaint;
    private Path mPath;
    private final int mRadius=50;
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
        mPathMeasure = new PathMeasure();

        mPath.addCircle(0,0,mRadius, Path.Direction.CW);
        mPath.moveTo(mRadius,0);
        mPath.rLineTo(mRadius,0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(canvas.getWidth()/2,canvas.getHeight()/2);
        canvas.rotate(45);

        mPathMeasure.setPath(mPath,true);
        mPathMeasure.getSegment(100,mPathMeasure.getLength(),mDstPath,true);
        mPathMeasure.nextContour();
        mPathMeasure.getSegment(0,mPathMeasure.getLength(),mDstPath,true);
        canvas.drawPath(mDstPath,mPaint);
    }
}
