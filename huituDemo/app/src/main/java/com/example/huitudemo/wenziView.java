package com.example.huitudemo;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class wenziView extends View {

    Context mContext;
    public wenziView(Context context) {
        super(context);
        mContext = context;
    }

    public wenziView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public wenziView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint=new Paint();
        paint.setColor(Color.RED);  //设置画笔颜色

        paint.setStrokeWidth (5);//设置画笔宽度
        paint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
        paint.setTextSize(80);//设置文字大小
        paint.setFakeBoldText(true);//设置是否为粗体文字
        //绘图样式，设置为填充
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText("欢迎光临Harvic的博客", 10,100, paint);

        //绘图样式设置为描边
        paint.setStyle(Paint.Style.STROKE);
        paint.setFakeBoldText(false);//设置是否为粗体文字
        paint.setTextSkewX((float) -0.25);//设置字体水平倾斜度，普通斜体字是-0.25
        canvas.drawText("欢迎光临Harvic的博客", 10,200, paint);

        //绘图样式设置为填充且描边
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setUnderlineText(true);//设置下划线
        canvas.drawText("欢迎光临Harvic的博客", 10,300, paint);

//        paint.setStrokeWidth (5);//设置画笔宽度
//        paint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
//        paint.setTextSize(60);//设置文字大小
//        paint.setStyle(Paint.Style.FILL);//绘图样式，设置为填充

//        AssetManager mgr=getContext().getAssets();//得到AssetManager
//        Typeface typeface=Typeface.createFromAsset(mgr, "fonts/jian_luobo.ttf");//根据路径得到Typeface
//        paint.setTypeface(typeface);
//        Log.v("msg",typeface.toString());
//        canvas.drawText("欢迎光临Harvic的博客",10,100, paint);//两个构造函数
    }
}
