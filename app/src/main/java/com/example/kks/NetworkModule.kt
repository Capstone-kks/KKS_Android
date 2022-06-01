package com.example.kks

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//const val BASE_URL="http://10.0.2.2:8080"
//const val BASE_URL="http://ec2-54-180-169-133.ap-northeast-2.compute.amazonaws.com:8080";
const val BASE_URL="http://devkks.shop";

fun getRetrofit(): Retrofit {
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    return retrofit

}