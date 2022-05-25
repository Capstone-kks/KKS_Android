package com.example.kks.info.follow;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kks.R;

import java.util.ArrayList;

public class FollowActivity extends AppCompatActivity {

    public static int follow_num;//0이면 내가 팔로우 하는 사람들 - following, 1이면 나를 팔로우 하는 사람들 - follower
    public static ArrayList<Follow> followingList;//follow_num이 0일 때
    public static ArrayList<Follow> followerList;//follow_num이 1일 때

    private String userId;

    private RecyclerView recyclerView;
    private FollowAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);

        recyclerView = findViewById(R.id.follow_activity);
        if(follow_num == 0)
            adapter = new FollowAdapter(this, followingList);
        else if(follow_num == 1)
            adapter = new FollowAdapter(this, followerList);
        recyclerView.setAdapter(adapter);
    }
}