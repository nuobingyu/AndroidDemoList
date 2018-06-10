package com.example.retrifitdemo1;

import android.os.*;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Normalizer;

public class UserLoginTask extends AsyncTask<Void ,Void ,Boolean>{

    private String xh;
    private String mm;


    UserLoginTask(String xh1,String mm1){
        xh =xh1;
        mm = mm1;
    }

    //api请求
    @Override
    protected Boolean doInBackground(Void... voids) {
        System.out.print("doInBackground");
        String result = "";
        String loginUrl= StringPool.URL_LOGIN;
        try {
            URL url = new URL(loginUrl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setConnectTimeout(5 * 1000);
            //connection.setRequestMethod("GET");
            //  .add("__VIEWSTATE", "dDwxNTMxMDk5Mzc0Ozs+lYSKnsl/mKGQ7CKkWFJpv0btUa8=" )
            //                .add("txtUserName", xh)
            //                .add("Textbox1", mm)
            //                .add("TextBox2", mm)
            //                .add("txtSecretCode", checkCode)
            //                .add("RadioButtonList1","%D1%A7%C9%FA" )
            //                .add("Button1", "")
            //                .add("lbLanguage","")
            //                .add("hidPdrs", "")
            //                .add("hidsc", "")

            connection.setRequestMethod("Post");

            InputStream inputStream =connection.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line = in.readLine())!= null){
                result += '\n' +line;
            }

            Log.e("result","内容"+result);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


    //ui更新
    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}
