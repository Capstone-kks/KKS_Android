package com.example.kks.comment

import retrofit2.Call
import retrofit2.http.*

interface CommentRetrofitInterface {

    // 댓글 조회
    @GET("/api/comment/{recordIdx}")
    fun getCommentList(
        @Path("recordIdx") recordIdx:Int
    ):Call<CommentListResponse>




    // 댓글 삭제
    @DELETE("/api/comment/{commentIdx}")
    fun deleteComment(
    @Path("commentIdx") commentIdx:Int
    ):Call<DeleteCommentResponse>

    // 댓글 작성
    @POST("/api/comment/creation")
    fun postComment(
        @Body postCommentReq: PostCommentReq
    ):Call<PostCommentResponse>



}