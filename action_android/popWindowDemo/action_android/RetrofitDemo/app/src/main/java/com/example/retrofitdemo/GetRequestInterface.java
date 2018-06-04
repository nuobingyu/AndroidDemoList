package com.example.retrofitdemo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetRequestInterface {

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
    Call<Translation> getCall();

}
