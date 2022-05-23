package com.example.kks.record

import com.google.gson.annotations.SerializedName

data class Record(
    @SerializedName("recordIdx") val recordIdx : Long, // 레코드 고유 번호
    @SerializedName("userId") val userIdx : Long, // 레코드 작성자 고유 번호
    @SerializedName("userNickname") val userNickname : String, // 작성자 닉네임
    @SerializedName("title") val title : String, // 레코드 제목
    @SerializedName("categoryIdx") val categoryIdx : Int, // 레코드 종류
    @SerializedName("rate") val rate : Double, // 레코드 평점
    @SerializedName("content") val content : String, // 레코드 내용
    @SerializedName("postPublic") val postPublic : Boolean, // 공개 여부
    @SerializedName("imgUrl") val recordImgUrl : Int, // 이미지
    @SerializedName("postDate") val postDate : String, // 작성한 날짜
    @SerializedName("commentCount") val commentCount : Int, // 댓글 수
)