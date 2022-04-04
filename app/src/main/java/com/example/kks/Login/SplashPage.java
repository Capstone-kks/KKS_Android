package com.example.kks.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.kks.HomeActivity;
import com.example.kks.R;
import com.example.kks.databinding.ActivityLoginPageBinding;
import com.example.kks.databinding.ActivitySplashPageBinding;

import java.util.Timer;
import java.util.TimerTask;

public class SplashPage extends AppCompatActivity {

    private ActivitySplashPageBinding binding;
    myDBAdapter myDBAdapter;
    String userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myDBAdapter = new myDBAdapter(this);

        binding = ActivitySplashPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        //setContentView(R.layout.activity_splash_page);



        //자동로그인 확인 -> 확인되면 바로 홈으로 넘어감
        //server와 연동 전 테스트 시 여기 주석처리 or 체크하지 말 것
        myDBAdapter.open();
        userId = myDBAdapter.openId();
        myDBAdapter.close();


        if (!userId.equals("")){
            Toast.makeText(getApplicationContext(), "login됨 : "+userId, Toast.LENGTH_SHORT).show();
            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                    finish();
                }
            };
            timer.schedule(timerTask, 1000);
        }
        else{
            Toast.makeText(getApplicationContext(), "간편로그인 불가능. 카카오로그인 필요.", Toast.LENGTH_LONG).show();

            //5초 후 자동으로 로그인페이지로 넘어감
            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), LoginPageActivity.class);
                    startActivity(intent);
                }
            };
            timer.schedule(timerTask, 2000);
        }
    }
}