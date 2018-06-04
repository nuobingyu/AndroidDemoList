package com.example.huitudemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class DoubleColorRadialGraidentView extends View {
    private Paint mPaint;
    private RadialGradient radialGradient;

    public DoubleColorRadialGraidentView(Context context) {
        super(context);
    }

    public DoubleColorRadialGraidentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DoubleColorRadialGraidentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(){
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        mPaint = new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        radialGradient = new RadialGradient(w/2, h/2,100,0xffff0000, 0xff00ff00, Shader.TileMode.REPEAT);
        mPaint.setShader(radialGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(getWidth()/2,getHeight()/2,100,mPaint);
    }
}
