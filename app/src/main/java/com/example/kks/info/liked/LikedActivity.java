package com.example.kks.info.liked;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kks.R;
import com.example.kks.controller.RetrofitAPI;
import com.example.kks.controller.RetrofitClient;
import com.example.kks.info.myrecord.MyRecord;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LikedActivity extends AppCompatActivity {

    private String userId;

    private GridView liked_gv;
    private ArrayList<MyRecord> list;
    private LikedRecordAdapter adapter;

    //retrofit
    RetrofitClient client = new RetrofitClient();
    Retrofit retrofit = client.setRetrofit();
    RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked);

        liked_gv = findViewById(R.id.liked_gv);

        //로그인한 사용자의 아이디 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("userId", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("userId","");

        //TODO 이미지 처리 후 -> 그리드뷰 연결

        retrofitAPI.getLikedRecords(userId).enqueue(new Callback<ArrayList<MyRecord>>() {
            @Override
            public void onResponse(Call<ArrayList<MyRecord>> call, Response<ArrayList<MyRecord>> response) {
                list = response.body();

                //TODO 그리드뷰 연결
            }

            @Override
            public void onFailure(Call<ArrayList<MyRecord>> call, Throwable t) {
                Log.e("좋아요 목록 결과 가져오기 실패",userId);
                t.printStackTrace();
            }
        });
    }
}
