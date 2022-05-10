package com.example.kks.feed

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
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
    private lateinit var records:ArrayList<Record>  // 더미 데이터

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

        records=ArrayList<Record>()
        records.add(Record(1,2,"홍길동","요기고 전시회",2,3.0,"요시고 전시회에 다녀왔습니다. 생각보다 볼거리가 많았던 전시회 였네요.",
        true,R.drawable.sample,"2022-03-13",3))
        records.add(Record(2,2,"사용자22","국립 현대 미술관",2,5.0,"국립 현대 미술관에 다녀왔습니다. 입장료도 비싸지 않고 굉장히 만족한 전시였습니다.",
            true,R.drawable.sample1,"2021-12-13",2))
        records.add(Record(3,2,"닉네임0","닥터스트레인지",3,4.0,"기대하던 닥터스트레인지 2가 개봉이 되자마자 보러갔다 왔습니다. ",
            true,R.drawable.sample2,"2022-05-05",1))
        initRecyclerView(records)






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
           // initRecyclerView()

        }

        // 인기순
        binding.sortHotTv.setOnClickListener {
            hotCheck=true
            recentCheck=false
            initRecentOrHotTextButton()
            sort=2
            // todo api 다시 호출
            getRecordList()
           // initRecyclerView()

        }

        // following 한 사람 글만 보기
        binding.followerCheckLl.setOnClickListener {
            isFollowingRecord =!isFollowingRecord
            initCheckFollowingButton()
            getRecordList()
         //   initRecyclerView()


        }

        // following 글만 보기
    }// end of initView()

    private fun  getRecordList(){

    }

    private fun initRecyclerView(records:ArrayList<Record>){
        // recyclerView <-> adapter 연결
        recordListAdapter = RecordListAdapter(context)
        recordListAdapter.setRecordClickListener(object : RecordListAdapter.RecordClickListener{
            override fun onItemClick(record: Record) {
                startRecordDetailActivity(record)
            }

        })
        binding.feedRecyclerView.adapter=recordListAdapter
        binding.feedRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

        recordListAdapter.addRecords(records)

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