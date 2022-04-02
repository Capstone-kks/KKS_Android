package com.example.kks.Pattern;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.kks.R;
import com.example.kks.databinding.ActivitySpendpatternBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class SpendpatternActivity extends AppCompatActivity {

    private ActivitySpendpatternBinding binding;

    //private PatternAdapter adapter1, adapter2;
    PatternList month, overall;
    PieChart pie1, pie2;

    String userId, nickname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_spendpattern);

        Intent intent = getIntent();
        userId = intent.getStringExtra("user_id");
        nickname = intent.getStringExtra("nickname");

        binding = ActivitySpendpatternBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //서버에서 닉네임 받아오기기
        binding.fornameview.setText(nickname + "님");

        month = getMonth();
        overall = getOverall();

        pie1 = findViewById(R.id.pieChart1);
        pie2 = findViewById(R.id.pieChart2);

        viewPie(pie1, month);
        viewPie(pie2, overall);


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
}

