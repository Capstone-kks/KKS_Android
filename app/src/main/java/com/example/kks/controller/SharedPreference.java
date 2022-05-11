package com.example.kks.controller;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public class SharedPreference {


    public static void saveString(Context context, String key, String value){

        SharedPreferences pref = context.getSharedPreferences(key, AppCompatActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }


    public static String getString(Context context, String key){
        SharedPreferences pref = context.getSharedPreferences(key, AppCompatActivity.MODE_PRIVATE);
        String value = pref.getString(key, "");
        return value;
    }

    public static void deleteAll(Context context, String key){
        SharedPreferences pref = context.getSharedPreferences(key, AppCompatActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

}
