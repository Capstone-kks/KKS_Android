package com.example.kks.calendar

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kks.databinding.ListItemMonthBinding
import com.example.kks.getUserIdx
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AdapterMonth(val context: Context): RecyclerView.Adapter<AdapterMonth.MonthView>(),CalendarView {


    val center = Int.MAX_VALUE / 2
    private var calendar = Calendar.getInstance()
   // var tempMonth = -1
    val format1= SimpleDateFormat("yyyy-MM")
    lateinit var dayList :MutableList<Date>
    private lateinit var dayListAdapter : AdapterDay



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthView {
        val binding : ListItemMonthBinding = ListItemMonthBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return MonthView(binding)
    }

    override fun onBindViewHolder(holder: MonthView, position: Int) {
        calendar.time = Date()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.add(Calendar.MONTH, position - center)
        holder.bind(calendar)
   //     Log.d("position:",position.toString())
     //   Log.d("position-center:",(position-center).toString())
      //  holder.bind(calendar)
   //     holder.binding.itemMonthText.text = "${calendar.get(Calendar.YEAR)}년 ${calendar.get(Calendar.MONTH) + 1}월"
//        val tempMonth = calendar.get(Calendar.MONTH) // 현재위치의 월
//        val tempYearMonth =  format1.format(calendar.time)
//        Toast.makeText(context,tempYearMonth,Toast.LENGTH_SHORT).show()

//        var dayList: MutableList<Date> = MutableList(6 * 7) { Date() } // 현재 위치의 월에대한 일들을 구함.
//        for(i in 0..5) {
//            for(k in 0..6) {
//                calendar.add(Calendar.DAY_OF_MONTH, (1-calendar.get(Calendar.DAY_OF_WEEK)) + k)
//                dayList[i * 7 + k] = calendar.time
//            }
//            calendar.add(Calendar.WEEK_OF_MONTH, 1)
//        }

    //    getCalendarData()
//
//        val dayListManager = GridLayoutManager(context, 7)
//        val dayListAdapter = AdapterDay(tempMonth, dayList,context)
//
//        holder.binding.itemMonthDayList.apply {
//            layoutManager = dayListManager
//            adapter = dayListAdapter
//        }
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    inner class MonthView(val binding:ListItemMonthBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(calendar: Calendar){
            binding.itemMonthText.text="${calendar.get(Calendar.YEAR)}년 ${calendar.get(Calendar.MONTH) + 1}월"
            val tempMonth = calendar.get(Calendar.MONTH)-1 // 현재위치의 월
            val tempYearMonth =  format1.format(calendar.time)

//            val dayList: MutableList<Date> = MutableList(6 * 7) { Date() } // 현재 위치의 월에대한 일들을 구함.
//            for(i in 0..5) {
//                for(k in 0..6) {
//                    calendar.add(Calendar.DAY_OF_MONTH, (1-calendar.get(Calendar.DAY_OF_WEEK)) + k)
//                    dayList[i * 7 + k] = calendar.time
//                }
//                calendar.add(Calendar.WEEK_OF_MONTH, 1)
//            }

            dayList = MutableList(6 * 7) { Date() } // 현재 위치의 월에대한 일들을 구함.
            for(i in 0..5) {
                for(k in 0..6) {
                    calendar.add(Calendar.DAY_OF_MONTH, (1-calendar.get(Calendar.DAY_OF_WEEK)) + k)
                    dayList[i * 7 + k] = calendar.time
                }
                calendar.add(Calendar.WEEK_OF_MONTH, 1)
            }


            val dayListManager = GridLayoutManager(context, 7)
//            val dayListAdapter = AdapterDay(tempMonth, dayList,context)
             dayListAdapter = AdapterDay(tempMonth, dayList,context)
            getCalendarData(tempYearMonth)

            binding.itemMonthDayRecyclerview.layoutManager = dayListManager
            binding.itemMonthDayRecyclerview.adapter = dayListAdapter

        }
    }

    private fun getCalendarData(date:String){
        val calendarService = CalendarService()
        calendarService.setCalendarView(this)
        calendarService.getCalendarData(getUserIdx(context),date)

    }

    override fun onGetCalendarLoading() {
        Log.d("calendar/API","로딩중...")
    }

    override fun onGetCalendarSuccess(result: ArrayList<CalendarContent>) {
        Log.d("calendar/API","성공")
        Log.d("calendar/API",result.toString())
        dayListAdapter.addRecords(result)
    }

    override fun onGetCalendarFailure(code: Int, message: String) {
        when(code){
            400-> Log.d("calendar/API",message)
        }
    }
}