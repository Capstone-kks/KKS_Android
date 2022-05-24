package com.example.kks.comment

interface DeleteCommentView {
    fun onGetDeleteCommentLoading() // 로딩중 (서버에서 데이터 가져오는중)
    fun onGetDeleteCommentSuccess(result: String) // 데이터 가져오기 성공시
    fun onGetDeleteCommentFailure(code:Int, message:String) // 데이터 가져오기 실패시
}