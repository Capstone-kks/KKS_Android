package com.example.kks.Pattern;

import android.view.View;

import com.example.kks.R;
import com.github.mikephil.charting.charts.PieChart;

public class PatternViewHolder {

    public PieChart pieChart;

    public PatternViewHolder(View a_view){
        pieChart = a_view.findViewById(R.id.pieChart);
    }
}
