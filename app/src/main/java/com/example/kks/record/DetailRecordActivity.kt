package com.example.kks.record

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.kks.R
import com.example.kks.comment.*
import com.example.kks.databinding.ActivityDetailRecordBinding

import com.example.kks.getUserIdx
import com.example.kks.saveCategoryId


// 작성한 글을 볼수있는 화면
class DetailRecordActivity : AppCompatActivity(),RecordView ,DeleteRecordView,CommentView,DeleteCommentView,PostCommentView,LikeRecordView{// end of class

    private lateinit var binding : ActivityDetailRecordBinding
    private var isLike = false
    private var recordIdx :Int =0
    private var userId:String=""
    private var writerId : String =""
    private lateinit var _record:Record

    private lateinit var commentsForRecordAdapter: CommentsForRecordAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId= getUserIdx(this)

        recordIdx=intent.getIntExtra("recordIdx",0)
        Log.d("check:",recordIdx.toString())

        getRecordDetails() // API 호출

        binding.likeImage.setOnClickListener {
            isLike =!isLike

            if(isLike){
             //   Toast.makeText(this,"해당 글에 좋아요를 눌렀습니다.",Toast.LENGTH_SHORT).show()
                binding.likeImage.setImageResource(R.drawable.ic_liked)
                postLikeRecord()
            }else{
               // Toast.makeText(this,"해당 글에 좋아요를 취소했습니다.",Toast.LENGTH_SHORT).show()
                binding.likeImage.setImageResource(R.drawable.ic_like)
                postLikeRecord()
            }

        }
        // 글 삭제 버튼
        binding.deleteButton.setOnClickListener {
            val builder = AlertDialog.Builder(this).create()
            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_delete,null)

            builder?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            builder.setCancelable(false)
            builder.setCanceledOnTouchOutside(false)

            val approve = dialogView.findViewById<Button>(R.id.dialog_approve_btn)
            approve.setOnClickListener {
                // 삭제 api
                val deleteRecordService=DeleteRecordService()
                deleteRecordService.setDeleteRecordView(this)
                deleteRecordService.getDeleteRecord(userId,recordIdx)


                builder.dismiss()
                finish() // feed 목록으로
            }

            val cancel = dialogView.findViewById<Button>(R.id.dialog_cancel_btn)
            cancel.setOnClickListener {
                builder.dismiss()
            }

            builder.setView(dialogView)
            builder.show()
        }


        // 글 수정 버튼
        binding.editButton.setOnClickListener {
            val intent = Intent(this,ModifyActivity::class.java)
            intent.putExtra("recordIdx",_record.recordIdx) // 글 id
            intent.putExtra("categoryId",_record.categoryId) // 카테고리 id
            intent.putExtra("postPublic",_record.postPublic) // 공개 여부
            intent.putExtra("title",_record.title) // 제목
            intent.putExtra("content",_record.content) // 내용
            intent.putExtra("rate",_record.rate) // 평점
            intent.putExtra("imgUrl",_record.imgUrl) // 이미지
            startActivity(intent)

        }

        binding.commentButton.setOnClickListener {

            val commentContent = binding.commentEt.text.toString()

            if(commentContent==""){
                Toast.makeText(this,"댓글을 입력해 주세요.",Toast.LENGTH_SHORT).show()
            }else{

                postComment(PostCommentReq(recordIdx,userId,commentContent))

            }
        }


        binding.backIv.setOnClickListener {
            finish()
        }

        binding.refreshLayout.setOnRefreshListener {
            getRecordDetails()

            binding.refreshLayout.isRefreshing=false
        }

        binding.profileLayout.setOnClickListener {
            val goProfileDialogFragment = GoProfileDialogFragment(this,writerId)
            goProfileDialogFragment.show(supportFragmentManager,goProfileDialogFragment.tag)

        }

    }// end of onCreate



    override fun onStart() {
        super.onStart()
        getRecordDetails() // API 호출
    }

    private fun postLikeRecord(){
        val likeRecordService = LikeRecordService()
        likeRecordService.setLikeRecordView(this)
        likeRecordService.postLikeRecord(LikeRecordReq(recordIdx,userId))
    }

    private fun getRecordDetails(){
        val recordService=DetailRecordService()
        recordService.setRecordView(this)
        recordService.getRecord(recordIdx,userId)

    }

    override fun onRecordLoading() {
        Log.d("글확인/API","로딩중...")

    }

    override fun onRecordSuccess(record: Record) {
        Log.d("글확인/API","성공")
        Log.d("글확인/API",record.toString())

        _record= record

        writerId = record.userId

        getCommentsForRecord(record.recordIdx)
        initRecyclerView()

        if(record.userId==userId){
            binding.editButton.visibility= View.VISIBLE
            binding.deleteButton.visibility=View.VISIBLE
        }
        var category=record.categoryId
        var categoryName=""
        when(category){
            1->categoryName="공연"
            10->categoryName="도서"
            11->categoryName="드라마"
            12->categoryName="연극/뮤지컬"
            13->categoryName="영화"
            14->categoryName="음악"
            15->categoryName="전시"
            16->categoryName="기타"
        }
        binding.recordDate.text = record.postDate.subSequence(0,10)
        binding.recordCategory.text = categoryName // 카테고리
        binding.recordTitle.text=record.title // 제목
        Glide.with(this).load(record.imgUrl).into(binding.recordPicture) // 사진

        Glide.with(this).load(record.profileImg).into(binding.profileImg) // 작성자 프로필 사진
        binding.userName.text = record.writerName // 작성자 이름

        binding.ratingBar.rating=record.rate // 평점
        binding.recordContent.text=record.content // 내용

        if(record.isLiked=="Y"){
            binding.likeImage.setImageResource(R.drawable.ic_liked)
            isLike=true
        }

        saveCategoryId(this,record.categoryId)


    }
    private fun getCommentsForRecord(_recordIdx:Int){
        val commentService = CommentService()
        commentService.setCommentView(this)
        commentService.getComments(_recordIdx)

    }

    private fun initRecyclerView(){
        commentsForRecordAdapter= CommentsForRecordAdapter(this)
        binding.commentRecyclerView.adapter = commentsForRecordAdapter
        binding.commentRecyclerView.layoutManager =  LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
    }

    private fun postComment(postCommentReq: PostCommentReq){
        val postCommentService = PostCommentService()
        postCommentService.setPostCommentView(this)
        postCommentService.getPostComment(postCommentReq)
    }

    // ---------- 글 조회 -------------------

    override fun onRecordFailure(code: Int, message: String) {
        when(code){
            400->Log.d("글확인/API",message)
        }
    }

    override fun onGetDeletedLoading() {
        Log.d("글삭제/API","로딩중...")
    }

    override fun onGetDeleteRecordSuccess(result: String) {
        Log.d("글삭제/API","성공")

    }

    override fun onGetDeleteRecordFailure(code: Int, message: String) {
        when(code){
            400->Log.d("글삭제/API",message)
        }
    }



    // -------------- 댓글 목록 조회 ---------------------

    override fun onGetCommentLoading() {
        Log.d("댓글목록/API","로딩중...")
    }

    override fun onGetCommentSuccess(result: ArrayList<CommentList>) {
        Log.d("댓글목록/API","성공")
        commentsForRecordAdapter.addComments(result)
        commentsForRecordAdapter.setCommentsClickListener(object : CommentsForRecordAdapter.CommentsItemClickListener{
            override fun onDeleteButton(commentIdx: Int) {

                getDeleteComment(commentIdx)

                Handler(Looper.getMainLooper()).postDelayed({
                    getCommentsForRecord(recordIdx)
                    initRecyclerView()
                },500)

            }

        })
    }

    override fun onGetCommentFailure(code: Int, message: String) {
        when(code){
            400->Log.d("댓글목록/API",message)
        }
    }

    private fun getDeleteComment(commentIdx:Int){
        val deleteCommentService = DeleteCommentService()
        deleteCommentService.setDeleteCommentView(this)
        deleteCommentService.getDeleteComment(commentIdx)

    }




    //-------------  댓글 삭제 ------------------------------

    override fun onGetDeleteCommentLoading() {
        Log.d("댓글삭제/API","로딩중...")
    }

    override fun onGetDeleteCommentSuccess(result: String) {
        Log.d("댓글삭제/API","성공")
        Toast.makeText(this,result,Toast.LENGTH_SHORT).show()
    }

    override fun onGetDeleteCommentFailure(code: Int, message: String) {
        when(code){
            400->Log.d("댓글삭제/API",message)
        }
    }

    override fun onGetPostCommentLoading() {
        Log.d("댓글작성/API","로딩중...")
    }

    override fun onGetPostCommentSuccess(result: String) {
        Log.d("댓글작성/API","성공")
        getRecordDetails()
        binding.commentEt.text.clear()
        Toast.makeText(this,result,Toast.LENGTH_SHORT).show()
    }

    override fun onGetPostCommentFailure(code: Int, message: String) {
        when(code){
            400->Log.d("댓글작성/API",message)
        }
    }

    override fun onLikeRecordLoading() {
        Log.d("글좋아요/API","로딩중..")
    }

    override fun onLikeRecordSuccess(result: String) {
        Log.d("글좋아요/API","성공")
        Toast.makeText(this,result,Toast.LENGTH_SHORT).show()
    }

    override fun onLikeRecordFailure(code: Int, message: String) {
        when(code){
            400->Log.d("글좋아요/API",message)
        }
    }


}