package com.example.kks.feed

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FeedRetrofitInterface {
    // Feed 목록 불러오기

    // 피드 글 목록 API
    @GET("api/record/all?")
    fun getFeedList(
        @Query("loginUserId") loginUserId:String, // 현재 로그인한 유저 id
        @Query("sort") sort:Int, // 정렬 유형 (최신순: 1 / 인기순 : 2)
        @Query("isFollowCheck") isFollowCheck: Boolean // ( follower 한 글만 보기 : true / 전체 보기 : false)
    ):Call<FeedListResponse>

}