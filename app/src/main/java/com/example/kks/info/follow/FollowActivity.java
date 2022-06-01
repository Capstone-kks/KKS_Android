package com.example.kks.info.follow;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kks.R;

import java.util.ArrayList;

public class FollowActivity extends AppCompatActivity {

    public static int follow_num;//0이면 내가 팔로우 하는 사람들 - following, 1이면 나를 팔로우 하는 사람들 - follower
    public static ArrayList<Follow> followingList = new ArrayList<Follow>();//follow_num이 0일 때
    public static ArrayList<Follow> followerList = new ArrayList<Follow>();//follow_num이 1일 때

    private String LoginUserId, userId, nickname;

    private RecyclerView recyclerView;
    private FollowAdapter adapter;
    private TextView followTitle;
    private ImageView backBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);

        recyclerView = findViewById(R.id.follow_activity);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        followTitle=findViewById(R.id.followTitleTv);
        backBtn=findViewById(R.id.followBackIv);


        Intent getintent = getIntent();
        userId = getintent.getStringExtra("userId");
        nickname = getintent.getStringExtra("nickName");

        //로그인한 사용자의 아이디 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("userId", Context.MODE_PRIVATE);
        LoginUserId = sharedPreferences.getString("userId","");

        if(follow_num == 0) {
            followTitle.setText("내가 팔로우한 사용자 목록");
            adapter = new FollowAdapter(this, followingList, userId, LoginUserId);
            recyclerView.setAdapter(adapter);
        }else if(follow_num == 1) {
            followTitle.setText("나를 팔로우한 사용자 목록");
            adapter = new FollowAdapter(this, followerList, userId, LoginUserId);
            recyclerView.setAdapter(adapter);
        }


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}