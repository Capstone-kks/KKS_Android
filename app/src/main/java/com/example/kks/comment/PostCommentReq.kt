package com.example.kks.comment

import com.google.gson.annotations.SerializedName

data class PostCommentReq(
    @SerializedName("recordIdx") val recordIdx:Int,
    @SerializedName("userId") val userId:String,
    @SerializedName("content") val content:String,


)
