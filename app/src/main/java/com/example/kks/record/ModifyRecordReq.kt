package com.example.kks.record

import com.google.gson.annotations.SerializedName

data class ModifyRecordReq(
    @SerializedName("title") val title : String, // 레코드 제목
    @SerializedName("categoryId") val categoryId : Int, // 레코드 종류
    @SerializedName("rate") val rate : Float, // 레코드 평점
    @SerializedName("content") val content : String, // 레코드 내용
    @SerializedName("postPublic") val postPublic : Int, // 공개 여부
    @SerializedName("imgUrl") val recordImgUrl : String // 이미지
)
