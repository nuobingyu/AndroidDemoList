package com.example.a3dhualangdemo;

import android.graphics.pdf.PdfRenderer;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

public class RotationPageTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE= 0.85f;

    @Override
    public void transformPage(@NonNull View page, float position) {
        float scaleFactor = Math.max(MIN_SCALE ,1-Math.abs(position));
        float rotate = 10 *  Math.abs(position);
        Log.i("transformPage",scaleFactor +" "+rotate);

        //记录： setRotation 图片 消失了
        Log.i("position",position+"");
        Log.i("rotate",""+rotate);
        if(position <= -1){
            page.setAlpha(0);
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
            page.setRotationY(45) ;
        }else if(position < 0){
            page.setAlpha(1);
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
            page.setRotationY(45);
        }else if(position < 1){
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
            page.setRotationY(-rotate);
        }else if(position >= 1){
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
            page.setRotationY(-rotate) ;
        }
    }

}
