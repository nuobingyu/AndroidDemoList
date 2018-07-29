package com.example.module_2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import static android.widget.Toast.LENGTH_SHORT;

@Route(path="/mod2/loginAct")
public class LoginActivity extends AppCompatActivity{

    @Autowired(name="user")
    public String name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout );
        Toast.makeText(LoginActivity.this,"启动mod2", LENGTH_SHORT ).show();
        ARouter.getInstance().inject(this);
        Log.i("msg","user = "+name);
        startService();
        bindService();
    }
}
