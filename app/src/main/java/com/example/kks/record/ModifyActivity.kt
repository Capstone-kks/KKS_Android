package com.example.kks.record

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kks.databinding.ActivityModifyBinding
import com.example.kks.getUserIdx

class ModifyActivity:AppCompatActivity() {

    lateinit var binding : ActivityModifyBinding
    private var userIdx: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userIdx= getUserIdx(this) // 현재 로그인한 사용자 id

        var recordTitle =intent.getStringExtra("title") // 제목
        var recordContent = intent.getStringExtra("content") // 내용
        var rate = intent.getFloatExtra("rate", 0.0F) // 평점


        binding.titleEt.setText(recordTitle)
        binding.contentEt.setText(recordContent)
        binding.ratingBar.rating = rate

        binding.recordSaveButton.setOnClickListener {
            Toast.makeText(this,"수정을 완료했습니다.",Toast.LENGTH_SHORT).show()
            finish()
        }
        binding.cancelTv.setOnClickListener {
            finish()
        }




    }// end of onCreate

}// end of class