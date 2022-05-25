package com.example.kks.calendar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kks.databinding.ListItemMonthBinding
import java.util.*

class AdapterMonth(val context: Context): RecyclerView.Adapter<AdapterMonth.MonthView>() {


    val center = Int.MAX_VALUE / 2
    private var calendar = Calendar.getInstance()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthView {
        val binding : ListItemMonthBinding = ListItemMonthBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MonthView(binding)
    }

    override fun onBindViewHolder(holder: MonthView, position: Int) {
        calendar.time = Date()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.add(Calendar.MONTH, position - center)
      //  holder.bind(calendar)
        holder.binding.itemMonthText.text = "${calendar.get(Calendar.YEAR)}년 ${calendar.get(Calendar.MONTH) + 1}월"
        val tempMonth = calendar.get(Calendar.MONTH)

        var dayList: MutableList<Date> = MutableList(6 * 7) { Date() }
        for(i in 0..5) {
            for(k in 0..6) {
                calendar.add(Calendar.DAY_OF_MONTH, (1-calendar.get(Calendar.DAY_OF_WEEK)) + k)
                dayList[i * 7 + k] = calendar.time
            }
            calendar.add(Calendar.WEEK_OF_MONTH, 1)
        }

        val dayListManager = GridLayoutManager(context, 7)
        val dayListAdapter = AdapterDay(tempMonth, dayList,context)

        holder.binding.itemMonthDayList.apply {
            layoutManager = dayListManager
            adapter = dayListAdapter
        }
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    inner class MonthView(val binding:ListItemMonthBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(calendar: Calendar){


        }
    }
}