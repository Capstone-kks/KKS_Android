package com.example.kks.info.myrecord;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.kks.R;
import com.example.kks.controller.RetrofitAPI;
import com.example.kks.controller.RetrofitClient;
import com.example.kks.controller.SharedPreference;
import com.example.kks.info.follow.Follow;
import com.example.kks.info.follow.FollowActivity;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyRecordActivity extends AppCompatActivity {

    public static String userId;
    //TODO:userId로 서버에서 사용자 이미지 가져오기
    public static String prefImg;

    private String prefId;

    private ImageView profile_iv;
    private ImageButton follower_btn, following_btn, follow_btn;
    private TextView follower_tv, following_tv, norecord_tv;
    private GridView records_gv;

    private ArrayList<MyRecord> list;
    private MyRecordAdapter adapter;

    //retrofit
    RetrofitClient client = new RetrofitClient();
    Retrofit retrofit = client.setRetrofit();
    RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myrecord);

        following_btn = findViewById(R.id.imgbtn_following);
        follower_btn = findViewById(R.id.imgbtn_follower);
        profile_iv = findViewById(R.id.myrecord_profile_iv);
        follower_tv = findViewById(R.id.follower_tv);
        following_tv = findViewById(R.id.following_tv);
        follow_btn = findViewById(R.id.myrecord_follow_imb);
        norecord_tv = findViewById(R.id.NoRecord_tv);
        records_gv = findViewById(R.id.myrecord_gv);

        list = new ArrayList<MyRecord>();

        //로그인한 사용자의 아이디 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("userId", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("userId","");

        //프로필 이미지 설정
        prefImg = SharedPreference.getString(this, "userImg");
        Glide.with(this).load(prefImg).apply(RequestOptions.bitmapTransform(new CropCircleTransformation())).into(profile_iv);

        //팔로우 신청버튼 보이기 여부
        prefId = SharedPreference.getString(this,"userId");
        if(prefId.equals(userId))
            follow_btn.setVisibility(View.GONE);
        else
            follow_btn.setVisibility(View.VISIBLE);

        //팔로워 리스트 가져오기 & 팔로워 숫자 셋팅
        retrofitAPI.getFollower(userId).enqueue(new Callback<ArrayList<Follow>>() {
            @Override
            public void onResponse(Call<ArrayList<Follow>> call, Response<ArrayList<Follow>> response) {
                FollowActivity.followerList = response.body();
                follower_tv.setText(String.valueOf(FollowActivity.followerList.size()));
            }

            @Override
            public void onFailure(Call<ArrayList<Follow>> call, Throwable t) {
                Log.e("팔로워 결과 가져오기 실패",userId);
                t.printStackTrace();
            }
        });
        //팔로잉 리스트 가져오기 & 팔로잉 숫자 셋팅
        retrofitAPI.getFollowing(userId).enqueue(new Callback<ArrayList<Follow>>() {
            @Override
            public void onResponse(Call<ArrayList<Follow>> call, Response<ArrayList<Follow>> response) {
                FollowActivity.followingList = response.body();
                following_tv.setText(String.valueOf(FollowActivity.followingList.size()));
            }

            @Override
            public void onFailure(Call<ArrayList<Follow>> call, Throwable t) {
                Log.e("팔로잉 결과 가져오기 실패",userId);
                t.printStackTrace();
            }
        });
        //팔로잉 버튼 클릭 시
        following_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FollowActivity.follow_num = 0;//사용자가 팔로우 하는 List 띄워야 함
                Intent intent = new Intent(getApplicationContext(), FollowActivity.class);
                startActivity(intent);
            }
        });
        //팔로워 버튼 클릭 시
        follower_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FollowActivity.follow_num = 1;//사용자를 팔로우 하는 List 띄워야 함
                Intent intent = new Intent(getApplicationContext(), FollowActivity.class);
                startActivity(intent);
            }
        });

        //다른 사용자 팔로우 버튼 클릭 시
        follow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 팔로우 요청 POST retrofit2
            }
        });

        //그리드뷰 셋팅
        retrofitAPI.getMyRecords(userId).enqueue(new Callback<ArrayList<MyRecord>>() {
            @Override
            public void onResponse(Call<ArrayList<MyRecord>> call, Response<ArrayList<MyRecord>> response) {
                ArrayList<MyRecord> data = response.body();

                if(data == null) {
                    Log.e("데이터 못받아옴",userId);
                    records_gv.setVisibility(View.GONE);
                    norecord_tv.setVisibility(View.VISIBLE);
                }
                else {
                    if(data.size() == 0){
                        Log.e("데이터 사이즈 0",userId);
                        records_gv.setVisibility(View.GONE);
                        norecord_tv.setVisibility(View.VISIBLE);
                    }
                    else{
                        Log.e("데이터 받아옴",data.get(0).getImg());
                        adapter = new MyRecordAdapter(getApplicationContext(), data);
                        records_gv.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MyRecord>> call, Throwable t) {
                Log.e("내 기록 이미지 가져오기 실패",userId);
                t.printStackTrace();
            }
        });

        //그리드뷰 클릭 시
        records_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int recordIdx = list.get(position).getRecordIdx();
                //TODO: 게시물 상세화면으로 넘기기
            }
        });
    }
}
