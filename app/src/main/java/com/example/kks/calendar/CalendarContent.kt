package com.example.kks.calendar

import com.google.gson.annotations.SerializedName

data class CalendarContent(
    @SerializedName("recordIdx") val recordIdx:Int,
    @SerializedName("imgUrl") val imgUrl:String,
    @SerializedName("postDate") val postDate:String


)
