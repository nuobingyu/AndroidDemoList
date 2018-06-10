package com.example.mvpdemo1.Presenter;

import android.content.Context;
import android.util.Log;

import com.example.mvpdemo1.Modle.LoginModle;
import com.example.mvpdemo1.View.LoginViewImpl;
import com.example.mvpdemo1.View.MainActivity;

public class LoginPresenterImpl implements LoginPresenter{
    private Context mContext;
    private LoginModle loginModle;
    private LoginViewImpl loginView;
    private String userName;
    private String mima;

    public LoginPresenterImpl(Context context,String userName ,String mima){
        mContext = context;
        this.userName = userName;
        this.mima = mima;
    }

    @Override
    public void login() {
        loginView = new LoginViewImpl(mContext);
        loginView.showProgress(mContext);
        if(userName.equals("nby")  && mima.equals("123456")){
            Log.e("login","userName="+userName+" mima="+mima);
            loginView.showLoginSuccessToast(mContext);
        }else{
            Log.e("login","userName="+userName+" mima="+mima);
            loginView.showLoginFailToast(mContext);
        }
        //loginView.dismissProgress(loginView.getProgressDialog());
    }

    @Override
    public void initModel() {
        loginModle.setUserName(userName);
        loginModle.setMima(mima);
    }


}
