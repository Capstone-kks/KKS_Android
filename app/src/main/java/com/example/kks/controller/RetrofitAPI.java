package com.example.kks.controller;

import com.example.kks.login.PostUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @POST("/api/login")
    Call<PostUser> postData(@Body PostUser postUser);

    @GET("/api/login/get")
    Call<PostUser> getUser(@Query("userId") String userId);
}
