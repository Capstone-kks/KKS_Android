package com.example.kks.pattern;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.kks.R;
import com.example.kks.databinding.PatternContentBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class PatternContent extends AppCompatActivity {

    private PatternContentBinding binding;

    PieChart pieChart;
    PieData pieData;
    List<PieEntry> pieEntryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pattern_content);

        pieChart = findViewById(R.id.pieChart);
        pieChart.setUsePercentValues(true);
        pieEntryList.add(new PieEntry(10,"India"));
        pieEntryList.add(new PieEntry(5,"US"));
        pieEntryList.add(new PieEntry(7,"UK"));
        pieEntryList.add(new PieEntry(3,"NZ"));

        PieDataSet pieDataSet = new PieDataSet(pieEntryList,"country");
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);

        float x = 0;
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(x);

        pieChart.setDrawHoleEnabled(false);
        pieChart.invalidate();
    }
}