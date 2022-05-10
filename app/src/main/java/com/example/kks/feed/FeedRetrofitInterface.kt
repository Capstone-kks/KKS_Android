package com.example.kks.feed

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FeedRetrofitInterface {
    // Feed 목록 불러오기
    @GET("/feed?")
    fun getFeed(
        @Query("sort") sort :Int, // 정렬 기준 (최신순 : 1, 인기순 : 2)
        @Query("isFollowing")  isFollowing : Boolean //  팔로잉한 유저만(true)/ 전체 (false)
    ): Call<FeedResponse>
}