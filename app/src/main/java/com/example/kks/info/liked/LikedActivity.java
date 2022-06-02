package com.example.kks.info.liked;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kks.R;
import com.example.kks.controller.ExpandableHeightGridView;
import com.example.kks.controller.RetrofitAPI;
import com.example.kks.controller.RetrofitClient;
import com.example.kks.info.myrecord.MyRecord;
import com.example.kks.info.myrecord.MyRecordAdapter;
import com.example.kks.record.DetailRecordActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LikedActivity extends AppCompatActivity {

    private String userId;

    private ImageButton back;
    static ExpandableHeightGridView liked_gv;
    private ArrayList<MyRecord> list;
    private MyRecordAdapter adapter;

    private Display display;
    private int width;

    //retrofit
    RetrofitClient client = new RetrofitClient();
    Retrofit retrofit = client.setRetrofit();
    RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked);

        back = findViewById(R.id.liked_back_btn);
        liked_gv = (ExpandableHeightGridView)findViewById(R.id.liked_grid);
        list = new ArrayList<MyRecord>();

        //화면 크기 받아오기
        display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        width = size.x;

        //로그인한 사용자의 아이디 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("userId", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("userId","");

        retrofitAPI.getLikedRecords(userId).enqueue(new Callback<ArrayList<MyRecord>>() {
            @Override
            public void onResponse(Call<ArrayList<MyRecord>> call, Response<ArrayList<MyRecord>> response) {
                ArrayList<MyRecord> data = response.body();

                for(int i=0; i<data.size(); i++)
                    list.add(data.get(i));
                adapter = new MyRecordAdapter(getApplicationContext(), list, width-100);
                liked_gv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<MyRecord>> call, Throwable t) {
                Log.e("좋아요 목록 결과 가져오기 실패",userId);
                t.printStackTrace();
            }
        });

        liked_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int recordIdx = list.get(position).getRecordIdx();
                Intent intent = new Intent(getApplicationContext(), DetailRecordActivity.class);
                intent.putExtra("recordIdx", recordIdx);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
