package com.example.mvpdemo1.Modle;

public class LoginModle {

    public LoginModle(){
    }

    public LoginModle(String userName ,String mima){
        this.userName = userName;
        this.mima = mima;
    }

    private String userName;

    private String mima;

    public String getMima() {
        return mima;
    }

    public void setMima(String mima) {
        this.mima = mima;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
