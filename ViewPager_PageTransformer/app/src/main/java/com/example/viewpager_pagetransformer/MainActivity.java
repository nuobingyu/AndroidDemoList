package com.example.viewpager_pagetransformer;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private int[] mImagIds = new int[] {R.drawable.ic_launcher_background,R.drawable.ic_launcher_foreground
            ,R.drawable.ic_launcher_background,R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_background
            ,R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_background};
    private List<ImageView> imageViews = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new PagerAdapter( ) {
            @Override
            public int getCount() {
                return mImagIds.length ;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object ;
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

        initData();
        viewPager.setPageTransformer(true,new DepthPAgeTransformer());
    }

    private void initData(){
        for(int imgId : mImagIds)
        {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(imgId);
            imageViews.add(imageView);
        }

    }
}
