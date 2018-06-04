package com.example.lunbotudemo2;

import android.annotation.SuppressLint;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager; //
    private List<ImageView> images; //
    private List<View> dots; //
    private int currentItem; // 当前位置
    private int oldPosition = 0; // 上次的位置
    private TextView titleTextView;
    private ViewPagerAdapter adapter;
    private ScheduledExecutorService scheduledExecutorService;


    private int[] imageIds = new int[]{
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d
    };
    private String[] titles = new String[]{
            "这是第一个",
            "这是第二个",
            "这是第三个",
            "这是第四个"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleTextView = findViewById(R.id.title);
        titleTextView.setText(titles[0]);
        mViewPager = findViewById(R.id.viewpager);

        images = new ArrayList<>();
        for(int i= 0 ; i<imageIds.length;i++){
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageIds[i]) ;
            images.add(imageView);
        }
        adapter = new ViewPagerAdapter(this,images);
        mViewPager.setAdapter(adapter);

        dots = new ArrayList<>();
        dots.add(findViewById(R.id.dot_0));
        dots.add(findViewById(R.id.dot_1));
        dots.add(findViewById(R.id.dot_2));
        dots.add(findViewById(R.id.dot_3));

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener( ) {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                titleTextView.setText(titles[position]);
                dots.get(position).setBackgroundResource(R.drawable.dot_focus);
                dots.get(oldPosition).setBackgroundResource(R.drawable.dot );

                oldPosition = position;
                currentItem = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }



    class ViewPageTask implements Runnable{

        @Override
        public void run() {
            currentItem = (currentItem +1) % imageIds.length;
            mHandler.sendEmptyMessage(0);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
             mViewPager.setCurrentItem(currentItem);
        }
    };

    @Override
    protected void onStart() {
        super.onStart( );
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(new ViewPageTask(),
                2,
                2,
                TimeUnit.SECONDS);
    }

    @Override
    protected void onStop() {
        super.onStop( );
        if(scheduledExecutorService != null){
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
        }
    }
}
