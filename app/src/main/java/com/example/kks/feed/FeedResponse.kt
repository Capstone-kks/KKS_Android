package com.example.kks.feed

import com.example.kks.record.Record
import com.google.gson.annotations.SerializedName

class FeedResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code:Int,
    @SerializedName("message") val message:String,
    @SerializedName("result") val result: ArrayList<Record>
)