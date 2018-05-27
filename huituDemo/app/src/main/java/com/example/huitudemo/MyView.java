package com.example.huitudemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {

    Context mContext;

    Paint paint = new Paint();

    public MyView(Context context) {
        super(context);
        mContext = context;
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5);
        paint.setShadowLayer(10,15,15,Color.RED);

        canvas.drawRGB(255,255,255);
        canvas.drawARGB(255,255,255,255);
        canvas.drawCircle(190,200,150,paint);
        //不抗锯齿的圆
//        paint.setAntiAlias(false);
//        canvas.drawCircle(getWidth()/2,getHeight()/2,100, paint);
        //1条线
        canvas.drawLine(0,0,getWidth() ,getHeight(),paint);
        //多条线
        float[] lins={getWidth(),0 ,getWidth()-100,20 ,getWidth()-200,40, getWidth()-300,60};
        canvas.drawLines(lins,paint);
        //1个点
        float[] ponits={0,0,100,100,200,200,300,300};
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(20);
        canvas.drawPoint(500,500,paint);
        //画一堆点
        canvas.drawPoints(ponits ,paint);
        //画矩形
        RectF rectF = new RectF(400,400,200,200);
        paint.setStrokeWidth(5);
        //canvas.drawRect(rectF ,paint);
        //画矩形
        canvas.drawRoundRect(rectF ,100,30,paint);
        paint.setStyle(Paint.Style.STROKE);
        //画椭圆
        canvas.drawOval(new RectF(500,200,300,100),paint);
        //画弧
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        RectF rectF1 = new RectF(400,10,700,300);
        canvas.drawArc(rectF1 ,0,90,true ,paint);

    }
}
