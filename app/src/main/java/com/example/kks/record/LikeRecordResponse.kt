package com.example.kks.record

import com.google.gson.annotations.SerializedName

data class LikeRecordResponse(

    @SerializedName("isSuccess") val isSuccess: Boolean, // 요청 성공 여부
    @SerializedName("code") val code:Int, // 응답코드
    @SerializedName("message") val message:String, // 응답 메세지
    @SerializedName("result") val result: String // 댓글을 업로드한 결과
)
