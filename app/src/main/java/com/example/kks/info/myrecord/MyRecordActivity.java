package com.example.kks.info.myrecord;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.kks.R;
import com.example.kks.SharedPreferenceManagerKt;
import com.example.kks.controller.ExpandableHeightGridView;
import com.example.kks.controller.RetrofitAPI;
import com.example.kks.controller.RetrofitClient;
import com.example.kks.controller.SharedPreference;
import com.example.kks.info.follow.Follow;
import com.example.kks.info.follow.FollowActivity;
import com.example.kks.login.PostUser;
import com.example.kks.record.DetailRecordActivity;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyRecordActivity extends AppCompatActivity {

    private String LoginUserId, LoginNickName, userId, nickName, prefImg;

    private int follow_status = 0;

    private ImageView profile_iv;
    private ImageButton follower_btn, following_btn, follow_btn;
    private TextView nickName_tv, follower_tv, following_tv, norecord_tv;
    static ExpandableHeightGridView records_gv;

    private ArrayList<MyRecord> recordList;
    private ArrayList<Follow> followerList;
    private ArrayList<Follow> followingList;
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
        setContentView(R.layout.activity_myrecord);

        nickName_tv = findViewById(R.id.myrecord_nickName_tv);
        following_btn = findViewById(R.id.imgbtn_following);
        follower_btn = findViewById(R.id.imgbtn_follower);
        profile_iv = findViewById(R.id.myrecord_profile_iv);
        follower_tv = findViewById(R.id.follower_tv);
        following_tv = findViewById(R.id.following_tv);
        follow_btn = findViewById(R.id.myrecord_follow_imb);
        norecord_tv = findViewById(R.id.NoRecord_tv);
        records_gv = (ExpandableHeightGridView) findViewById(R.id.myrecord_grid);
        records_gv.setExpanded(true);

        recordList = new ArrayList<MyRecord>();
        followerList = new ArrayList<Follow>();
        followingList = new ArrayList<Follow>();

        //화면 크기 받아오기
        display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        width = size.x;

        //로그인한 사용자의 아이디 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("userId", Context.MODE_PRIVATE);
        LoginUserId = sharedPreferences.getString("userId","");
        LoginNickName = SharedPreferenceManagerKt.getNickname(getApplicationContext());

        //화면 사용자 정보 받아오기
        Intent getintent = getIntent();
        userId = getintent.getStringExtra("userId");


        if(userId.equals(LoginUserId)){//로그인 사용자의 개인 게시물
            Log.d("개인 게시물 페이지","로그인 사용자 페이지");

            //팔로우 버튼 없애기
            follow_btn.setVisibility(View.GONE);

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
                            for(int i = 0; i < data.size();i++)
                                recordList.add(data.get(i));
                            adapter = new MyRecordAdapter(getApplicationContext(), recordList, width);
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
        }
        else{ //다른 사용자의 개인 게시물 페이지
            Log.d("개인 게시물 페이지","다른 사용자 페이지");

            //팔로우 버튼 보이기
            follow_btn.setVisibility(View.VISIBLE);
            //팔로우 버튼 셋팅
            //String tempLoginUserId = "'" + LoginUserId + "'";
            //String tempUserId = "'" + userId + "'";
            retrofitAPI.getFollowStatus(LoginUserId, userId).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.body() != null) {
                        int result = Integer.parseInt(response.body());

                        if (result == 1) {
                            follow_btn.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                            follow_status = 1;
                        }
                        else if (result == 0) {
                            follow_btn.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
                            follow_status = 0;
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("myrecord follow error", t.getMessage());
                }
            });

            //그리드뷰 셋팅
            String userIdTemp = "'" + userId + "'";
            retrofitAPI.getotherRecords(userIdTemp).enqueue(new Callback<ArrayList<MyRecord>>() {
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
                            for(int i = 0; i < data.size();i++)
                                recordList.add(data.get(i));
                            adapter = new MyRecordAdapter(getApplicationContext(), recordList, width);
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
        }

        //프로필 이미지, 닉네임 설정
        retrofitAPI.getUser(userId).enqueue(new Callback<PostUser>() {
            @Override
            public void onResponse(Call<PostUser> call, Response<PostUser> response) {
                PostUser data = response.body();

                nickName = data.getNickName();
                nickName_tv.setText(nickName + " 님의 프로필");
                Glide.with(getApplicationContext()).load(data.getUserImg()).apply(RequestOptions.bitmapTransform(new CropCircleTransformation())).into(profile_iv);
            }

            @Override
            public void onFailure(Call<PostUser> call, Throwable t) {  }
        });


        getFollowSetting();

        //팔로잉 버튼 클릭 시
        following_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FollowActivity.follow_num = 0;//사용자가 팔로우 하는 List 띄워야 함
                Intent intent = new Intent(getApplicationContext(), FollowActivity.class);
                intent.putExtra("userId",userId);
                intent.putExtra("nickName",nickName);
                startActivity(intent);
            }
        });
        //팔로워 버튼 클릭 시
        follower_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FollowActivity.follow_num = 1;//사용자를 팔로우 하는 List 띄워야 함
                Intent intent = new Intent(getApplicationContext(), FollowActivity.class);
                intent.putExtra("userId",userId);
                intent.putExtra("nickName",nickName);
                startActivity(intent);
            }
        });

        follow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(follow_status == 0){//팔로우 신청
                    follow_status = 1;
                    Log.d("팔로우 신청 버튼 클릭",String.valueOf(follow_status));
                    //이미지뷰 변경
                    follow_btn.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                    //서버 변경
                    //String tempLoginUserId = "'" + LoginUserId + "'";
                    //String tempuserId = "'" + userId + "'";
                    retrofitAPI.requestFollow(LoginUserId, userId).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.body() != null) {
                                int result = Integer.parseInt(response.body());
                                if(result == 1)
                                    Toast.makeText(getApplicationContext(), nickName + "님을 팔로우", Toast.LENGTH_LONG).show();
                                getFollowSetting();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.d("팔로우 신청 오류",call.toString());
                        }
                    });
                }
                else if(follow_status == 1){//팔로우 취소
                    Log.d("팔로우 취소 버튼 클릭","실행");
                    follow_status = 0;
                    //이미지뷰 변경
                    follow_btn.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
                    //서버 변경
                    retrofitAPI.cancelFollow(LoginUserId, userId).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.body() != null) {
                                int result = Integer.parseInt(response.body());
                                if(result == 1)
                                    Toast.makeText(getApplicationContext(), nickName + "님 팔로우 취소", Toast.LENGTH_LONG).show();
                                getFollowSetting();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.d("팔로우 취소 오류",call.toString());
                        }
                    });
                }
            }
        });



        //그리드뷰 클릭 시 -> 게시물 상세페이지로 이동
        records_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int recordIdx = recordList.get(position).getRecordIdx();
                Intent intent = new Intent(getApplicationContext(), DetailRecordActivity.class);
                intent.putExtra("recordIdx", recordIdx);
                startActivity(intent);
            }
        });
    }

    private void getFollowSetting(){
        //팔로워 리스트 가져오기 & 팔로워 숫자 셋팅
        retrofitAPI.getFollower(userId).enqueue(new Callback<ArrayList<Follow>>() {
            @Override
            public void onResponse(Call<ArrayList<Follow>> call, Response<ArrayList<Follow>> response) {
                ArrayList<Follow> data = response.body();

                followerList.clear();

                for(int i = 0; i< data.size(); i++) {
                    followerList.add(data.get(i));
                }

                FollowActivity.followerList = followerList;
                follower_tv.setText(String.valueOf(followerList.size()));
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
                ArrayList<Follow> data = response.body();

                followingList.clear();
                for(int i = 0; i< data.size(); i++) {
                    followingList.add(data.get(i));
                    followingList.get(i).setFollowstatus(1);
                }
                FollowActivity.followingList = followingList;
                following_tv.setText(String.valueOf(followingList.size()));
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Follow>> call, Throwable t) {
                Log.e("팔로잉 결과 가져오기 실패",userId);
                t.printStackTrace();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //팔로워 리스트 가져오기 & 팔로워 숫자 셋팅
        getFollowSetting();
    }
}
