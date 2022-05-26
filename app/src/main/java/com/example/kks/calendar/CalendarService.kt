package com.example.kks.calendar

import android.util.Log
import com.example.kks.getRetrofit
import retrofit2.Call
import retrofit2.Response

class CalendarService {
    private lateinit var calendarView: CalendarView

    // 외부 접근
    fun setCalendarView(calendarView: CalendarView){
        this.calendarView = calendarView
    }

    // 서버에서 해당 데이터 (feed) 에 대한 내용 받아오는 함수
    fun getCalendarData(userId:String,yearMonth:String){
        val calendarService = getRetrofit().create(CalendarRetrofitInterface::class.java)

        calendarView.onGetCalendarLoading()

        calendarService.getCalendarDataList(userId,yearMonth)
            .enqueue(object :retrofit2.Callback<CalendarResponse>{
                override fun onResponse(call: Call<CalendarResponse>, response: Response<CalendarResponse>) {

                    Log.d("why",response.toString());
                    val resp = response.body()!!

                    when(resp.code){
                        1000->{
                            calendarView.onGetCalendarSuccess(resp.result)
                        }else->calendarView.onGetCalendarFailure(resp.code, resp.message)
                    }
                }

                override fun onFailure(call: Call<CalendarResponse>, t: Throwable) {
                    calendarView.onGetCalendarFailure(400,"네트워크 오류가 발생했습니다.")
                }

            })
    }
}