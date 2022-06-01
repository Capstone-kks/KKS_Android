package com.example.kks.comment

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kks.databinding.CommentListItemBinding
import com.example.kks.getUserIdx

class CommentsForRecordAdapter(val context: Context):RecyclerView.Adapter<CommentsForRecordAdapter.ViewHolder>() {

    private val commentList = ArrayList<CommentList>()
    private val loginUserId= getUserIdx(context)

    interface CommentsItemClickListener{
        fun onDeleteButton(recordIdx:Int)
        fun goProfileActivity(recordIdx: String)

    }
    private lateinit var commentsItemClickListener: CommentsItemClickListener

    fun setCommentsClickListener(commentsItemClickListener: CommentsItemClickListener){
        this.commentsItemClickListener= commentsItemClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding : CommentListItemBinding = CommentListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(commentList[position])
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addComments(comments:ArrayList<CommentList>){
        this.commentList.clear()
        this.commentList.addAll(comments)
        notifyDataSetChanged()
    }


    inner class ViewHolder(val binding:CommentListItemBinding):RecyclerView.ViewHolder(binding.root){


        fun bind(comment: CommentList){

            binding.commentNickName.text= comment.nickName // 닉네임
            binding.commentPostDate.text = comment.postDate// 작성 날짜
            binding.commentContent.text = comment.content // 댓글 내용

            if(comment.userId==loginUserId){
                binding.deleteCommentTv.visibility = View.VISIBLE
            }

            // 삭제버튼 클릭시
            binding.deleteCommentTv.setOnClickListener {

                commentsItemClickListener.onDeleteButton(comment.commentIdx)
            }

            binding.commentNickName.setOnClickListener {
                var userId = comment.userId
                commentsItemClickListener.goProfileActivity(userId)

            }



        }// end of bind()

    }


}