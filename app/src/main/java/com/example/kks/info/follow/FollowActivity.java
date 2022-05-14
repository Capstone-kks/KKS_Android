package com.example.kks.info.follow;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kks.R;

import java.util.ArrayList;

public class FollowActivity extends AppCompatActivity {

    public static int follow_num;//0이면 내가 팔로우 하는 사람들, 1이면 나를 팔로우 하는 사람들
    private String userId;

    private RecyclerView recyclerView;
    private FollowAdapter adapter;
    private ArrayList<Follow> followList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);

        //로그인한 사용자의 아이디 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("userId", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("userId","");

/*
        recyclerView = findViewById(R.id.follow_fragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        followList = new ArrayList<Follow>();
        followList.add(new Follow("dbms456"));
        followList.add(new Follow("jupiter"));
        followList.add(new Follow("capst0ne"));
        adapter = new FollowAdapter(this, followList);
        recyclerView.setAdapter(adapter);
        */
    }
}