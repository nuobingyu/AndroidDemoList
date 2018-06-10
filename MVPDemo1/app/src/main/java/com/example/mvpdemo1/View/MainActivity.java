package com.example.mvpdemo1.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mvpdemo1.Presenter.LoginPresenterImpl;
import com.example.mvpdemo1.R;

public class MainActivity extends AppCompatActivity {

    EditText userNameEdit;
    EditText mimaEdit;
    Button loginBtn;

    LoginPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        loginBtn.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                presenter = new LoginPresenterImpl(MainActivity.this,userNameEdit.getText().toString()
                ,mimaEdit.getText().toString());
                presenter.login();

            }
        });
    }


    public void initView(){
        userNameEdit = findViewById(R.id.user);
        mimaEdit = findViewById(R.id.mima);
        loginBtn = findViewById(R.id.login_btn);
    }

}
