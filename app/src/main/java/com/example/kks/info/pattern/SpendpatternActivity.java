package com.example.kks.info.pattern;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kks.R;
import com.example.kks.controller.RetrofitAPI;
import com.example.kks.controller.RetrofitClient;
import com.example.kks.controller.SharedPreference;
import com.example.kks.login.PostUser;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SpendpatternActivity extends AppCompatActivity {

    //private PatternAdapter adapter1, adapter2;
    PatternList month, overall;
    PieChart pie1, pie2;

    String userId;
    String nickname ="      ";
    Handler handler = new Handler();

    private TextView username_txt;
    public static Activity act;

    int[] numlist1 = new int[8];
    int[] numlist2 = new int[8];

    LinearLayout nopattern, nopattern2;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spendpattern);

        act = this;

        userId = SharedPreference.getString(this, "userId");
        Log.d("저장된", userId);

        username_txt = findViewById(R.id.fornameview);
        nopattern = findViewById(R.id.nopattern);
        nopattern2 = findViewById(R.id.nopattern2);

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

        //서버에서 전체기간 카테고리별 개수 받아오기
        numlist1 = getAllCount(userId);

        //서버에서 이번달 카테고리별 개수 받아오기
        //오늘의 연월 받기
        LocalDate now = LocalDate.now();
        String y = Integer.toString(now.getYear());
        String m = String.format("%02d", now.getMonthValue());

        String date = y + "-" + m;
        Log.d("오늘 연월", date);

        numlist2 = getMonthCount(userId, date);

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

                            month = getMonth(numlist2);
                            overall = getOverall(numlist1);

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

        // 핑크, 노랑, 핑크2, 연두, 주황, 하늘, 연보라, 청록,
        int color[] = {Color.rgb(255, 198, 198), Color.rgb(255, 252, 128), Color.rgb(206, 242, 121),
                Color.rgb(255, 204, 255), Color.rgb(255, 184, 90), Color.rgb(178, 235, 244),
                Color.rgb(218, 217, 255),Color.rgb(183, 240, 177)};

        //라벨 카테고리
        List<String> category = new ArrayList<>();
        category.add("공연");
        category.add("도서");
        category.add("드라마");
        category.add("연/뮤");
        category.add("영화");
        category.add("음악");
        category.add("전시");
        category.add("기타");


        //piechart 그리기
        pie.setUsePercentValues(true);


        //값이 없는 것은 piechart에서 제외
        //pieEntryList.add(new PieEntry(list.movie, "영화"));
        for (int i = 0; i < 8; i++){
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


    public PatternList getMonth(int[] numlist) {
        //db에 들어가서 카테고리별 개수 받아오기
        //PatternList list = new PatternList(5, 15, 5, 15, 5, 15, 5, 4);
        PatternList list = new PatternList(numlist[0], numlist[1], numlist[2], numlist[3], numlist[4], numlist[5], numlist[6], numlist[7]);

        return list;
    }

    public PatternList getOverall(int[] numlist) {
        PatternList list = new PatternList(numlist[0], numlist[1], numlist[2], numlist[3], numlist[4], numlist[5], numlist[6], numlist[7]);

        return list;
    }

    public void finish(View view){
        //Fragment fragment = new ArchiveFolderFragment();
        //FragmentManager fragmentManager = getSupportFragmentManager();
        //fragmentManager.beginTransaction().replace(R.id.mainFrameLayout, fragment).commit();

        //Intent intent = new Intent(SpendpatternActivity.this, ActForFragmentArchiveFolderActivity.class);
        //startActivity(intent);
        act.finish();
    }

    public int[] getAllCount(String userId){
        RetrofitClient client = new RetrofitClient();
        Retrofit retrofit = client.setRetrofit();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        int[] numlist = new int[8];

        Call<String> call2 = retrofitAPI.getcountall(userId);
        call2.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    int sum = 0;
                    String str = response.body().toString();
                    Log.d("카운트", str);
                    str += "";
                    String[] arr = str.split(",");
                    System.out.println(Arrays.toString(arr));

                    for (int i=0; i<8;i++){
                        numlist[i] = Integer.parseInt(arr[i]);
                        sum += numlist[i];
                    }
                    System.out.println(Arrays.toString(numlist));

                    if (sum == 0){
                        pie2.setVisibility(View.GONE);
                        nopattern2.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<String>  call, Throwable t) {
                Log.d("카운트 실패", userId);
                t.printStackTrace();
            }
        });

        return numlist;
    }

    public int[] getMonthCount(String userId, String date){
        RetrofitClient client = new RetrofitClient();
        Retrofit retrofit = client.setRetrofit();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        int[] numlist = new int[8];

        Call<String> call = retrofitAPI.getcountmonth(userId, date);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    int sum = 0;
                    String getstr = response.body().toString();
                    Log.d("월카운트", getstr);
                    getstr += "";
                    String[] arr = getstr.split(",");
                    System.out.println(Arrays.toString(arr));

                    for (int i=0; i<8;i++){
                        numlist[i] = Integer.parseInt(arr[i]);
                        sum += numlist[i];
                    }
                    System.out.println(Arrays.toString(numlist));

                    if (sum == 0){
                        pie1.setVisibility(View.GONE);
                        nopattern.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<String>  call, Throwable t) {
                Log.d("카운트 실패", userId);
                t.printStackTrace();
            }
        });

        return numlist;
    }
}
