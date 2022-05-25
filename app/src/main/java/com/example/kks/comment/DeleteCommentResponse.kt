package com.example.kks.comment

import com.example.kks.record.Record
import com.google.gson.annotations.SerializedName

data class DeleteCommentResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean, // 요청 성공 여부
    @SerializedName("code") val code:Int, // 응답코드
    @SerializedName("message") val message:String, // 응답 메세지
    @SerializedName("result") val result: String
)