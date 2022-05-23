package com.example.kks.feed

import com.google.gson.annotations.SerializedName

data class FeedResponse(
    @SerializedName("recordIdx") val recordIdx : Int, // 레코드 고유 번호
    @SerializedName("userId") val userId : String, // 레코드 작성자 고유 번호
    @SerializedName("nickName") val nickName : String, // 작성자 닉네임
    @SerializedName("title") val title : String, // 레코드 제목
    @SerializedName("content") val content : String, // 레코드 내용
    @SerializedName("postDate") val postDate : String, // 작성한 날짜
    @SerializedName("imgUrl") val imgUrl : String, // 이미지

)
