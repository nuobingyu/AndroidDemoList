package com.example.popwindowdome_camera;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.zip.Inflater;

import static com.example.popwindowdome_camera.R.layout.popwindow;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView tv = findViewById(R.id.text_btn);

        tv.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                PopupWindow popupWindow = new PopupWindow();
                popupWindow.setHeight(500);
                popupWindow .setWidth(ViewGroup.LayoutParams.MATCH_PARENT );


                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.popwindow,null ); //这里更换popwindow布局

//                TextView ttv = view.findViewById(R.id.text);
//                ttv.setText("我是popWindow的内容");
//                TextView textView = new TextView(MainActivity.this );
//                textView.setText("我是popWindow的内容");
//                textView.setWidth(200);
//                textView.setHeight(100);
//                textView.setTextColor(getResources().getColor(R.color.blue ));
//                RelativeLayout  layout = view.findViewById(R.id.simple_popwindow);
//
//                layout.addView(textView);
                 
                popupWindow.setContentView(view);

                //popwindow 的第一个参数为描点view,即依附在哪个view上，是不是popwindow布局里的view都可以
//                popupWindow.showAsDropDown(tv,0,0,Gravity.CENTER ); //ttv | textview
                SelectPicPopupWindow  cSelectPicPopupWindow = new SelectPicPopupWindow(MainActivity.this );
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    cSelectPicPopupWindow.showAsDropDown(tv,0,0,Gravity.CENTER);
                }
            }
        });
    }

}
