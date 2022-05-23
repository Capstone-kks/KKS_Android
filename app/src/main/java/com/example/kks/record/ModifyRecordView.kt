package com.example.kks.record

interface ModifyRecordView {
    fun onGetModifyRecordLoading() // 로딩중 (서버에서 데이터 가져오는중)
    fun onGetModifyRecordSuccess(result: String) // 데이터 가져오기 성공시
    fun onGetModifyRecordFailure(code:Int, message:String) // 데이터 가져오기 실패시
}