package com.example.xuanpiaodemo;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;

import java.util.ArrayList;
import java.util.List;

public class MyView extends View implements View.OnTouchListener,ScaleGestureDetector.OnScaleGestureListener{

    private int mViewH,mViewW;//大图宽高
    private int mSeatH,mSeatW;//座位宽高
    private int sViewH,sViewW;//缩略图宽高
    public static List<Seat> selectList = new ArrayList<>();

    private int spacing ;//水平距离
    private int varSpacing; //竖直距离
    private int row; //行数
    private int column; //列数
    private Bitmap seatBitmap;//可选
    private Bitmap checkedSeatBitmap;//已选
    private Bitmap seatSoldBitmap;//卖出
    float overviewScale = 5.0f;//缩略图比例
    float SCALE_MAX =1.5f;
    float SCALE_INIT = 1.0f;
    private float price = 28; //电影票单价

    private int SELECTED_MAX = 30; //做大选择座位数量
    private Matrix matrix;
    private float[] matrixValues = new float[9];
    private int myViewTop ;
    private int initMarginLeft = 80;
    private int initMarginTop = 120;

    private static final int SEAT_TYPE_NOT_SELECTED =1 ;
    private static final int SEAT_TYPE_SELECTED = 2;
    private static final int SEAT_TYPE_SOLD = 3;
    private static final int NO_SEAT = 4;
    private Context mContext;
    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetector gestureDetector ;

    private int[][] data = new int[][]{
            {0,1,1,1,1,1,1,1,1,1,0},
            {2,2,2,2,2,2,2,2,2,2,2},
            {0,3,3,3,3,3,3,3,3,3,0},
            {0,1,1,1,1,1,1,1,1,1,0},
            {2,2,2,2,2,2,2,2,2,2,2},
            {3,3,3,3,3,3,3,3,3,3,3},
            {1,1,1,1,1,1,1,1,1,1,1},
            {0,1,2,2,2,2,2,2,2,1,0},
            {0,1,3,3,3,3,3,3,3,1,0},
            {1,1,1,1,1,1,1,1,1,1,1}};

    public MyView(Context context) {
        this(context,null) ;
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0) ;
    }

    @SuppressLint("ClickableViewAccessibility")
    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
        this.setOnTouchListener(this);
        scaleGestureDetector = new ScaleGestureDetector(context , this);
        gestureDetector = new GestureDetector(context ,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                isCanDrag = false;
                handleClickEvent(e);
                isCanDrag = true;
                return true;
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e("onMeasure"," ");
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        getLayoutParams().height =  mHeight;
        getLayoutParams().width = mWidth;
        Log.i("onMeasure",mWidth +" "+mHeight );
        super.onMeasure(widthMeasureSpec , heightMeasureSpec );
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.e("onLayout"," ");
        super.onLayout(changed, left, top, right, bottom);
        myViewTop = top;
        mViewH = bottom -top;
        mViewW = right -left;
        mSeatW = seatBitmap.getWidth();
        mSeatH = seatBitmap.getHeight();
        sViewH = (int)(mViewH /overviewScale);
        sViewW = (int)(mViewW /overviewScale);

        Log.i("onLayout","height ="+getLayoutParams().height);
    }


    int mHeight,mWidth;

    BitmapFactory.Options options = new BitmapFactory.Options();

    private void init(){
        matrix = new Matrix();
        row = data.length; //初始化行
        column = data[0].length; //初始化列
        spacing = dip2xp(mContext,5); //初始化水平空隙
        varSpacing = dip2xp(mContext,10) ; //初始化竖直空隙
        mViewW = dip2xp(mContext ,80) ;

        options.inSampleSize = 2;
        seatBitmap = BitmapFactory.decodeResource(getResources() ,R.drawable.seat_gray,options ).copy(Bitmap.Config.ARGB_8888,true ) ; //普通座位
        checkedSeatBitmap = BitmapFactory.decodeResource(getResources() ,R.drawable.seat_green,options ).copy(Bitmap.Config.ARGB_8888,true ); //选中座位
        seatSoldBitmap = BitmapFactory.decodeResource(getResources() ,R.drawable.seat_sold,options ).copy(Bitmap.Config.ARGB_8888,true ); // 未选中座位

        indextViewTop = (int) (marginTop + seatBitmap.getHeight()*getScale()/2); //索引条与上的距离
    }

    public static boolean isClick = false;

    //工具方法dp->px
    public  int dip2xp(Context context ,float dpValue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue *scale +0.5f);
    }

  // Bitmap bg_bitmap;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"ResourceAsColor", "SetTextI18n", "DrawAllocation"})
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("OnDraw"," ");

        drawSeat(canvas);
        canvas.save();
        drawScreen(canvas);
        drawScreenText(canvas);
        drawIndexView(canvas);
        drawOverView(canvas);
        canvas.restore();


        isCanDrag = true;
        if(selectList != null && selectList.size() !=0){
            MainActivity.order.setBackgroundResource(R.color.colorAccent) ;
            MainActivity.order.setText(MyView.selectList.size()*price+"元 确认支付");
            isClick = true;
        }else{
            isClick = false;
            MainActivity.order.setBackgroundResource(R.color.colorOrder_bg_unClick) ;
            MainActivity.order.setText("请先选座");
            MainActivity.order.setTextColor(R.color.colorOrder);
        }
    }

    //画座位
    public void drawSeat(Canvas canvas) {
        selectList.clear();
        for(int i = 0 ; i< row; i++)
        {
            for(int j = 0 ; j< column; j++)
            {
                int left = (j+1)* seatBitmap.getWidth() +j * spacing;
                int top = (i+1)* seatBitmap.getHeight() +i * varSpacing;
                left = (int)(left*getScale())+marginLeft;
                top = (int)(top*getScale())+marginTop;
                Paint paint = new Paint();

                switch(data[i][j]){
                    case SEAT_TYPE_NOT_SELECTED:
                        canvas.drawBitmap(seatBitmap ,left ,top ,paint);
                        break;
                    case SEAT_TYPE_SELECTED:
                        selectList.add(new Seat(SEAT_TYPE_SELECTED ,i,j,left,top ));
                        canvas.drawBitmap(checkedSeatBitmap,left ,top ,paint );
                        paint.setColor(Color.WHITE);
                        paint.setStrokeWidth(2);
                        int size = 24;
                        paint.setTextSize(size);
                        canvas.drawText((i+1)+"排",left+10,top+25,paint);
                        canvas.drawText((j+1)+"列",left+10,top+45,paint );
                        break;
                    case SEAT_TYPE_SOLD:
                        canvas.drawBitmap(seatSoldBitmap ,left ,top ,paint );
                        break;
                }
            }
        }
    }

    //画缩略图
    @SuppressLint("ResourceAsColor")
    public void drawOverView(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(R.color.colorS_bg);
        paint.setAlpha(8);
        paint.setStyle(Paint.Style.FILL);
        int width = (int) ((column*mSeatW + (column -1)*spacing)/overviewScale);
        int height = (int) ((column*mSeatH +(row -1)*varSpacing)/overviewScale);
        Rect rect = new Rect(mViewW-width-10 ,10, mViewW -10, height);
        canvas.drawRect(rect ,paint );
        Paint paint1 = new Paint();
        paint1.setColor(R.color.colorText);
        paint1.setStyle(Paint.Style.FILL);
        for(int i = 0 ; i< row ; i++)
            for(int j = 0; j< column ; j++)
            {
                int left =(int) ((j* mSeatW +j * spacing)/overviewScale);
                int top = (int)((i* mSeatH +i * varSpacing)/overviewScale);
                int right = left + (int)(mSeatW/overviewScale);
                int bottom = top + (int)(mSeatH/overviewScale);
                Rect rect1 = new Rect(rect.left+left ,rect.top +top ,rect.left+right ,rect.top+ bottom );
                canvas.drawRect(rect1 ,paint1);
            }
         drawOverRect(canvas );
    }

    //画缩略图选择区域
    public void drawOverRect(Canvas canvas){
        Paint p = new Paint();
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(1);
        p.setColor(Color.RED);
        int width = (int) ((column*mSeatW + (column -1)*spacing)/overviewScale/getScale());
        int height = (int) ((column*mSeatH +(row -1)*varSpacing)/overviewScale/getScale());
        int top = 10 ;
        int left = mViewW-width-10;
        int right = mViewW -10;
        int bottom =height;
        int dx = (int) ((initMarginLeft - marginLeft)/overviewScale/getScale());
        int dy = (int) ((initMarginTop -marginTop) /overviewScale /getScale());

        Rect rect = new Rect(left,top,right ,bottom);
        canvas.drawRect(rect ,p);
    }

    int marginTop = 80 + 40;
    int marginLeft = 70 +10;
    private int DScreen = 0;
    //画屏幕
    @SuppressLint("ResourceAsColor")
    public void drawScreen(Canvas canvas){
        Path path = new Path();
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.colorS_bg));
        paint.setStyle(Paint.Style.FILL);

        path.moveTo((mViewW/2-300)+DScreen,20*getScale());

        path.lineTo((mViewW /2-300)+DScreen,20*getScale());
        path.lineTo((mViewW/2+250+DScreen),20*getScale());
        path.lineTo((mViewW/2+200+DScreen),80*getScale());
        path.lineTo((mViewW/2-250+DScreen),80*getScale());
        path.close();
        canvas.drawPath(path,paint);
    }


    //绘制文字（屏幕名称）
    @SuppressLint("ResourceAsColor")
    public void drawScreenText(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(R.color.colorOrder);
        paint.setStrokeWidth(10);
        int size = 30;
        paint.setTextSize(size);

        canvas.drawText("7号厅3D荧屏",mViewW/2-100+DScreen ,60,paint);
    }

    private int indextViewLeft = 20;
    private int indextViewTop;
    //绘制索引
    @SuppressLint("ResourceAsColor")
    public void drawIndexView(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(R.color.colorS_bg);
        paint.setStyle(Paint.Style.FILL);
        RectF rectF = new RectF();
        rectF.top = marginTop + seatBitmap.getHeight()*getScale()/2;
        indextViewTop = (int) rectF.top;
        rectF.bottom =(int) ((column * seatBitmap.getHeight() + (column-1)*varSpacing)*getScale()) +marginTop;
        rectF.left = indextViewLeft;
        rectF.right = 70;
        canvas.drawRoundRect(rectF,20 ,20,paint);

        //绘制数字
        paint.setColor(R.color.colorText);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(30);
        paint.setStrokeWidth(1);
        for(int i = 0 ; i< row ;i++){
            float x = rectF.left +10;
            float y = rectF.top + (i*varSpacing +(i+1)*seatBitmap.getHeight())*getScale();
            canvas.drawText(""+(i+1),x,y,paint );
        }
    }

    //处理单击事件
    public void handleClickEvent(MotionEvent e){
        float x = e.getX();
        float y = e.getY();

        for(int i = 0 ; i< row  ; i++){
            for(int j = 0 ; j< column  ; j++){
                float left = (j+1)* seatBitmap.getWidth() +j * spacing;
                float top = (i+1)* seatBitmap.getHeight() +i * varSpacing;
                left = left*getScale()+marginLeft;
                top = top*getScale() +marginTop;
                float minX = left ;
                float minY = top ;
                float maxX = left +seatBitmap.getWidth()*getScale();
                float maxY = top+seatBitmap.getHeight()*getScale();
                if(x >=minX && x<=maxX &&y >=minY && y<=maxY && data[i][j] ==SEAT_TYPE_NOT_SELECTED){
                    addASeat(SEAT_TYPE_SELECTED ,i,j);
                    return ;
                }
                if(x >=minX && x<=maxX &&y >=minY && y<=maxY && data[i][j] ==SEAT_TYPE_SELECTED){
                    data[i][j] = SEAT_TYPE_NOT_SELECTED;
                    postInvalidate();
                }
            }
        }

    }

    //添加选择座位
    private void addASeat(int type, int i, int j){
        data[i][j] = type;
        postInvalidate();
    }

    //自动滑动到原位置
    public void AutoPosition(){
        isAutoDraging = true;
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1,100);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener( ) {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fract = animation.getAnimatedFraction();
                Log.i("回滑中","------------------------------------------------");
                marginLeft +=(initMarginLeft -marginLeft)*fract;
                DScreen -=DScreen*fract;
                marginTop +=(initMarginTop - marginTop)*fract;
                indextViewTop +=(initMarginTop - marginTop)*fract;
                invalidate();
            }
        });
        valueAnimator.setDuration(500);
        valueAnimator.start();
//        marginLeft = initMarginLeft;
//        marginTop = initMarginTop;
//        DScreen = 0;
        invalidate();
        isAutoDraging = false;
    }

    public int getTransY(){
        matrix.getValues(matrixValues);
        return (int) matrixValues[Matrix.MTRANS_Y];
    }

    public int getTransX(){
        matrix.getValues(matrixValues);
        return (int) matrixValues[Matrix.MTRANS_X];
    }

    //移动实现
    public void pingMuAnimTrans(final int dx ,final int dy){
        if(isAutoDraging)
            return;
        Log.i("pingMuTrans","在滑动");
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1 ,100) ;
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener( ) {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                    marginLeft +=dx*0.1;
                    marginTop +=dy*0.1;
                    indextViewTop +=dy*0.1;
                    DScreen +=dx*0.1;
//                    Log.i("Anim",dx+","+dy);
                    invalidate();
            }
        });
        valueAnimator.setDuration(12);
        valueAnimator.start();
    }

    public float getScale(){
        matrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }

    float mLastX = 0,mLastY= 0;
    int lastPointerCount = 0;
    boolean isCanDrag = true,isScale = false;
    private boolean isAutoDraging = false;

    @Override
    public boolean onTouch(View v, final MotionEvent event) {
       scaleGestureDetector.onTouchEvent(event);
       gestureDetector.onTouchEvent(event);

        float x = 0;
        float y = 0;
        int pointerCount = event.getPointerCount();
        for(int i = 0 ; i< pointerCount ; i++){
            x += event.getX(i);
            y += event.getY(i);
        }
        x = x/ pointerCount;
        y = y/ pointerCount;

        if(pointerCount != lastPointerCount){
            isCanDrag = false;
            mLastX = 0;
            mLastY = 0;
        }
        lastPointerCount = pointerCount;

        switch(event.getAction()){
            case MotionEvent.ACTION_MOVE:

                if(mLastY == 0 && mLastX ==0){
                    mLastX = x;
                    mLastY = y;
                    break;
                }
                int dx = (int)(x - mLastX);
                int dy = (int)(y - mLastY);
                Log.i("MOVE上次","mLastX="+(int)mLastX+",mLastY="+(int)mLastY);
                Log.i("MOVE这次","x="+(int)x+",y="+(int)y);
                Log.i("dx,dy",dx+","+dy);
                isCanDrag = isCanDrag(dx,dy);
                if(isCanDrag && !isAutoDraging ) {
                    pingMuAnimTrans(dx,dy);
                    invalidate();
                }
                if(getScale() >1.0 && ((initMarginTop -marginTop)<-200*getScale()||(initMarginTop -marginTop)>500*getScale()
                        || initMarginLeft -marginLeft>500*getScale()|| initMarginLeft -marginLeft<-300*getScale()))
                    AutoPosition();
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mLastX = 0;
                mLastY = 0;
                if(getScale() <=1.0)
                    AutoPosition();
                break;
        }
        return true;
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return true;
    }

    //判断是否是一次滑动
    public boolean isCanDrag(float dx , float dy){
        if(!isScale){
            if(Math.sqrt((dx * dx) + (dy * dy)) >= 8){
                return true;
            }
        }
        return false;
    }

    //缩放实现
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scale = getScale();
        float scaleFactor = detector.getScaleFactor();

        Log.i("onScale","缩放中");
        if((scale < SCALE_MAX && scaleFactor >1.0f) ||(scaleFactor < 1.0f && scale > SCALE_INIT))
        {
            if(scaleFactor * scale < SCALE_INIT)
            {
                scaleFactor = SCALE_INIT / scale;

            }
            if(scaleFactor * scale > SCALE_MAX)
            {
                scaleFactor = SCALE_MAX /scale;
            }

           matrix.postScale(scaleFactor ,scaleFactor ,detector.getFocusX() ,detector.getFocusY());
           invalidate();
        }
        return true;
    }
    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        isScale = true;
        return true;
    }
    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        isScale = false;

    }
}
