package com.example.viewpagerdemo_loop;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private int[] mImagIds =new int[]{R.drawable.a4 ,R.drawable.a1 ,R.drawable.a2 ,R.drawable.a3 ,R.drawable.a4 ,R.drawable.a1};
    private List<ImageView> imageViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        viewPager = findViewById(R.id.viewpager);
        viewPager.setCurrentItem(1,false);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mImagIds.length;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                container.addView(imageViews.get(position));
                return imageViews.get(position);
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView(imageViews.get(position));
            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener( ) {

            //positionOffsetPixels: 当前页面偏移的px值
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position == 0 && positionOffsetPixels == 0){
                    viewPager.setCurrentItem(4,false);//直观最后一张图
                }else if(position == 5 && positionOffsetPixels == 0){
                    //如果是实际最后一张图则设置直观第一张图
                    viewPager.setCurrentItem(1,false);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void init(){
        for(int imgId : mImagIds)
        {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(imgId);
            imageViews.add(imageView);
        }
    }
}
