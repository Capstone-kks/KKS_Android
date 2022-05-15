package com.example.kks.record

interface WriteRecordView {
    fun onGetWriteRecordLoading() // 로딩중 (서버에서 데이터 가져오는중)
    fun onGetWriteRecordSuccess(result: WriteRecordResult) // 데이터 가져오기 성공시
    fun onGetWriteRecordFailure(code:Int, message:String) // 데이터 가져오기 실패시
}