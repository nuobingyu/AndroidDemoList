package com.example.bsrdemo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class MyView extends View {

    private Paint mPaint;
    private Path mPath = new Path();
    private float mPreX,mPreY;
    private int mItemWaveLength = 1000;
    private int dx;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public MyView(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(event.getX() , event.getY());
                mPreX = event.getX();
                mPreY = event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                float endX = (mPreX+event.getX())/2;
                float endY = (mPreY+event.getY())/2;
                mPath.quadTo(mPreX,mPreY,endX,endY);
                mPreX = event.getX();
                mPreY =event.getY();
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        Paint paint = new Paint();
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(5);
//        paint.setColor(Color.GREEN);
//
//        Path path = new Path();
//        path.moveTo(100,300);
//        path.quadTo(200,200,300,300);
//        path.quadTo(400,400,500,300);
//
//        path.rQuadTo(100,-100,200,0);
//        path.rQuadTo(100,100,200,0);
//
//        canvas.drawPath(path,paint);

        mPath.reset();
        int originY = 300;
        int halfWaveLen = mItemWaveLength/2;
        mPath.moveTo(-mItemWaveLength +dx,originY);
        for (int i = -mItemWaveLength;i<=getWidth()+mItemWaveLength;i+=mItemWaveLength){
            mPath.rQuadTo(halfWaveLen/2,-100,halfWaveLen,0);
            mPath.rQuadTo(halfWaveLen/2,100,halfWaveLen,0);
        }
        mPath.lineTo(getWidth(),getHeight());
        mPath.lineTo(0,getHeight());
        mPath.close();
        canvas.drawPath(mPath,mPaint);
    }

    public void reset(){
        mPath.reset();
        invalidate();
    }


    public void startAnim(){
        final ValueAnimator animator = ValueAnimator.ofInt(0,mItemWaveLength);
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (int)animation.getAnimatedValue();
                postInvalidate();

            }
        });
        animator.start();
    }
}
