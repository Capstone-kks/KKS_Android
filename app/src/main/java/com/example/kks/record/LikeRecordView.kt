package com.example.kks.record

interface LikeRecordView {
    fun onLikeRecordLoading()
    fun onLikeRecordSuccess(result: String)
    fun onLikeRecordFailure(code:Int, message:String)
}