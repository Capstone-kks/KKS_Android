package com.example.kks.record

import android.util.Log
import android.widget.Toast
import com.example.kks.getRetrofit
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response

class LikeRecordService {
    private lateinit var likeRecordView: LikeRecordView

    // 외부 접근
    fun setLikeRecordView(likeRecordView: LikeRecordView){
        this.likeRecordView = likeRecordView
    }

    fun postLikeRecord(likeRecordReq:LikeRecordReq){
        val writeRecordService = getRetrofit().create(RecordRetrofitInterface::class.java)

        likeRecordView.onLikeRecordLoading()

        writeRecordService.postLikeRecord(likeRecordReq)
            .enqueue(object:retrofit2.Callback<LikeRecordResponse>{
                override fun onResponse(call: Call<LikeRecordResponse>, response: Response<LikeRecordResponse>)
                {
                    Log.d("response.body()",response.body().toString())

                    val resp = response.body()!!
                    when(resp.code){
                        1000->{
                            likeRecordView.onLikeRecordSuccess(resp.result)
                        }else-> likeRecordView.onLikeRecordFailure(resp.code,resp.message)
                    }



                }

                override fun onFailure(call: Call<LikeRecordResponse>, t: Throwable) {
                    likeRecordView.onLikeRecordFailure(400,"네트워크 오류입니다.")
                }

            })
    }

}