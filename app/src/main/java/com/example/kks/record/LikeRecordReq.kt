package com.example.kks.record

import com.google.gson.annotations.SerializedName

data class LikeRecordReq(
    @SerializedName("recordIdx") val recordIdx:Int,
    @SerializedName("userId") val userId:String,

)