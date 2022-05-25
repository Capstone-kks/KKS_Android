package com.example.kks.calendar

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.kks.R
import com.example.kks.databinding.ListItemDayBinding
import com.example.kks.feed.RecordListAdapter
import com.example.kks.record.WriteActivity
import okhttp3.internal.format
import java.text.SimpleDateFormat
import java.util.*

class AdapterDay(val tempMonth:Int, val dayList: MutableList<Date>,val context: Context):RecyclerView.Adapter<AdapterDay.DayView>() {

    val ROW=6
    val nowMillis = System.currentTimeMillis()
    val now_date = Date(nowMillis)
    val format1=SimpleDateFormat("yyyy-MM-dd")
    val todayDate=format1.format(now_date)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterDay.DayView {
        val binding : ListItemDayBinding = ListItemDayBinding.inflate(LayoutInflater.from(parent.context),parent,false)



        return DayView(binding)

    }

    override fun onBindViewHolder(holder: AdapterDay.DayView, position: Int) {

        // 클릭한 날짜 구하기
//        val year = (dayList[position].year+1900).toString()
//        val month = (dayList[position].month+1).toString()
//        val date = dayList[position].date.toString()

        holder.bind(dayList[position])

        holder.binding.itemDayLayout.setOnClickListener {
            val clickDate=format1.format(dayList[position]) // 클릭한 날짜 (글쓸때 넘김)
            val today = format1.parse(todayDate)
            val click = format1.parse(clickDate)


            if(today.before(click)){
                Toast.makeText(context,"미래 날짜에는 글을 작성할 수 없습니다.",Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(context,WriteActivity::class.java)
                intent.putExtra("clickDate",clickDate)
                ContextCompat.startActivity(context,intent,null)
            }
        }




//        if(tempDate.equals(todayDate)){
//            holder.binding.itemDayText.setBackgroundColor(Color.YELLOW)
//        }
//        holder.binding.itemDayLayout.setOnClickListener {
//         //   Toast.makeText(context, "${dayList[position]}", Toast.LENGTH_SHORT).show()
//         //   val intent = Intent(context,WriteActivity::class.java)
//        //    ContextCompat.startActivity(context,intent,null)
////            Toast.makeText(context,dayList[position].year.toString(),Toast.LENGTH_SHORT).show()
////            Toast.makeText(context,dayList[position].month.toString(),Toast.LENGTH_SHORT).show()
////            Toast.makeText(context,dayList[position].date.toString(),Toast.LENGTH_SHORT).show()
////            Toast.makeText(context,dayList[position].day.toString(),Toast.LENGTH_SHORT).show()
//
//            Toast.makeText(context,tempDate,Toast.LENGTH_SHORT).show()
//            Toast.makeText(context,today,Toast.LENGTH_SHORT).show()
//        }



   //     holder.binding.itemDayText.text = dayList[position].date.toString()


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

        fun bind(day:Date){
            val date = format1.format(day)

            binding.itemDayText.text = day.date.toString()

            if(date.equals(todayDate)){
                binding.itemDayText.setBackgroundColor(Color.parseColor("#AFE5C6"))
            }

            // 이미지
//            if(day.date==17){
//                binding.itemDayText.visibility = View.GONE
//                binding.itemImage.visibility = View.VISIBLE
//                binding.itemImage.setImageResource(R.drawable.sample)
//            }

        }

    }

}