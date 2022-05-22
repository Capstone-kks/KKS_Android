package com.example.kks.record

import android.util.Log
import android.widget.Toast
import com.example.kks.getRetrofit
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response

class ModifyRecordService {
    private lateinit var modifyRecordView: ModifyRecordView

    // 외부 접근
    fun setModifyRecordView(modifyRecordView: ModifyRecordView){
        this.modifyRecordView = modifyRecordView
    }

    fun getModifyRecord(userId:String,recordIdx:Int,modifyRecordReq: ModifyRecordReq){
        val modifyRecordService = getRetrofit().create(RecordRetrofitInterface::class.java)

        modifyRecordView.onGetModifyRecordLoading()


        modifyRecordService.getModifyRecord(userId,recordIdx,modifyRecordReq)
            .enqueue(object :retrofit2.Callback<ModifyRecordResponse>{
                override fun onResponse(call: Call<ModifyRecordResponse>, response: Response<ModifyRecordResponse>) {

                    val resp = response.body()!!
                    when(resp.code){
                        1000->{
                            modifyRecordView.onGetModifyRecordSuccess(resp.result)
                        }else->modifyRecordView.onGetModifyRecordFailure(resp.code,resp.message)
                    }
                }

                override fun onFailure(call: Call<ModifyRecordResponse>, t: Throwable) {
                    modifyRecordView.onGetModifyRecordFailure(400,"네트워크 오류가 발생했습니다.")
                }

            })
    }

}