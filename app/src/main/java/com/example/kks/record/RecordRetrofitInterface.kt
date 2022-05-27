package com.example.kks.record

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface RecordRetrofitInterface {

    // 글 작성 API
    @Multipart
    @POST("/api/record/save")
    fun postRecord(
        @Part images: MultipartBody.Part,
        @Part("RecordSaveReq") record: RecordReq
    ): Call<PostRecordResponse>


    // 글 조회 API
    @GET("/api/record/detail/{recordIdx}/{userId}")
    fun getRecordDetail(
        @Path("recordIdx") recordIdx : Int,
        @Path("userId") userId : String
    ):Call<DetailRecordResponse>



    // 글 수정 API
    @Multipart
    @PATCH("/api/record/modify/{userId}/{recordIdx}")
    fun getModifyRecord(
        @Path("userId") userId : String,
        @Path("recordIdx") recordIdx : Int,
        @Part("ModifyRecordReq") modifyRecordReq:ModifyRecordReq,
        @Part images:MultipartBody.Part?
    ):Call<ModifyRecordResponse>





    // 글 삭제 API
    @DELETE("/api/record/deletion/{userId}/{recordIdx}")
    fun getDeleteRecord(
        @Path("userId") userId : String, // 삭제를 시도하는 user id
        @Path("recordIdx") recordIdx : Int //
    ):Call<DeleteRecordResponse>


    @POST("/api/likes")
    fun postLikeRecord(
        @Body likeRecordReq: LikeRecordReq
    ):Call<LikeRecordResponse>


}