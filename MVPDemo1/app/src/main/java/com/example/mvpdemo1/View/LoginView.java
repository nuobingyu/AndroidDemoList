package com.example.mvpdemo1.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ProgressBar;

public interface  LoginView {

    public void showLoginSuccessToast(Context context);
    public void showLoginFailToast(Context context);
    public void showProgress(Context context);
    public void dismissProgress(ProgressDialog progressDialog);

}
