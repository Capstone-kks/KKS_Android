package com.example.kks.comment

import android.util.Log
import android.widget.Toast
import com.example.kks.getRetrofit
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response

class CommentService {
    private lateinit var commentView: CommentView

    // 외부 접근
    fun setCommentView(commentView: CommentView){
        this.commentView = commentView
    }

    fun getComments(recordIdx:Int){
        val deleteRecordService = getRetrofit().create(CommentRetrofitInterface::class.java)

        commentView.onGetCommentLoading()

        deleteRecordService.getCommentList(recordIdx)
            .enqueue(object:retrofit2.Callback<CommentListResponse>{
                override fun onResponse(call: Call<CommentListResponse>, response: Response<CommentListResponse>)
                {
                    Log.d("response.body()",response.body().toString())

                    val resp = response.body()!!
                    when(resp.code){
                        1000->{
                            commentView.onGetCommentSuccess(resp.result)
                        }else-> commentView.onGetCommentFailure(resp.code,resp.message)
                    }



                }

                override fun onFailure(call: Call<CommentListResponse>, t: Throwable) {
                    commentView.onGetCommentFailure(400,"네트워크 오류입니다.")
                }

            })
    }

}