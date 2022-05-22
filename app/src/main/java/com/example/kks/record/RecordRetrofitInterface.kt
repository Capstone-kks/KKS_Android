package com.example.kks.record

import retrofit2.Call
import retrofit2.http.*

interface RecordRetrofitInterface {

    // 글 작성 API
    @POST("/api/record/save")
    fun postRecord(
        @Body record: RecordReq
    ): Call<PostRecordResponse>


    // 글 조회 API
    @GET("/api/record/detail/{recordIdx}")
    fun getRecordDetail(
        @Path("recordIdx") recordIdx : Int
    ):Call<DetailRecordResponse>



    // 글 수정 API
    @PATCH("/api/record/modify/{userId}/{recordIdx}")
    fun getModifyRecord(
        @Path("userId") userId : String,
        @Path("recordIdx") recordIdx : Int,
        @Body modifyRecordReq:ModifyRecordReq
    ):Call<ModifyRecordResponse>





    // 글 삭제 API
    @DELETE("/api/record/{userId}/{recordIdx}")
    fun getDeleteRecord(
        @Path("userId") userId : String, // 삭제를 시도하는 user id
        @Path("recordIdx") recordIdx : Int //
    ):Call<DeleteRecordResponse>



}