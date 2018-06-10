package com.example.a2048;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class UIActivity extends AppCompatActivity{

    TextView start;
    TextView exit;
    TextView aboutAs;
    TextView settings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_first);

        start = findViewById(R.id.start);
        exit = findViewById(R.id.exit);
        aboutAs = findViewById(R.id.aboutAs);
        settings = findViewById(R.id.settings);

        start.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        exit.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"尚未实现",Toast.LENGTH_SHORT ).show();
            }
        });


    }
}
