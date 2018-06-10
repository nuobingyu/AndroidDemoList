package com.example.a3dhualangdemo;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MyPagerAdapter adapter;
    int[] data = new int[]{R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4,R.drawable.a5};
//    int[] data = new int[]{R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground
//        ,R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground
//        ,R.drawable.ic_launcher_foreground};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager);
        adapter = new MyPagerAdapter(data ,this);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true ,new RotationPageTransformer());
        viewPager.setPageMargin(20);
        viewPager.setOffscreenPageLimit(3);

    }
}
