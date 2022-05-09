package com.example.kks.feed

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kks.R
import com.example.kks.databinding.FragmentFeedBinding
import com.example.kks.record.Record
import com.example.kks.record.RecordActivity

class FeedFragment:Fragment() {

    lateinit var binding : FragmentFeedBinding
    private var sort : Int = 1 // 정렬 기준은 기본이 최신순 ( 인기순 : 2)
    private var recentCheck : Boolean = true // 최신순
    private var hotCheck : Boolean = false // 인기순
    private var isFollowingRecord : Boolean = false // following 글들만 보기

    private lateinit var recordListAdapter : RecordListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedBinding.inflate(inflater,container,false)

//        binding.button.setOnClickListener {
//            startActivity(Intent(context, RecordActivity::class.java))
//        }

        initView() // view 초기화

        binding.refreshLayout.setOnClickListener{
            // todo 서버에서 데이터 reload


            binding.refreshLayout.isRefreshing=false
        }
        return binding.root
    }// end of onCreateView


    private fun initView(){

        // 최신순
        binding.sortRecentTv.setOnClickListener {
            recentCheck=true
            hotCheck=false
            initRecentOrHotTextButton()
            sort=1
            // todo api 다시 호출
            getRecordList()
            initRecyclerView()

        }

        // 인기순
        binding.sortHotTv.setOnClickListener {
            hotCheck=true
            recentCheck=false
            initRecentOrHotTextButton()
            sort=2
            // todo api 다시 호출
            getRecordList()
            initRecyclerView()

        }

        // following 한 사람 글만 보기
        binding.followerCheckLl.setOnClickListener {
            isFollowingRecord =!isFollowingRecord
            initCheckFollowingButton()
            getRecordList()
            initRecyclerView()


        }

        // following 글만 보기
    }// end of initView()

    private fun  getRecordList(){

    }

    private fun initRecyclerView(){
        // recyclerView <-> adapter 연결
        recordListAdapter = RecordListAdapter(context)
        recordListAdapter.setRecordClickListener(object : RecordListAdapter.RecordClickListener{
            override fun onItemClick(record: Record) {
                startRecordDetailActivity(record)
            }

        })

    }

    private fun startRecordDetailActivity(record:Record){
        val intent=Intent(context,RecordActivity::class.java)
        intent.putExtra("recordIdx",record.recordIdx) // 질문 고유 번호 넘김
        startActivity(intent)
    }

    private fun initRecentOrHotTextButton(){
        if(recentCheck){
            binding.sortRecentTv.setTextColor(Color.parseColor("#474A57"))
            binding.sortRecentTv.isEnabled = false
        }else{
            binding.sortRecentTv.setTextColor(Color.parseColor("#C4C4C4"))
            binding.sortRecentTv.isEnabled=true
        }

        if(hotCheck){
            binding.sortHotTv.setTextColor(Color.parseColor("#474A57"))
            binding.sortHotTv.isEnabled=false
        }else{
            binding.sortHotTv.setTextColor(Color.parseColor("#C4C4C4"))
            binding.sortHotTv.isEnabled=true
        }
    }

    private fun initCheckFollowingButton(){
        if(isFollowingRecord){
            binding.followerIsCheckIv.setImageResource(R.drawable.ic_check_ok)
        }else{
            binding.followerIsCheckIv.setImageResource(R.drawable.ic_check_no)
        }
    }


}// end of class