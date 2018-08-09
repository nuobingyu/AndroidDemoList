package com.example.xuanpiaodemo;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.*;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    public static Button order ;
    public static RelativeLayout root ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyView myView = new MyView(this);
        root = new RelativeLayout(MainActivity.this);
        order =findViewById(R.id.seletseat_btn);

            order.setOnClickListener(new View.OnClickListener( ) {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {
                    if (MyView.isClick) {
                        //跳转支付
                        Toast.makeText(MainActivity.this, "跳转支付", Toast.LENGTH_SHORT).show( );
                    }else{
                        Toast.makeText(MainActivity.this,"请先选择座位呦~",Toast.LENGTH_SHORT ).show();
                    }
                }
            });
    }
}
