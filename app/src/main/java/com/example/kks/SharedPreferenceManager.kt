package com.example.kks

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

// 모든 파일에서 공통적으로 사용할수 있는 함수
fun saveJwt(context: Context, jwt:String){
    val spf = context.getSharedPreferences("profile",AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()

    editor.putString("jwt",jwt)
    editor.apply()
}

fun getJwt(context: Context): String {
    val spf = context.getSharedPreferences("profile", AppCompatActivity.MODE_PRIVATE)

    return spf.getString("jwt", "")!!
}

fun saveUserIdx(context: Context, userIdx: String) {
    val spf = context.getSharedPreferences("profile", AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()

    editor.putString("userIdx", userIdx)
    editor.apply()
}

fun getUserIdx(context: Context): String {
    val spf = context.getSharedPreferences("profile", AppCompatActivity.MODE_PRIVATE)

    return spf.getString("userIdx","")!!
}
fun saveNickname(context: Context, nickname: String) {
    val spf = context.getSharedPreferences("profile", AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()

    editor.putString("nickname", nickname)
    editor.apply()
}

fun getNickname(context: Context): String {
    val spf = context.getSharedPreferences("profile", AppCompatActivity.MODE_PRIVATE)

    return spf.getString("nickname", "")!!
}
fun saveCategoryId(context: Context, categoryId: Int) {
    val spf = context.getSharedPreferences("profile", AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()

    editor.putInt("categoryId", categoryId)
    editor.apply()
}

fun getCategoryId(context: Context): Int {
    val spf = context.getSharedPreferences("profile", AppCompatActivity.MODE_PRIVATE)

    return spf.getInt("categoryId", 0)!!
}
fun removeSpfAll(context:Context){
    val spf = context.getSharedPreferences("profile",AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()

    editor.clear()
    editor.commit()
}