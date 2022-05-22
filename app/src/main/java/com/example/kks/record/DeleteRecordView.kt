package com.example.kks.record

interface DeleteRecordView {
    fun onGetDeletedLoading() // 로딩중 (서버에서 데이터 가져오는중)
    fun onGetDeleteRecordSuccess(result: String) // 데이터 가져오기 성공시
    fun onGetDeleteRecordFailure(code:Int, message:String) // 데이터 가져오기 실패시
}