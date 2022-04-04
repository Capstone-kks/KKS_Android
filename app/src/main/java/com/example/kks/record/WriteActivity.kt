package com.example.kks.record

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.kks.R
import com.example.kks.databinding.ActivityWriteBinding

class WriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWriteBinding
    private lateinit var resultLauncher : ActivityResultLauncher<Intent>
    private var selectedUri : Uri? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinner()

        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result->
            if(result.resultCode== Activity.RESULT_OK){
                val intent = result.data
                selectedUri = intent?.data
                if(selectedUri!=null){
                    binding.imageView.setImageURI(selectedUri)
                }else{
                    Toast.makeText(this ,"사진을 가져오지 못했습니다.",Toast.LENGTH_SHORT).show()
                }


            }else return@registerForActivityResult
        }

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
}