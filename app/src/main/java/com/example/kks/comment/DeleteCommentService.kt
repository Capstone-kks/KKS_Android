package com.example.kks.comment

import android.util.Log
import android.widget.Toast
import com.example.kks.getRetrofit
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response

class DeleteCommentService {
    private lateinit var deleteCommentView: DeleteCommentView

    // 외부 접근
    fun setDeleteCommentView(deleteCommentView: DeleteCommentView){
        this.deleteCommentView = deleteCommentView
    }

    fun getDeleteComment(commentIdx:Int){
        val deleteRecordService = getRetrofit().create(CommentRetrofitInterface::class.java)

        deleteCommentView.onGetDeleteCommentLoading()

        deleteRecordService.deleteComment(commentIdx)
            .enqueue(object:retrofit2.Callback<DeleteCommentResponse>{
                override fun onResponse(call: Call<DeleteCommentResponse>, response: Response<DeleteCommentResponse>)
                {
                    Log.d("response.body()",response.body().toString())

                    val resp = response.body()!!
                    when(resp.code){
                        1000->{
                            deleteCommentView.onGetDeleteCommentSuccess(resp.result)
                        }else-> deleteCommentView.onGetDeleteCommentFailure(resp.code,resp.message)
                    }



                }

                override fun onFailure(call: Call<DeleteCommentResponse>, t: Throwable) {
                    deleteCommentView.onGetDeleteCommentFailure(400,"네트워크 오류입니다.")
                }

            })
    }

}