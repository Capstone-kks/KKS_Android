package com.example.kks.calendar


interface CalendarView{
    fun onGetCalendarLoading()
    fun onGetCalendarSuccess(result: ArrayList<CalendarContent>)
    fun onGetCalendarFailure(code : Int, message: String)

}