package com.example.kks.comment

import android.util.Log
import android.widget.Toast
import com.example.kks.getRetrofit
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response

class PostCommentService {
    private lateinit var postCommentView: PostCommentView

    // 외부 접근
    fun setPostCommentView(postCommentView: PostCommentView){
        this.postCommentView = postCommentView
    }

    fun getPostComment(postCommentReq: PostCommentReq){
        val postCommentService = getRetrofit().create(CommentRetrofitInterface::class.java)

        postCommentView.onGetPostCommentLoading()

        postCommentService.postComment(postCommentReq)
            .enqueue(object:retrofit2.Callback<PostCommentResponse>{
                override fun onResponse(call: Call<PostCommentResponse>, response: Response<PostCommentResponse>)
                {
                    Log.d("response.body()",response.body().toString())

                    val resp = response.body()!!
                    when(resp.code){
                        1000->{
                            postCommentView.onGetPostCommentSuccess(resp.result)
                        }else-> postCommentView.onGetPostCommentFailure(resp.code,resp.message)
                    }



                }

                override fun onFailure(call: Call<PostCommentResponse>, t: Throwable) {
                    postCommentView.onGetPostCommentFailure(400,"네트워크 오류입니다.")
                }

            })
    }

}