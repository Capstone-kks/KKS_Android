package com.example.kks.comment

interface CommentView {
    fun onGetCommentLoading() // 로딩중 (서버에서 데이터 가져오는중)
    fun onGetCommentSuccess(result: ArrayList<CommentList>) // 데이터 가져오기 성공시
    fun onGetCommentFailure(code:Int, message:String) // 데이터 가져오기 실패시
}