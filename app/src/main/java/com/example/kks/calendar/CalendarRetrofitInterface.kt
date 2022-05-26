package com.example.kks.calendar

import retrofit2.Call
import retrofit2.http.*
import java.time.YearMonth

interface CalendarRetrofitInterface {

    @GET("/api/calendar/{userId}/{yearMonth}")
    fun getCalendarDataList(
        @Path("userId") userId:String,
        @Path("yearMonth") yearMonth:String
    ):Call<CalendarResponse>

}