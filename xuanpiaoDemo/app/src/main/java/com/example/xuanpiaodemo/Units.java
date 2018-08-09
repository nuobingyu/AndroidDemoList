package com.example.xuanpiaodemo;

import android.util.DisplayMetrics;

public class Units {

    public static int getScreenWidth(){
        DisplayMetrics dm = new DisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight(){
        DisplayMetrics dm = new DisplayMetrics();
        return dm.heightPixels;
    }

    public static int px2dp(float px){
        DisplayMetrics dm = new DisplayMetrics();
        final float scale = dm.density;
        return (int)(px / scale  +0.5f);
    }
}
