package com.example.kks.record

import android.util.Log
import android.widget.Toast
import com.example.kks.getRetrofit
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response

class WriteRecordService {
    private lateinit var writeRecordView:WriteRecordView

    // 외부 접근
    fun setWriteRecordView(writeRecordView: WriteRecordView){
        this.writeRecordView = writeRecordView
    }

    fun getWriteRecord(record:RecordReq){
        val writeRecordService = getRetrofit().create(RecordRetrofitInterface::class.java)

        writeRecordView.onGetWriteRecordLoading()

        writeRecordService.postRecord(record)
            .enqueue(object:retrofit2.Callback<PostRecordResponse>{
                override fun onResponse(call: Call<PostRecordResponse>, response: Response<PostRecordResponse>)
                {
                    Log.d("response.body()",response.body().toString())

                    val resp = response.body()!!
                    when(resp.code){
                        1000->{
                            writeRecordView.onGetWriteRecordSuccess(resp.result)
                        }else-> writeRecordView.onGetWriteRecordFailure(resp.code,resp.message)
                    }



                }

                override fun onFailure(call: Call<PostRecordResponse>, t: Throwable) {
                   writeRecordView.onGetWriteRecordFailure(400,"네트워크 오류입니다.")
                }

            })
    }

}