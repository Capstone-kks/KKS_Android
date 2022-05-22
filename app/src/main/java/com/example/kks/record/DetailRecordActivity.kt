package com.example.kks.record

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kks.R
import com.example.kks.databinding.ActivityRecordBinding
import com.example.kks.getUserIdx


// 작성한 글을 볼수있는 화면
class DetailRecordActivity : AppCompatActivity(),RecordView ,DeleteRecordView{// end of class

    private lateinit var binding : ActivityRecordBinding
    private var isLike = false
    private var recordIdx :Int =0
    private var userId:String=""
    private lateinit var _record:Record



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId= getUserIdx(this)

        recordIdx=intent.getIntExtra("recordIdx",0)
        Log.d("check:",recordIdx.toString())

        getRecordDetails() // API 호출

        binding.likeImage.setOnClickListener {
            isLike =!isLike

            if(isLike){
                Toast.makeText(this,"해당 글에 좋아요를 눌렀습니다.",Toast.LENGTH_SHORT).show()
                binding.likeImage.setImageResource(R.drawable.ic_liked)
            }else{
                Toast.makeText(this,"해당 글에 좋아요를 취소했습니다.",Toast.LENGTH_SHORT).show()
                binding.likeImage.setImageResource(R.drawable.ic_like)
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


        binding.backIv.setOnClickListener {
            finish()
        }


    }// end of onCreate

    override fun onStart() {
        super.onStart()
        getRecordDetails() // API 호출
    }

    private fun getRecordDetails(){
        val recordService=RecordService()
        recordService.setRecordView(this)
        recordService.getRecord(recordIdx)

    }

    override fun onRecordLoading() {
        Log.d("글확인/API","로딩중...")
        Toast.makeText(this,"글확인API 로딩중...",Toast.LENGTH_SHORT).show()
    }

    override fun onRecordSuccess(record: Record) {
        Log.d("글확인/API","성공")
        Toast.makeText(this,"글확인API 성공...",Toast.LENGTH_SHORT).show()
        Log.d("글확인/API",record.toString())

        _record= record
        //todo comment api 호출

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
        binding.ratingBar.rating=record.rate // 평점
        binding.recordContent.text=record.content // 내용




    }

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
        Log.d("글삭제/API",result)
    }

    override fun onGetDeleteRecordFailure(code: Int, message: String) {
        when(code){
            400->Log.d("글삭제/API",message)
        }
    }


}