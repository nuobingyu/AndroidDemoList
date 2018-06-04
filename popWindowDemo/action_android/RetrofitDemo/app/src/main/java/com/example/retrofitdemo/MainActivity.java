package com.example.retrofitdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    /**
     * 1. 导包
     * 2. 创建接受接口 用注解方式写明请求方式：get("url后面部分或者其他") post("/form")
     * 3. 创建实体类
     * 4. 创建Retrofit实例通过build方法
     * 5. 实例化request
     * 6. call.enqueue(new Callback<类>{....})开始网络请求
     * 7. 在onResponse方法中获取数据基本与okhttp就一样了
     * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        request();
    }

    public void request(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetRequestInterface request = retrofit.create(GetRequestInterface.class);

        Call<Translation> call = request.getCall();
        call.enqueue(new Callback<Translation>( ) {
            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
                response.body().show();

            }

            @Override
            public void onFailure(Call<Translation> call, Throwable t) {

            }
        });

    }
}
