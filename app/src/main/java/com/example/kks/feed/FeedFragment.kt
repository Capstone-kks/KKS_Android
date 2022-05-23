package com.example.kks.feed

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kks.R
import com.example.kks.databinding.FragmentFeedBinding
import com.example.kks.getUserIdx
import com.example.kks.record.DetailRecordActivity

class FeedFragment:Fragment(),FeedView {// end of class

    lateinit var binding : FragmentFeedBinding
    private var sort : Int = 1 // 정렬 기준은 기본이 최신순 ( 인기순 : 2)
    private var recentCheck : Boolean = true // 최신순
    private var hotCheck : Boolean = false // 인기순
    private var isFollowingRecord : Boolean = false // following 글들만 보기
    private var userId : String =""// 로그인한 사용자 id


    private lateinit var recordListAdapter : RecordListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedBinding.inflate(inflater,container,false)

        userId= context?.let{ getUserIdx(it)}!!
        Log.d("userId: ",userId)

        // default 값은 최신순 : sort = 1 & 모든 글 보기 isFollowingRecord=false

        initView() // view 초기화





        binding.refreshLayout.setOnRefreshListener{

            getRecordList()
            initRecyclerView()


            binding.refreshLayout.isRefreshing=false
        }
        return binding.root
    }// end of onCreateView

    override fun onStart() {
        super.onStart()


        getRecordList()
        initRecyclerView()

    }

    private fun initView(){

        // 최신순
        binding.sortRecentTv.setOnClickListener {
            recentCheck=true
            hotCheck=false
            initRecentOrHotTextButton()
            sort=1

            getRecordList()
            initRecyclerView()

        }

        // 인기순
        binding.sortHotTv.setOnClickListener {
            hotCheck=true
            recentCheck=false
            initRecentOrHotTextButton()
            sort=2

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
        val feedService = FeedService()
        feedService.setFeedService(this)
        Log.d("apple:",userId)
        Log.d("apple:",sort.toString())
        Log.d("apple:",isFollowingRecord.toString())
        feedService.getFeed(userId,sort,isFollowingRecord)

    }

    private fun initRecyclerView(){
        // recyclerView <-> adapter 연결

        recordListAdapter = RecordListAdapter(context)
        recordListAdapter.setRecordClickListener(object : RecordListAdapter.RecordClickListener{
            override fun onItemClick(record: FeedResponse) {
                startRecordDetailActivity(record.recordIdx)
            }

        })
        binding.feedRecyclerView.adapter=recordListAdapter
        binding.feedRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

    }

    private fun startRecordDetailActivity(recordIdx:Int){
        val intent=Intent(context,DetailRecordActivity::class.java)
        intent.putExtra("recordIdx",recordIdx) // 질문 고유 번호 넘김
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

    override fun onGetFeedLoading() {
        Log.d("Feed API","로딩중...")
    }

    override fun onGetFeedSuccess(result: ArrayList<FeedResponse>) {
        Log.d("Feed API","성공")
        if(result!=null){ // 글이 있으면 어댑터에 추가
            recordListAdapter.addRecords(result);


        }
    }

    override fun onGetFeedFailure(code: Int, message: String) {
        when(code){
            400-> Log.d("Feed API", message)
        }
    }


}