package com.example.kks.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.kks.R;
import com.example.kks.databinding.ActivityLoginPageBinding;
import com.example.kks.databinding.ActivitySplashPageBinding;

import java.util.Timer;
import java.util.TimerTask;

public class SplashPage extends AppCompatActivity {

    private ActivitySplashPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySplashPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        //setContentView(R.layout.activity_splash_page);



        //자동로그인 확인 -> 확인되면 바로 홈으로 넘어감



        //5초 후 자동으로 로그인페이지로 넘어감
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), LoginPageActivity.class);
                    startActivity(intent);
                }
        };
        timer.schedule(timerTask, 5000);
    }


}