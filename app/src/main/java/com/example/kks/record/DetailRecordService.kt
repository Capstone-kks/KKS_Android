package com.example.kks.record

import android.util.Log
import android.widget.Toast
import com.example.kks.getRetrofit
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response

class DetailRecordService {
    private lateinit var recordView: RecordView

    // 외부 접근
    fun setRecordView(recordView: RecordView){
        this.recordView = recordView
    }

    fun getRecord(recordIdx:Int,userId:String){
        val recordService = getRetrofit().create(RecordRetrofitInterface::class.java)

        recordView.onRecordLoading()

        recordService.getRecordDetail(recordIdx,userId)
            .enqueue(object : retrofit2.Callback<DetailRecordResponse>{
                override fun onResponse(call: Call<DetailRecordResponse>, response: Response<DetailRecordResponse>
                ) {
                    val resp = response.body()!!
                    when(resp.code){
                        1000-> {
                            recordView.onRecordSuccess(resp.result)
                        }else->recordView.onRecordFailure(resp.code,resp.message)
                    }
                }

                override fun onFailure(call: Call<DetailRecordResponse>, t: Throwable) {
                    recordView.onRecordFailure(400,"네트워크 오류가 발생했습니다.")
                }

            } )
    }

}