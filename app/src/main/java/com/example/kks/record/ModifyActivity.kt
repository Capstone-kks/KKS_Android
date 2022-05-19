package com.example.kks.record

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kks.R
import com.example.kks.databinding.ActivityModifyBinding
import com.example.kks.getUserIdx

class ModifyActivity:AppCompatActivity() {

    lateinit var binding : ActivityModifyBinding
    private var userId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId= getUserIdx(this) // 현재 로그인한 사용자 id(string)

        var recordTitle =intent.getStringExtra("title") // 제목
        var recordContent = intent.getStringExtra("content") // 내용
        var rate = intent.getFloatExtra("rate", 0.0F) // 평점\
        var imgUrl = intent.getIntExtra("imgUrl",0) // 이미지


        binding.titleEt.setText(recordTitle)
        binding.contentEt.setText(recordContent)
        binding.ratingBar.rating = rate
        binding.imageView.setImageResource(R.drawable.sample)

        binding.recordSaveButton.setOnClickListener {
            Toast.makeText(this,"수정을 완료했습니다.",Toast.LENGTH_SHORT).show()
            finish()
        }
        binding.cancelTv.setOnClickListener {
            finish()
        }

        setupSpinner()



    }// end of onCreate


    private fun setupSpinner(){
        val groups = resources.getStringArray(R.array.pick_category)
        val adapter = object : ArrayAdapter<String>(this, R.layout.item_spinner){
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent)
                if(position==count){
                    // 마지막 포지션의 textView를 힌트용으로 사용
                    (v.findViewById<View>(R.id.item_spinner_tv) as TextView).text=""
                    // 아이템의 마지막 값을 불려와 hint 로 추가
                    (v.findViewById<View>(R.id.item_spinner_tv) as TextView).hint = getItem(count)
                }

                return v
            }

            override fun getCount(): Int {
                // 마지막은 힌트 용이므로 1 뺌.
                return super.getCount()-1
            }
        }
        // 아이템 추가
        adapter.addAll(groups.toMutableList())
        // hint로 사용할 문구를 마지막 아이템에 추가
        adapter.add("카테고리 선택하기")
        // 어댑터 연결
        binding.categorySpinner.adapter = adapter
        // 스피너 초기값을 마지막 아이템으로 설정
        binding.categorySpinner.setSelection(adapter.count)

//        // droplist를 스피너와 간격을 두고 나오게 함 -> 아이템 크기 = 125px
//        binding.categorySpinner.dropDownVerticalOffset = dipToPixels(45f).toInt()
    } // end of setupSpinner

}// end of class