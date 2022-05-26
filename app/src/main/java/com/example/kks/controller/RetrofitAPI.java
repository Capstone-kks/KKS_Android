package com.example.kks.controller;

import com.example.kks.info.follow.Follow;
import com.example.kks.info.liked.RecordLike;
import com.example.kks.info.myrecord.MyRecord;
import com.example.kks.login.PostUser;
import com.example.kks.record.Record;
import com.example.kks.search.Recommend;
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


    //검색
    @GET("/api/record/search/keyword")
    Call<ArrayList<Search>> getSearchResult(@Query("keyword") String keyword);

    @GET("/api/record/search/keywordtest")
    Call<ArrayList<SearchTest>> getSearchResultTest(@Query("keyword") String keyword, @Query("loginUserId") String loginUserId, @Query("sort") int sort);


    //사용자 추천
    @GET("api/record/recommend")
    Call<ArrayList<Recommend>> getRecommend(@Query("userId") String userId);


    //프로필
    @GET("api/info/follower")
    Call<ArrayList<Follow>> getFollower(@Query("userId") String userId);

    @GET("api/info/following")
    Call<ArrayList<Follow>> getFollowing(@Query("userId") String userId);

    @GET("api/info/myrecord")
    Call<ArrayList<MyRecord>> getMyRecords(@Query("userId") String userId);

    @GET("api/info/liked")
    Call<ArrayList<MyRecord>> getLikedRecords(@Query("userId") String userId);

    @POST("api/info/withdrawal")
    Call<String> postWithdrawalUser(@Body String userId);


    //아카이브
    @GET("/api/archive/search")
    Call<ArrayList<Records>> getArchiveSearch(@Query("userId") String userId, @Query("categoryId") int categoryId, @Query("keyword") String keyword);


    //for 아카이브 검색 - 좋아요 개수 반환
    @GET("/api/likes/countlike")
    Call<String> getCountLike(@Query("recordIdx") int recordIdx);

    //for 아카이브 검색 = 좋아요 여부 반환
    @GET("/api/likes/getstatus")
    Call<String> getLikeStatus(@Query("recordIdx") int recordIdx, @Query("userId") String userId);

    //예슬 - 아카이브 검색 좋아요 test
    @GET("/api/likes/getliketest")
    Call<ArrayList<RecordLike>> getRecordLiked(@Query("recordIdx") int recordIdx, @Query("userId") String userId);

}
