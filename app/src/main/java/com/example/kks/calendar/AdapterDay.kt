package com.example.kks.calendar

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.kks.databinding.ListItemDayBinding
import com.example.kks.feed.RecordListAdapter
import com.example.kks.record.WriteActivity
import java.util.*

class AdapterDay(val tempMonth:Int, val dayList: MutableList<Date>,val context: Context):RecyclerView.Adapter<AdapterDay.DayView>() {

    val ROW=6

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterDay.DayView {
        val binding : ListItemDayBinding = ListItemDayBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DayView(binding)

    }

    override fun onBindViewHolder(holder: AdapterDay.DayView, position: Int) {
        holder.binding.itemDayLayout.setOnClickListener {
            Toast.makeText(context, "${dayList[position]}", Toast.LENGTH_SHORT).show()
            val intent = Intent(context,WriteActivity::class.java)
            ContextCompat.startActivity(context,intent,null)

        }
        holder.binding.itemDayText.text = dayList[position].date.toString()

        holder.binding.itemDayText.setTextColor(when(position%7){
            0-> Color.RED
            6-> Color.BLUE
            else-> Color.BLACK
        })

        if(tempMonth!=dayList[position].month){
            holder.binding.itemDayLayout.alpha = 0.4F
        }

    }

    override fun getItemCount(): Int {

        return ROW*7

    }

    inner class DayView(val binding:ListItemDayBinding):RecyclerView.ViewHolder(binding.root){

    }

}