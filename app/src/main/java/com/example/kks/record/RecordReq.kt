package com.example.kks.record

import com.google.gson.annotations.SerializedName

data class RecordReq(
    @SerializedName("userId") val userId : String, // 레코드 작성자 고유 번호
    @SerializedName("title") val title : String, // 레코드 제목
    @SerializedName("categoryId") val categoryId : Int, // 레코드 종류
    @SerializedName("rate") val rate : Float, // 레코드 평점
    @SerializedName("content") val content : String, // 레코드 내용
    @SerializedName("postPublic") val postPublic : Int, // 공개 여부
 //   @SerializedName("imgUrl") val recordImgUrl : String, // 이미지
    @SerializedName("postDate") val postDate : String, // 작성한 날짜
    @SerializedName("commentCount") val commentCount : Int, // 댓글 수
)