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
    //PatternList month, overall;

    String userId, nickname;
    String userImg = "no";

    PieChart pie1, pie2;
    PieData pieData;
    List<PieEntry> pieEntryList1 = new ArrayList<>();
    List<PieEntry> pieEntryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_spendpattern);

        Intent intent = getIntent();
        userId = intent.getStringExtra("user_id");
        nickname = intent.getStringExtra("nickname");
        userImg = intent.getStringExtra("userImage");
        //System.out.println("image url : " + userImg);

        binding = ActivitySpendpatternBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.fornameview.setText(nickname+"님");
        //값 받아오기
        //한달치 값
        //전체 값


        //임의로 값 생성
        //month = getMonth();
        //overall = getOverall();

        PatternList month = new PatternList(5,15,5,15,5,15,5);
        PatternList overall = new PatternList(5,5,5,5,5,5,5);


        //pie1 = binding.pieChart1;
        pie1 = findViewById(R.id.pieChart1);
        //viewPie(pie1, month);
        pie1.setUsePercentValues(true);
        pieEntryList1.add(new PieEntry(month.movie,"영화"));
        pieEntryList1.add(new PieEntry(month.drama,"드라마"));
        pieEntryList1.add(new PieEntry(month.doc,"다큐"));
        pieEntryList1.add(new PieEntry(month.exhibit,"전시"));
        pieEntryList1.add(new PieEntry(month.musical,"뮤지컬"));
        pieEntryList1.add(new PieEntry(month.book,"도서"));
        pieEntryList1.add(new PieEntry(month.music,"음악"));

        PieDataSet pieDataSet = new PieDataSet(pieEntryList1,"문화");
        //pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        pieDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        // for set value of text label
        pieDataSet.setValueTextSize(8f);
        pieDataSet.setValueTextColor(Color.BLACK);

        //밑 라벨
        Legend legend = pie1.getLegend();
        legend.setTextColor(Color.GRAY);
        legend.setTextSize(6f);
        //pieChart.setUsePercentValues(true);
        pieData = new PieData(pieDataSet);
        // if you want change Label text
        // 안쪽 라벨
        pie1.setEntryLabelTextSize(9f);
        pie1.setEntryLabelColor(Color.BLACK);
        pie1.setData(pieData);
        pie1.setDrawHoleEnabled(false);
        pie1.getDescription().setEnabled(false);
        pie1.invalidate();




        //pie2 = binding.pieChart2;
        pie2 = findViewById(R.id.pieChart2);
        //viewPie(pie2, overall);
        pie2.setUsePercentValues(true);
        pieEntryList.add(new PieEntry(month.movie,"영화"));
        pieEntryList.add(new PieEntry(month.drama,"드라마"));
        pieEntryList.add(new PieEntry(month.doc,"다큐"));
        pieEntryList.add(new PieEntry(month.exhibit,"전시"));
        pieEntryList.add(new PieEntry(month.musical,"전시"));
        pieEntryList.add(new PieEntry(month.book,"전시"));
        pieEntryList.add(new PieEntry(month.music,"전시"));

        PieDataSet pieDataSet2 = new PieDataSet(pieEntryList,"문화");
        pieDataSet2.setColors(ColorTemplate.JOYFUL_COLORS);
        pieData = new PieData(pieDataSet2);
        pie2.setData(pieData);
        pie2.setDrawHoleEnabled(false);
        pie2.invalidate();



        /*
        List<PatternList> patternlist = new ArrayList<>();

        ArrayList<Integer> mylist = new ArrayList<>();
        for (int i = 0; i<6; i++){
            mylist.add(i);
        }

        //임의값 생성


        RecyclerView recyclerview1 = binding.patternview1;
        //RecyclerView recyclerview2 = binding.patternview2;

        recyclerview1.setLayoutManager(new LinearLayoutManager(this));
        //recyclerview2.setLayoutManager(new LinearLayoutManager(this));

        adapter1 = new PatternAdapter(mylist);
        //adapter2 = new PatternAdapter(patternlist);

        recyclerview1.setAdapter(adapter1);
        //recyclerview2.setAdapter(adapter2);

         */


    }


    public void viewPie(PieChart pieChart, PatternList list){
        PieData pieData;
        List<PieEntry> pieEntryList = new ArrayList<>();

        pieChart.setUsePercentValues(true);
        pieEntryList.add(new PieEntry(list.movie,"영화"));
        pieEntryList.add(new PieEntry(list.drama,"드라마"));
        pieEntryList.add(new PieEntry(list.doc,"다큐"));
        pieEntryList.add(new PieEntry(list.exhibit,"전시"));
        pieEntryList.add(new PieEntry(list.musical,"전시"));
        pieEntryList.add(new PieEntry(list.book,"전시"));
        pieEntryList.add(new PieEntry(list.music,"전시"));

        PieDataSet pieDataSet = new PieDataSet(pieEntryList,"문화");
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.setDrawHoleEnabled(false);
        pieChart.invalidate();
    }


    public PatternList getOverall(){
        //서버 연동해서 값 받아오기
        PatternList list = null;

        //임의값생성
        list.movie = 5;
        list.drama = 5;
        list.doc = 5;
        list.exhibit = 5;
        list.musical = 5;
        list.book = 5;
        list.music = 5;

        return list;
    }

    public PatternList getMonth(){
        //서버 연동해서 값 받아오기
        PatternList list = null;

        //임의값생성
        list.movie = 5;
        list.drama = 5;
        list.doc = 5;
        list.exhibit = 5;
        list.musical = 5;
        list.book = 5;
        list.music = 5;

        return list;
    }
}