package com.example.kks.feed

import com.example.kks.getRetrofit
import com.example.kks.record.Record
import retrofit2.Call
import retrofit2.Response

class FeedService {
    private lateinit var feedView:FeedView // 선언

    // 외부 접근
    fun setFeedService(feedService: FeedService){
        this.feedView = feedView
    }

    // 서버에서 해당 데이터 (feed) 에 대한 내용 받아오는 함수
    fun getFeed(){
        val feedService = getRetrofit().create(FeedRetrofitInterface::class.java)

        feedView.onGetFeedLoading()

        feedService.getFeed(sort = 1,isFollowing = false)
            .enqueue(object :retrofit2.Callback<Record>{
                override fun onResponse(call: Call<Record>, response: Response<Record>) {
                    TODO("Not yet implemented")
                }

                override fun onFailure(call: Call<Record>, t: Throwable) {

                }

            })
    }
}