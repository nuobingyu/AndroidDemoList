package com.example.huadongchongtu;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

import android.os.Handler;

import java.util.NoSuchElementException;

public class StickyLayout extends LinearLayout{

    private View mHeader;
    private View mContent;

    private int mTouchSlop;
    //上次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;

    //上从滑动的坐标（onInterceptTouchEvent）
    private int mLastInterceptX = 0;
    private int mLastInterceptY = 0;

    private OnGiveUpTouchEventListener mGiveUpTouchEventListener;

    //header状态
    public static  final int STATUS_EXPANDED = 1;
    public static  final int STATUS_COLLAPSED = 2;
    private int mStatus = STATUS_EXPANDED;

    //Header的高度 单位: px
    private int mHeaderHeight;
    private int mOriginalHeaderHeight;
    private static final int TAN = 2;

    private boolean  mDisallowInterceptTouchEventOnHeader = true;
    private boolean  mIsSticky = true;
    private boolean mInitDataSucceed = false;

    public interface OnGiveUpTouchEventListener {
        boolean giveUpTouchEvent(MotionEvent event);
    }

    public void setOnGiveUpTouchEventListener(OnGiveUpTouchEventListener l) {
        mGiveUpTouchEventListener = l;
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if(hasWindowFocus && (mHeader == null || mContent == null)){
            initData();
        }
    }

    public void initData(){
        int headerId = getResources().getIdentifier("sticky_header","id",getContext().getPackageName());
        int contentId = getResources().getIdentifier("sticky_content","id",getContext().getPackageName());
        if(headerId != 0 &&contentId !=0){
            mHeader = findViewById(headerId);
            mContent = findViewById(contentId);
            mOriginalHeaderHeight = mHeader.getMeasuredHeight();
            mHeaderHeight = mOriginalHeaderHeight;
            Log.i("头部信息mOri",mOriginalHeaderHeight+"");
            mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
            if(mHeaderHeight >0){
                mInitDataSucceed = true;
            }
            Log.i("init","mTouchTop = "+mTouchSlop +" mHeaderHeight = "+mHeaderHeight);
        }else{
            throw new NoSuchElementException("Did your view with \"sticky_header\" or \"sticky_content\" exists");
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int intercepted = 0;
        int x= (int) ev.getX();
        int y = (int) ev.getY();

        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i("onInterceptTouchEvent","down");
                mLastInterceptX = x;
                mLastInterceptY = y;
                mLastX = x;
                mLastY = y;
                intercepted = 0;

            case MotionEvent.ACTION_MOVE:
                Log.i("onInterceptTouchEvent","move");
                int deltaX = x - mLastInterceptX; //拦截手指移动x距离
                int deltaY = y - mLastInterceptY; //          y

                if(mDisallowInterceptTouchEventOnHeader && y <= getHeaderHeight()){
                    intercepted = 0;
                }else if(Math.abs(deltaX) >= Math.abs(deltaY)){
                    intercepted = 0;
                }else if(mStatus == STATUS_EXPANDED && deltaY <= -mTouchSlop){
                    intercepted = 1;
                }else if(mGiveUpTouchEventListener != null){
                    if(mGiveUpTouchEventListener.giveUpTouchEvent(ev) && deltaY >= mTouchSlop)
                        intercepted = 1;
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.i("onInterceptTouchEvent","up");
                intercepted = 0;
                mLastInterceptX = mLastInterceptY = 0;
                break;
            default:
                break;
        }
        Log.i("onInterceptTouchEvent","是否拦截？"+intercepted);
        return intercepted != 0 && mIsSticky;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!mIsSticky){
            return true;
        }
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("onTouchEvent","down");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("onTouchEvent","move");
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;

                Log.i("tag", "mHeaderHeight=" + mHeaderHeight + " deltaY=" + deltaY);
                mHeaderHeight += deltaY;
                setHeaderHeight(mHeaderHeight);
//                LayoutParams layoutParams = new LayoutParams(mHeader.getLayoutParams().width,mHeaderHeight);
//                mHeader.setLayoutParams(layoutParams);
                break;
            case MotionEvent.ACTION_UP:
                Log.i("onTouchEvent","up");
                int destHeight = 0;
                if (mHeaderHeight <= mOriginalHeaderHeight * 0.5) {
                    destHeight = 0;
                    mStatus = STATUS_COLLAPSED;
                } else {
                    destHeight = mOriginalHeaderHeight;
                    mStatus = STATUS_EXPANDED;
                }
                this.smoothSetHeaderHeight( mHeaderHeight,destHeight, 500);
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }


    public void smoothSetHeaderHeight(final int from, final int to, long duration) {
        smoothSetHeaderHeight(from, to, duration, false);
    }


    //使用延时策略
    public void smoothSetHeaderHeight(final int from, final int to, long duration, final boolean modifyOriginalHeaderHeight) {

        final int frameCount = (int) (duration / 1000f * 30) + 1;
        final float partation = (to - from) / (float) frameCount;
        new Thread() {//"Thread#smoothSetHeaderHeight"
            @Override
            public void run() {
                for (int i = 0; i < frameCount; i++) {
                    final int height;
                    if (i == frameCount - 1) {
                        height = to;
                    } else {
                        height = (int) (from + partation * i);
                    }
                    post(new Runnable() {
                        public void run() {
                            setHeaderHeight(height);
                        }
                    });
                    try {
                        sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (modifyOriginalHeaderHeight) {
                    setOriginalHeaderHeight(to);
                }
            }
        }.start();
    }

    public StickyLayout(Context context) {
        super(context);
        mStatus = STATUS_EXPANDED;
    }

    public StickyLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mStatus = STATUS_EXPANDED;
    }

    public StickyLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mStatus = STATUS_EXPANDED;
    }

    public void setOriginalHeaderHeight(int originalHeaderHeight) {
        mOriginalHeaderHeight = originalHeaderHeight;
    }

    public void setHeaderHeight(int height, boolean modifyOriginalHeaderHeight) {

        if (modifyOriginalHeaderHeight) {
            setOriginalHeaderHeight(height);
        }
        setHeaderHeight(height);
    }

    public void setHeaderHeight(int height) {
        if (!mInitDataSucceed) {
            initData( );
        }
        if (height <= 0) {
            height = 0;
        }else if (height > mOriginalHeaderHeight) {
            height = mOriginalHeaderHeight;
        }
        if (height == 0) {
            mStatus = STATUS_COLLAPSED;
        } else {
            mStatus = STATUS_EXPANDED;
        }
        if (mHeader != null && mHeader.getLayoutParams() != null) {
            mHeader.getLayoutParams().height = height;
            mHeaderHeight = height;
            mHeader.requestLayout();
        }
        //Log.i("height","---------------height-------------"+mHeader.getLayoutParams().height);
    }
    public int getHeaderHeight() {
        return mHeaderHeight;
    }

    public void setSticky(boolean isSticky) {
        mIsSticky = isSticky;
    }

    public void requestDisallowInterceptTouchEventOnHeader(boolean disallowIntercept) {
        mDisallowInterceptTouchEventOnHeader = disallowIntercept;
    }
}
