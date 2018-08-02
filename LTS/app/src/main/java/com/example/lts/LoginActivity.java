package com.example.lts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity{
    EditText mimaText;
    EditText userText;
    Button loginBtn;
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        userText = findViewById(R.id.user);
        mimaText = findViewById(R.id.mima);
        loginBtn = findViewById(R.id.login_btn);
        imageView = findViewById(R.id.login_tx);

        loginBtn.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                String str1 = userText.getText().toString();
                String str2 = mimaText.getText().toString();

                if(str1.equals("")||str2.equals("")){
                    Toast("用户名和密码不能为空！");
                }else if(!(str1.equals("123456")&&str2.equals("123456"))){
                    Toast("用户名或密码有误！");
                }else if(str1.equals("123456")&&str2.equals("123456")){
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }

            }
        });

    }


    public void Toast(String content){
        Toast.makeText(this,content,Toast.LENGTH_SHORT).show();
    }
}
