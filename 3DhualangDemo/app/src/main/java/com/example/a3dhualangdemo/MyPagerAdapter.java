package com.example.a3dhualangdemo;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class MyPagerAdapter extends PagerAdapter{

    private int[] mBitmapIds;
    private Context mContext;

    public MyPagerAdapter(int[] data, Context context){
        mBitmapIds = data;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mBitmapIds.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_viewpager,container,false);
        ImageView img = view.findViewById(R.id.item_iv);
        img.setImageResource(mBitmapIds[position]);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(img ,"rotationY",0,180);
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE );
        objectAnimator.setDuration(1000);
        objectAnimator.start();
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
