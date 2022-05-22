package com.example.kks.feed

import android.util.Log
import com.example.kks.getRetrofit
import retrofit2.Call
import retrofit2.Response

class FeedService {
    private lateinit var feedView:FeedView // 선언

    // 외부 접근
    fun setFeedService(feedView: FeedView){
        this.feedView = feedView
    }

    // 서버에서 해당 데이터 (feed) 에 대한 내용 받아오는 함수
    fun getFeed(loginUserId:String,sort:Int,isFollowCheck:Boolean){
        val feedService = getRetrofit().create(FeedRetrofitInterface::class.java)

        feedView.onGetFeedLoading()

        feedService.getFeedList(loginUserId,sort ,isFollowCheck )
            .enqueue(object :retrofit2.Callback<FeedListResponse>{
                override fun onResponse(call: Call<FeedListResponse>, response: Response<FeedListResponse>) {

                    Log.d("why",response.toString());
                    val resp = response.body()!!

                    when(resp.code){
                        1000->{
                            feedView.onGetFeedSuccess(resp.result)
                        }else->feedView.onGetFeedFailure(resp.code, resp.message)
                    }
                }

                override fun onFailure(call: Call<FeedListResponse>, t: Throwable) {
                    feedView.onGetFeedFailure(400,"네트워크 오류가 발생했습니다.")
                }

            })
    }
}