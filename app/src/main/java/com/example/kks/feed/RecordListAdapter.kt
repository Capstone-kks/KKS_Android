package com.example.kks.feed

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kks.databinding.RecordListItemBinding
import com.example.kks.record.Record

class RecordListAdapter(val context: Context?):RecyclerView.Adapter<RecordListAdapter.ViewHolder>() {

    private val recordList = ArrayList<FeedResponse>()

    // 클릭 인터페이스 정의
    interface RecordClickListener{
        fun onItemClick(record:FeedResponse)
    }

    private lateinit var recordClickListener : RecordClickListener

    fun setRecordClickListener(recordClickListener : RecordClickListener){
        this.recordClickListener = recordClickListener

    }


    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int): RecordListAdapter.ViewHolder {
        val binding : RecordListItemBinding = RecordListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecordListAdapter.ViewHolder, position: Int) {
       holder.bind(recordList[position])
        //recyclerView 의 각 아이템을 클릭할때
        holder.itemView.setOnClickListener {
            recordClickListener.onItemClick(recordList[position])
     //       Toast.makeText(context,position.toString(),Toast.LENGTH_SHORT).show()

        }

    }

    override fun getItemCount(): Int {
        return recordList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addRecords(records:ArrayList<FeedResponse>){
        this.recordList.clear()
        this.recordList.addAll(records)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding:RecordListItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(record: FeedResponse){
            Glide.with(context!!).load(record.imgUrl).into(binding.itemRecordIv) // 글 이미지

            binding.itemRecordTitleTv.text = record.title // 글 제목
            binding.itemRecordContentTv.text = record.content // 글 내용
            binding.itemRecordDate.text = record.postDate // 글 작성 날짜
            binding.itemRecordWriterTv.text = record.nickName // 글 작성자

        }
    }


}