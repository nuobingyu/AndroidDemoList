package com.example.huitudemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class MyView1 extends View {

    private Context mContext;

    public MyView1(Context context) {
        super(context);
        mContext = context;
    }

    public MyView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        canvas.drawLine(20,20,100,100,paint);
        Path path = new Path();
        paint.setColor(Color.BLUE);
        path.moveTo(20,10);
        path.lineTo(100,30);
        path.lineTo(20,30);
        path.close();
//        path.moveTo(10, 10); //设定起始点
//        path.lineTo(10, 100);//第一条直线的终点，也是第二条直线的起点
//        path.lineTo(300, 100);//画第二条直线
//        path.lineTo(500, 100);//第三条直线
//        path.close();//闭环
        canvas.drawPath(path,paint);
        float[] radi={5,5,5,10,10,5,10,10};
        Path CCWRectpath = new Path();
        RectF rect1 =  new RectF(50, 50, 240, 200);
       // CCWRectpath.addRect(rect1, Path.Direction.CCW);
        CCWRectpath.addRoundRect(rect1,radi, Path.Direction.CCW);
//第二个顺向生成
       // Path CWRectpath = new Path();
        RectF rect2 =  new RectF(290, 50, 480, 200);
        CCWRectpath.addRect(rect2, Path.Direction.CW);

//先画出这两个路径
        canvas.drawPath(CCWRectpath, paint);
       // canvas.drawPath(CCWRectpath, paint);
    }
}
