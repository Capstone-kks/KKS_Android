package com.example.kks.record

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.kks.R
import com.example.kks.databinding.ActivityModifyBinding
import com.example.kks.getUserIdx

class ModifyActivity:AppCompatActivity(),ModifyRecordView {// end of class

    lateinit var binding : ActivityModifyBinding
    private var userId: String = ""
    private var recordTitle:String?=""
    private var recordContent:String?=""
    private var recordImgUrl:String?=""
    private var recordRate:Float=0.0F
    private var recordIdx=0
    private var categoryId=0
    private var postPublic=0
    private var index:Int = -1
    private var categoryName:String=""
    private lateinit var resultLauncher : ActivityResultLauncher<Intent>
    private var path: Bitmap?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId= getUserIdx(this) // 현재 로그인한 사용자 id(string)

        recordIdx=intent.getIntExtra("recordIdx",0) // 글 id
        categoryId=intent.getIntExtra("categoryId",0) // 카테고리
        postPublic=intent.getIntExtra("postPublic",0) // 공개여부
        recordTitle =intent.getStringExtra("title") // 제목
        recordContent = intent.getStringExtra("content") // 내용
        recordRate = intent.getFloatExtra("rate", 0.0F) // 평점
        recordImgUrl = intent.getStringExtra("imgUrl") // 이미지


        // 초기 세팅
        binding.titleEt.setText(recordTitle)
        binding.contentEt.setText(recordContent)
        binding.ratingBar.rating = recordRate
        Glide.with(this).load(recordImgUrl).into(binding.imageView)

        when(categoryId){
            1->index=0
            12->index=1
            10->index=2
            11->index=3
            13->index=4
            14->index=5
            15->index=6
            16->index=7
        }

        if(postPublic==0){ // 비공개
            binding.publicRadioGroup.check(R.id.closeRadioButton)

        }else if(postPublic==1){ //공개
            binding.publicRadioGroup.check(R.id.openRadioButton)
        }



        //저장 버튼
        binding.recordSaveButton.setOnClickListener {

            recordTitle = binding.titleEt.text.toString() // 제목
            recordRate = binding.ratingBar.rating // 평점
            categoryName = binding.categorySpinner.selectedItem.toString() // 카테고리
            if(categoryName=="공연"){
                categoryId=1
            }else if(categoryName=="도서"){
                categoryId=10
            }else if(categoryName=="드라마"){
                categoryId=11
            }else if(categoryName=="연극/뮤지컬"){
                categoryId=12
            }else if(categoryName=="영화"){
                categoryId=13
            }else if(categoryName=="음악"){
                categoryId=14
            }else if(categoryName=="전시"){
                categoryId=15
            }else if(categoryName=="기타"){
                categoryId=16
            }
            recordContent=binding.contentEt.text.toString() // 내용

            recordRate= binding.ratingBar.rating // 평점


            Log.d("modify-title: ",recordTitle.toString())
            Log.d("modify-categoryName: ",categoryName)
            Log.d("modify-categoryIdx: ",categoryId.toString())
            Log.d("modify-rate: ",recordRate.toString())
            Log.d("modify-content: ",recordContent.toString())
            Log.d("modify-imgUrl",recordImgUrl.toString())
            Log.d("write-radioSelect: ",postPublic.toString())


            //todo 서버 api 호출
            if(recordTitle!=""&&recordContent!=""){
                getModifyRecord()
                Toast.makeText(this,"수정을 완료했습니다.",Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this,"제목과 내용을 입력해주세요.",Toast.LENGTH_SHORT).show()
            }

        }
        // 취소 버튼
        binding.cancelTv.setOnClickListener {
            finish()
        }

        setupSpinner()

        binding.publicRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.openRadioButton -> postPublic=1 // 공개
                R.id.closeRadioButton -> postPublic=0 // 비공개
            }
        }

        binding.imageAddButton.setOnClickListener { // 이미지 추가 버튼
            when{
                ContextCompat.checkSelfPermission( // 이미 권한에 동의한 경우
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED->{
                    startContentProvider()
                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)->{
                    showPermissionContextPopup()
                }
                else->{
                    requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1010)
                }

            }

        }

        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result->
            if(result.resultCode== Activity.RESULT_OK){
                result.data?.data?.let{// 결과가 제대로 들어왔을때 (이미지 주소를 잘 가져왔을때) 실행
                        uri->
                    path=null // 앨범에서 가져올때마다 초기화
                    val inputStream = uri.let{
                        contentResolver.openInputStream(
                            it
                        )
                    }
                    val bitmap= BitmapFactory.decodeStream(inputStream)
                    inputStream!!.close()
                    path=bitmap
                }
                if(result.data?.data==null){
                    Toast.makeText(this,"사진을 가져오지 못했습니다.",Toast.LENGTH_SHORT).show()
                }else{
                    binding.imageView.setImageURI(result.data!!.data)
                }
            }else return@registerForActivityResult
        }



    }// end of onCreate

    private fun getModifyRecord(){
        val modifyRecordService = ModifyRecordService()
        modifyRecordService.setModifyRecordView(this)
        modifyRecordService.getModifyRecord(userId,recordIdx,ModifyRecordReq(recordTitle!!,categoryId,recordRate,
            recordContent!!,postPublic,recordImgUrl!!))

    }

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
//        binding.categorySpinner.setSelection(adapter.count)
        binding.categorySpinner.setSelection(index)

    } // end of setupSpinner


    private fun startContentProvider(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }
    private fun showPermissionContextPopup(){
        AlertDialog.Builder(this)
            .setTitle("권한 팝업")
            .setMessage("앨범 접근에 대한 권한을 허락해주세요.")
            .setPositiveButton("동의") { _, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1010)
            }
            .create()
            .show()
    }

    override fun onGetModifyRecordLoading() {
        Log.d("글수정/API","로딩중...")
    }

    override fun onGetModifyRecordSuccess(result: String) {
        Log.d("글수정/API","성공")
        Log.d("글수정/API",result)
    }

    override fun onGetModifyRecordFailure(code: Int, message: String) {
        when(code){
            400->Log.d("글수정/API",message)
        }
    }


}

