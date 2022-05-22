package com.example.kks.comment

import retrofit2.Call
import retrofit2.http.*

interface CommentRetrofitInterface {

    // 댓글 작성
    @GET("/api/comment/{recordIdx}")
    fun getCommentList(
        @Path("recordIdx") recordIdx:Int
    ):Call<CommentListResponse>


    // 댓글 수정
//    @PATCH
//
//
//    // 댓글 삭제
//    @DELETE




}