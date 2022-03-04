package com.example.kks;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.kks.databinding.ActivityHomeBinding;
import com.example.kks.databinding.ActivityLoginPageBinding;

public class HomeActivity extends AppCompatActivity {

    String userId, nickname;
    String userImg = "no";

    private ActivityHomeBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        //setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        userId = intent.getStringExtra("user_id");
        nickname = intent.getStringExtra("nickname");
        userImg = intent.getStringExtra("userImage");
    }

    public void goProfile(View view){
        Intent intent = new Intent(getApplicationContext(), MyProfileActivity.class);
        intent.putExtra("user_id", userId);
        intent.putExtra("nickname", nickname);
        intent.putExtra("userImage", userImg);
        startActivity(intent);
        finish();
    }

    public void goWrite(View view){
        Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
        intent.putExtra("user_id", userId);
        intent.putExtra("nickname", nickname);
        intent.putExtra("userImage", userImg);
        startActivity(intent);
        finish();
    }
}