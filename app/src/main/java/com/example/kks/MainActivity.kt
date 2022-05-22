package com.example.kks

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kks.archive.ArchiveFolderFragment
import com.example.kks.calendar.CalendarFragment
import com.example.kks.controller.SharedPreference
import com.example.kks.databinding.ActivityMainBinding
import com.example.kks.feed.FeedFragment
import com.example.kks.info.InfoFragment
import com.example.kks.login.LoginPageActivity
import com.example.kks.login.myDBAdapter
import com.example.kks.search.SearchFragment
import com.kakao.sdk.common.util.Utility

class MainActivity : AppCompatActivity(){

    //yk variables
    var userId: String? = null
    var nickname: String? = null
    var userImg: String? = "no"
    var checking = false
    var prefId: String = ""
    var prefImg: String = ""

    private val mcontext: Context? = null

    var myDBAdapter: myDBAdapter? = null
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigation()

        // 해쉬값 구하기
        val keyHash = Utility.getKeyHash(this)
        Log.d("Hash",keyHash)


        //yk code
        val intent = intent
 //       userId = intent.getStringExtra("user_id")
        //nickname = intent.getStringExtra("nickname")
        //userImg = intent.getStringExtra("userImage")
//        checking = intent.extras!!.getBoolean("checking")

 //       maintainId(userId!!, checking)

        /*
        //sharedpreference
        SharedPreference.saveString(mcontext, "userId", userId)
        prefId = SharedPreference.getString(mcontext, "userId")
        Log.d("저장된", prefId);
        //prefImg = SharedPreference.getString(mcontext, "userImg")
        //maintainImg(prefId, prefImg, checking)
        */

        binding.mainBottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.calendarFragment->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.mainFrameLayout, CalendarFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.feedFragment->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.mainFrameLayout, FeedFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.searchFragment->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.mainFrameLayout, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.archiveFragment->{
                    var fragment2 = ArchiveFolderFragment()
                    var bundle = Bundle()
                    //bundle.putString("userId",userId)
                    fragment2.arguments = bundle

                    supportFragmentManager.beginTransaction()
                       .replace(R.id.mainFrameLayout, fragment2)
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.profileFragment->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.mainFrameLayout,InfoFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    private fun initNavigation(){
        supportFragmentManager.beginTransaction().replace(R.id.mainFrameLayout,CalendarFragment())
            .commitAllowingStateLoss()
    }

    //yk code
    fun maintainId(userId: String, checking: Boolean) {
        myDBAdapter = myDBAdapter(this)
        if (checking == true) {
            //id 정보 db에 저장
            myDBAdapter!!.open()
            myDBAdapter!!.clear()
            myDBAdapter!!.insert(userId)
            myDBAdapter!!.close()
            Toast.makeText(applicationContext, "db에 log정보 추가 : $userId", Toast.LENGTH_LONG).show()
        }
    }

    fun maintainImg(userId: String, userImg: String?, checking: Boolean) {
        myDBAdapter = myDBAdapter(this)
        if (checking == true) {
            //id 정보 db에 저장
            myDBAdapter!!.open()
            myDBAdapter!!.clear()
            myDBAdapter!!.insert(userId)
            myDBAdapter!!.insert(userImg)
            myDBAdapter!!.close()
            Toast.makeText(applicationContext, "db에 img정보 추가 : $userId", Toast.LENGTH_LONG).show()
        }
    }


}