package com.example.kks.Controller;

import com.example.kks.Login.PostUser;

import java.lang.reflect.Member;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitAPI {
    @POST("/api/login")
    Call<PostUser> postData(@Body PostUser postUser);
}
