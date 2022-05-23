package com.example.kks.info.pattern;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.kks.R;
import com.example.kks.controller.RetrofitAPI;
import com.example.kks.controller.RetrofitClient;
import com.example.kks.controller.SharedPreference;
import com.example.kks.databinding.ActivitySpendpatternBinding;
import com.example.kks.login.PostUser;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SpendpatternActivity extends AppCompatActivity {

    //private PatternAdapter adapter1, adapter2;
    PatternList month, overall;
    PieChart pie1, pie2;

    String userId, nickname;
    Handler handler = new Handler();

    private TextView username_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spendpattern);

        userId = SharedPreference.getString(this, "userId");
        Log.d("저장된", userId);

        username_txt = findViewById(R.id.fornameview);

        //서버에서 닉네임 받아오기기
        RetrofitClient client = new RetrofitClient();
        Retrofit retrofit = client.setRetrofit();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Call<PostUser> call = retrofitAPI.getUser(userId);
        call.enqueue(new Callback<PostUser>() {
            @Override
            public void onResponse(Call<PostUser> call, Response<PostUser> response) {
                if(response.isSuccessful()){
                    PostUser data = response.body();
                    nickname = data.getNickName();
                    //userImg = data.getUserImg();
                    Log.d("이름", nickname);
                    //Log.d("이미지", userImg);
                }
            }

            @Override
            public void onFailure(Call<PostUser> call, Throwable t) {
                Log.d("실패", userId);
                t.printStackTrace();
            }
        });


        new Thread(new Runnable() {
            boolean isRun = false;
            int value = 0;

            @Override
            public void run() {
                isRun = true;
                while ((isRun)) {
                    value += 1;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            username_txt.setText(nickname + "님");

                            month = getMonth();
                            overall = getOverall();

                            pie1 = findViewById(R.id.pieChart1);
                            pie2 = findViewById(R.id.pieChart2);

                            viewPie(pie1, month);
                            viewPie(pie2, overall);
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                }
            }
        }).start(); //start()붙이면 바로실행시킨다.

    }


    public void viewPie(PieChart pie, PatternList list) {
        PieData pieData;
        List<PieEntry> pieEntryList = new ArrayList<>();

        // 핑크, 노랑, 연두, 주황, 하늘, 연보라, 청록
        int color[] = {Color.rgb(255, 198, 198), Color.rgb(255, 252, 128), Color.rgb(206, 242, 121),
                Color.rgb(255, 184, 90), Color.rgb(178, 235, 244), Color.rgb(218, 217, 255),
                Color.rgb(183, 240, 177)};

        //라벨 카테고리
        List<String> category = new ArrayList<>();
        category.add("영화");
        category.add("드라마");
        category.add("다큐");
        category.add("전시");
        category.add("뮤지컬");
        category.add("도서");
        category.add("음악");


        //piechart 그리기
        pie.setUsePercentValues(true);


        //값이 없는 것은 piechart에서 제외
        //pieEntryList.add(new PieEntry(list.movie, "영화"));
        for (int i = 0; i < 7; i++){
            if (list.get(i) > 0){
                pieEntryList.add(new PieEntry(list.get(i), category.get(i)));
            }
        }


        PieDataSet pieDataSet = new PieDataSet(pieEntryList, "");
        //pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        //pieDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        pieDataSet.setColors(color);

        // for set value of text label
        pieDataSet.setValueTextSize(8f);
        pieDataSet.setValueTextColor(Color.BLACK);

        //밑 라벨
        Legend legend = pie.getLegend();
        legend.setTextColor(Color.GRAY);
        legend.setTextSize(6f);
        //pieChart.setUsePercentValues(true);
        pieData = new PieData(pieDataSet);

        // 안쪽 라벨
        pie.setEntryLabelTextSize(9f);
        pie.setEntryLabelColor(Color.BLACK);
        pie.setData(pieData);
        pie.setDrawHoleEnabled(false);
        pie.getDescription().setEnabled(false);
        pie.invalidate();
    }


    public PatternList getMonth() {
        //db에 들어가서 카테고리별 개수 받아오기
        PatternList list = new PatternList(5, 15, 5, 15, 5, 15, 5);

        return list;
    }

    public PatternList getOverall() {
        //db에 들어가서 카테고리별 개수 받아오기
        PatternList list = new PatternList(5, 15, 5, 0, 5, 15, 5);

        return list;
    }

    public void finish(View view){
        //Fragment fragment = new ArchiveFolderFragment();
        //FragmentManager fragmentManager = getSupportFragmentManager();
        //fragmentManager.beginTransaction().replace(R.id.mainFrameLayout, fragment).commit();

        //Intent intent = new Intent(SpendpatternActivity.this, ActForFragmentArchiveFolderActivity.class);
        //startActivity(intent);
    }
}

