package com.example.kks.record

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.kks.MainActivity
import com.example.kks.R
import com.example.kks.databinding.ActivityWriteBinding
import com.example.kks.getNickname
import com.example.kks.getUserIdx
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.time.LocalDate

class WriteActivity : AppCompatActivity() ,WriteRecordView{

    private lateinit var binding: ActivityWriteBinding
    private lateinit var resultLauncher : ActivityResultLauncher<Intent>
    private var selectedUri : Uri? =null
    private var writeRecordService = WriteRecordService()
    private var radioSelect : Int = 0
    private var rate :Float= 0.0F


    private  var multibody : MultipartBody.Part? = null

    // bitmap 변수
    private var path:Bitmap?=null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinner()

        writeRecordService.setWriteRecordView(this)



        binding.backIv.setOnClickListener {
            finish()
        }

        binding.imageAddButton.setOnClickListener { // 이미지 추가 버튼
            when{
                ContextCompat.checkSelfPermission( // 이미 권한에 동의한 경우
                        this,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED->{
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

//        binding.categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//               Log.d("write-position: ",position.toString())
//                selectCategory=true
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//                selectCategory=false
//
//            }
//        }

        binding.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            Toast.makeText(this,rating.toInt().toString(),Toast.LENGTH_SHORT).show()
         //   rate=rating.toInt()
        }
//
//        binding.publicRadioGroup.setOnClickListener {
//            radioSelect=1 // 라디오 버튼 클릭 체크
//        }

        binding.publicRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.openRadioButton -> radioSelect=1 // 공개
                R.id.closeRadioButton -> radioSelect=0 // 비공개
            }
        }

        binding.postRecordButton.setOnClickListener {  // 작성하기 버튼

            val title=binding.titleEt.text.toString() // 제목 가져옴
            val categoryName = binding.categorySpinner.selectedItem.toString() // 카테고리
            var categoryIdx:Int=-1
            if(categoryName=="공연"){
                categoryIdx=1
            }else if(categoryName=="도서"){
                categoryIdx=10
            }else if(categoryName=="드라마"){
                categoryIdx=11
            }else if(categoryName=="연극/뮤지컬"){
                categoryIdx=12
            }else if(categoryName=="영화"){
                categoryIdx=13
            }else if(categoryName=="음악"){
                categoryIdx=14
            }else if(categoryName=="전시"){
                categoryIdx=15
            }else if(categoryName=="기타"){
                categoryIdx=16
            }

//            var rate = binding.ratingBar.numStars // 평점

            var content = binding.contentEt.text.toString()

            rate= binding.ratingBar.rating

            Log.d("write-title: ",title)
            Log.d("write-categoryName: ",categoryName)
            Log.d("write-categoryIdx: ",categoryIdx.toString())
            Log.d("write-rate: ",rate.toString())
            Log.d("write-content: ",content)
            Log.d("write-path: ",path.toString())
            Log.d("write-radioSelect: ",radioSelect.toString())


            if(title==""){
            //    Toast.makeText(this,"제목을 입력해주세요",Toast.LENGTH_SHORT).show()
                binding.warningTv.text="제목을 입력해주세요"

            }else if(content==""){
              //  Toast.makeText(this,"내용을 입력해주세요",Toast.LENGTH_SHORT).show()
                binding.warningTv.text = "내용을 입력해주세요"
            }else if(path==null){
                binding.warningTv.text = "사진을 선택해주세요"

            }else if(categoryIdx==-1){
                binding.warningTv.text = "카테고리를 선택해주세요"

            }else{
                binding.warningTv.text=""
//                Toast.makeText(this,"API 호출",Toast.LENGTH_SHORT).show()





                //todo 이미지 multipart로 전송하기
//                val uploadBitmap = Bitmap.createScaledBitmap(path!!,500,400,true)
//                val stream = ByteArrayOutputStream()
//                uploadBitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)
//                val byteArray = stream.toByteArray()
//                val sendImage = byteArray.toRequestBody("image/*".toMediaTypeOrNull())
//                multibody = MultipartBody.Part.createFormData("images","image.jpeg",sendImage)

                var currentTime : LocalDate = LocalDate.now()
                val postDate = currentTime.toString()


                writeRecordService.getWriteRecord(RecordReq(getUserIdx(this),title,categoryIdx,rate,content,radioSelect,"image",0))
            }






            // API 호출


        }


        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result->
            if(result.resultCode==Activity.RESULT_OK){
                result.data?.data?.let{// 결과가 제대로 들어왔을때 (이미지 주소를 잘 가져왔을때) 실행
                    uri->
                    path=null // 앨범에서 가져올때마다 초기화
                    val inputStream = uri.let{
                        contentResolver.openInputStream(
                            it
                        )
                    }
                    val bitmap=BitmapFactory.decodeStream(inputStream)
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            1010->
                if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    startContentProvider()
                }else{
                    Toast.makeText(this, "앨범에 대한 접근 권한을 거부하셨습니다.", Toast.LENGTH_SHORT).show()
                }
        }
    }

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

    override fun onGetWriteRecordLoading() {
        Log.d("글작성/API","로딩중...")
    }

    override fun onGetWriteRecordSuccess(result: String) {
        Log.d("글작성/API","성공")
        Toast.makeText(this,"글 작성을 완료했습니다.",Toast.LENGTH_SHORT).show()
        val intent = Intent(this,MainActivity::class.java) // 메인 화면으로 이동 (캘린더)
        startActivity(intent)
        finish()
    }

    override fun onGetWriteRecordFailure(code: Int, message: String) {
        when(code){
            400->Log.d("글작성/API",message)
        }
    }
}