package com.example.mvpdemo1.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LoginViewImpl implements LoginView{

    private Context mContext;
    private ProgressDialog progressDialog;

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }

    public LoginViewImpl(Context context){
        mContext = context;
    }


    @Override
    public void showLoginSuccessToast(Context context) {
        Toast.makeText(context,"登录成功！",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginFailToast(Context context) {
        Toast.makeText(context,"登陆失败",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("正在加载...");
        progressDialog.show();
    }

    @Override
    public void dismissProgress(ProgressDialog progressDialog) {
        progressDialog.dismiss();
    }
}
