package com.example.kks.info.liked;

import android.os.Bundle;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kks.R;

public class LikedActivity extends AppCompatActivity {

    private GridView liked_gv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked);

        liked_gv = findViewById(R.id.liked_gv);
    }
}
