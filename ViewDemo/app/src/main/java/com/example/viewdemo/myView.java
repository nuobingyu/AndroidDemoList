package com.example.viewdemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

public class myView extends android.support.v7.widget.AppCompatTextView  {


    private int mLastX = 0;
    private int mLastY = 0;
    private Scroller scroller = new Scroller(getContext());

    public myView(Context context) {
        super(context);
    }

    public myView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public myView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX( );
        int y = (int) event.getRawY( );

        switch (event.getAction( )) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                Log.i("TAG", "move,deltaX:" + deltaX + "deltaY:" + deltaY);
                int translationX = (int)getTranslationX()+deltaX;
                int translationY = (int)getTranslationY()+deltaY;
//                setTranslationX(translationX);
//                setTranslationY(translationY);


                scroller.forceFinished(true);
                scroller.startScroll(getScrollX() ,0,500-getScrollX(),0,10000);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;

        return true;
    }

    @Override
    public void computeScroll() {
        if(scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX() ,scroller.getCurrY());
            postInvalidate();
        }
    }
}
