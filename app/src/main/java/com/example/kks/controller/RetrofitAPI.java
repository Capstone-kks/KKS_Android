package com.example.kks.controller;

import com.example.kks.login.PostUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @POST("/api/login")
    Call<PostUser> postData(@Body PostUser postUser);

    @GET("/api/login/get")
    Call<PostUser> getUser(@Query("userId") String userId);

    @GET("/api/record/countall")
    Call<String> getcountall(@Query("userId") String userId);

    @GET("/api/record/countmonth")
    Call<String> getcountmonth(@Query("userId") String userId, @Query("postDate") String date);

    @GET("/api/archive/category")
    Call<List<CatImg>> getcatimg(@Query("userId") String userId, @Query("categoryId") int categoryId);

}
