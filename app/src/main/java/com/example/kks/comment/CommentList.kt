package com.example.kks.comment

import com.google.gson.annotations.SerializedName

data class CommentList(
    @SerializedName("commentIdx") val commentIdx: Int, //
    @SerializedName("recordIdx") val recordIdx:Int, //
    @SerializedName("userId") val userId:String, //
    @SerializedName("nickName") val nickName: String,
    @SerializedName("content") val content: String,
    @SerializedName("postDate") val postDate: String,

)
