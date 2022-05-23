package com.example.kks.feed

import com.google.gson.annotations.SerializedName

class FeedListResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code:Int,
    @SerializedName("message") val message:String,
    @SerializedName("result") val result: ArrayList<FeedResponse>
)