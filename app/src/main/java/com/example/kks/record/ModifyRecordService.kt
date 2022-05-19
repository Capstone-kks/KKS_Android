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

    fun getModifyRecord(record:RecordReq){
        val writeRecordService = getRetrofit().create(RecordRetrofitInterface::class.java)

        modifyRecordView.onGetModifyRecordLoading()

        // todo api 호출 작성
    }

}