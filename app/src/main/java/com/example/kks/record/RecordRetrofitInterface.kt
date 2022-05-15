package com.example.kks.record

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Part

interface RecordRetrofitInterface {

    // 글 작성
    @POST("/api/record/save")
    fun postRecord(
        @Body record: RecordReq
    ): Call<PostRecordResponse>


    // 글 수정


    // 글 삭제

}