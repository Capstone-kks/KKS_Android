package com.example.kks.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {
    //private static final String BASE_URL = "http://localhost:8080";;
    //private static final String BASE_URL = "http://10.0.2.2:8080";
    private static final String BASE_URL = "http://ec2-54-180-169-133.ap-northeast-2.compute.amazonaws.com:8080";

    public Retrofit setRetrofit() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
    /*
    public static RetrofitAPI getApiService() {
        return getInstance().create(RetrofitAPI.class);
    }

    private static Retrofit getInstance() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
     */

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }
}