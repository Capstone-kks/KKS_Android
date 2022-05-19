package com.example.kks.record

interface RecordView {
    fun onRecordLoading() // 로딩중 (서버에서 데이터 가져오는중)
    fun onRecordSuccess(result: Record) // 데이터 가져오기 성공시
    fun onRecordFailure(code:Int, message:String) // 데이터 가져오기 실패시
}