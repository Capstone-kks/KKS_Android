package com.example.kks.record

import android.util.Log
import android.widget.Toast
import com.example.kks.getRetrofit
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response

class DeleteRecordService {
    private lateinit var deleteRecordView:DeleteRecordView

    // 외부 접근
    fun setDeleteRecordView(deleteRecordView: DeleteRecordView){
        this.deleteRecordView = deleteRecordView
    }

    fun getDeleteRecord(userId:String,recordIdx:Int){
        val deleteRecordService = getRetrofit().create(RecordRetrofitInterface::class.java)

        deleteRecordView.onGetDeletedLoading()

        deleteRecordService.getDeleteRecord(userId,recordIdx)
            .enqueue(object:retrofit2.Callback<DeleteRecordResponse>{
                override fun onResponse(call: Call<DeleteRecordResponse>, response: Response<DeleteRecordResponse>)
                {
                    Log.d("response.body()",response.body().toString())

                    val resp = response.body()!!
                    when(resp.code){
                        1000->{
                            deleteRecordView.onGetDeleteRecordSuccess(resp.result)
                        }else-> deleteRecordView.onGetDeleteRecordFailure(resp.code,resp.message)
                    }



                }

                override fun onFailure(call: Call<DeleteRecordResponse>, t: Throwable) {
                    deleteRecordView.onGetDeleteRecordFailure(400,"네트워크 오류입니다.")
                }

            })
    }

}