package com.example.kks.controller;

import com.example.kks.info.follow.Follow;
import com.example.kks.info.myrecord.MyRecord;
import com.example.kks.login.PostUser;
import com.example.kks.record.Record;
import com.example.kks.search.Search;
import com.example.kks.search.SearchTest;

import java.util.ArrayList;
import java.util.List;

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


    @GET("/api/record/search/keyword")
    Call<ArrayList<Search>> getSearchResult(@Query("keyword") String keyword);

    @GET("/api/record/search/keywordtest")
    Call<ArrayList<SearchTest>> getSearchResultTest(@Query("keyword") String keyword, @Query("loginUserId") String loginUserId, @Query("sort") int sort);

    @GET("api/follower")
    Call<ArrayList<Follow>> getFollower(@Query("userId") String userId);

    @GET("api/following")
    Call<ArrayList<Follow>> getFollowing(@Query("userId") String userId);

    @GET("api/record/myrecord")
    Call<ArrayList<MyRecord>> getMyRecords(@Query("userId") String userId);

}
