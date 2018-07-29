package com.example.zujianhuademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_hhh);

        ARouter.openLog();
        ARouter.openDebug();
        ARouter.init(getApplication());

        TextView text1 = findViewById(R.id.text111);
        TextView text2 = findViewById(R.id.text222);
        text1.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"app_mainACT", LENGTH_SHORT ).show();
                ARouter.getInstance().build("/mod1/mainAct").navigation();

                //finish();
            }
        });
        text2.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(MainActivity.this,);
                ARouter.getInstance()
                        .build("/mod2/loginAct")
                        .withString("user","nby")
                        .navigation();

            }
        });
    }


}
