package com.example.kks.controller;

import com.example.kks.login.PostUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI {
    @POST("/api/login")
    Call<PostUser> postData(@Body PostUser postUser);
}
