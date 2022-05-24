package com.example.kks.comment

interface PostCommentView {
    fun onGetPostCommentLoading() // 로딩중 (서버에서 데이터 가져오는중)
    fun onGetPostCommentSuccess(result: String) // 데이터 가져오기 성공시
    fun onGetPostCommentFailure(code:Int, message:String) // 데이터 가져오기 실패시
}