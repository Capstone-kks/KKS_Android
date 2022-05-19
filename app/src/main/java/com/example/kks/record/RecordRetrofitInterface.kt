package com.example.kks.record

import retrofit2.Call
import retrofit2.http.*

interface RecordRetrofitInterface {

    // 글 작성 API
    @POST("/api/record/save")
    fun postRecord(
        @Body record: RecordReq
    ): Call<PostRecordResponse>


    // 글 확인 API
    @GET("/api/record/{recordIdx}")
    fun getRecordDetail(
        @Path("recordIdx") recordIdx : Int
    ):Call<DetailRecordResponse>



    // 글 수정 API


    // 글 삭제 API


    // 피드 글 목록 API
    @GET("/all?")
    fun getFeedRecords(
        @Query("sort") sort:Int, // 정렬 유형 (최신순: 1 / 인기순 : 2)
        @Query("follower") follower: Boolean // ( follower 한 글만 보기 : true / 전체 보기 : false)
    )


}