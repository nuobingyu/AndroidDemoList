package com.example.huadongchongtu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Scroller;

public class MyViewPager extends ViewPager{

    private Scroller mScroller;

    private int mLastXIntercept = 0;
    private int mLastYIntercept = 0;

    public MyViewPager(@NonNull Context context) {
        super(context);
    }

    public MyViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
       boolean intercepted = false;

       int x = (int)event.getX();
       int y = (int)event.getY();
       switch(event.getAction()){
           case MotionEvent.ACTION_DOWN:
               intercepted = false;
               if(!mScroller.isFinished()){
                   mScroller.abortAnimation();
                   intercepted = true;
               }
               break;
           case MotionEvent.ACTION_MOVE:
               int deltaX = x- mLastXIntercept;
               int deltaY = y- mLastYIntercept;
               if(Math.abs(deltaX) > Math.abs(deltaY)){
                   intercepted = true;
               }else{
                   intercepted = false;
               }
               break;
           case MotionEvent.ACTION_UP:
               intercepted = false;
               break;
           default:
               break;
       }
       Log.i("tag","intercepted="+intercepted);
       mLastXIntercept = x;
       mLastYIntercept = y;

        return super.onInterceptHoverEvent(event);
    }
}
