package com.example.bsrdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MyView myView = (MyView)findViewById(R.id.myview);

        myView.startAnim();

        Button reset_btn = findViewById(R.id.reset);
        reset_btn.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                myView.reset();
            }
        });
    }
}
