package com.example.kks

import android.content.pm.PackageInfo
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.kks.calendar.CalendarFragment
import com.example.kks.databinding.ActivityMainBinding

import com.example.kks.feed.FeedFragment
import com.example.kks.info.InfoFragment
import com.example.kks.search.SearchFragment
import com.kakao.sdk.common.util.Utility

class MainActivity : AppCompatActivity(){
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigation()

        // 해쉬값 구하기
        val keyHash = Utility.getKeyHash(this)
        Log.d("Hash",keyHash)



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
//                R.id.archiveFragment->{
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.mainFrameLayout, )
//                        .commitAllowingStateLoss()
//                    return@setOnItemSelectedListener true
//                }
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


}