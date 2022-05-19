package com.example.kks.record

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.kks.R
import com.example.kks.databinding.ActivityRecordBinding


// 작성한 글을 볼수있는 화면
class RecordActivity : AppCompatActivity(),RecordView {// end of class

    private lateinit var binding : ActivityRecordBinding
    private var isLike = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        // 글 삭제 처리
        binding.deleteButton.setOnClickListener {
            val builder = AlertDialog.Builder(this).create()
            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_delete,null)

            builder?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            builder.setCancelable(false)
            builder.setCanceledOnTouchOutside(false)

            val approve = dialogView.findViewById<Button>(R.id.dialog_approve_btn)
            approve.setOnClickListener {
                // todo 글 삭제 api 호출

                builder.dismiss()
            }

            val cancel = dialogView.findViewById<Button>(R.id.dialog_cancel_btn)
            cancel.setOnClickListener {
                builder.dismiss()
            }

            builder.setView(dialogView)
            builder.show()
        }


        // 글 수정 처리
        binding.editButton.setOnClickListener {
            val intent = Intent(this,ModifyActivity::class.java)
            intent.putExtra("title",binding.recordTitle.text) // 제목
            intent.putExtra("content",binding.recordContent.text) // 내용
            intent.putExtra("rate",binding.ratingBar.rating) // 평점
            intent.putExtra("imgUrl",binding.recordPicture.id) // 이미지
            //  intent.putExtra("category",binding.)
            startActivity(intent)

        }


        binding.backIv.setOnClickListener {
            finish()
        }


    }// end of onCreate

    override fun onRecordLoading() {
        Log.d("글확인/API","로딩중...")
    }

    override fun onRecordSuccess(result: Record) {
        Log.d("글확인/API","성공")
        //todo 화면에 정보 띄움.

    }

    override fun onRecordFailure(code: Int, message: String) {
        when(code){
            400->Log.d("글확인/API",message)
        }
    }


}