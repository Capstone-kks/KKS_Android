package com.example.kks.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.kks.HomeActivity;
import com.example.kks.MainActivity;
import com.example.kks.controller.SharedPreference;
import com.example.kks.databinding.ActivitySplashPageBinding;

import java.util.Timer;
import java.util.TimerTask;

public class SplashPage extends AppCompatActivity {

    private ActivitySplashPageBinding binding;
    myDBAdapter myDBAdapter;
    String userId = "";
    //String userImg = "";
    static private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        myDBAdapter = new myDBAdapter(this);

        binding = ActivitySplashPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        //setContentView(R.layout.activity_splash_page);




        //자동로그인 확인 -> 확인되면 바로 홈으로 넘어감
        //server와 연동 전 테스트 시 여기 주석처리 or 체크하지 말 것
        myDBAdapter.open();
        userId = myDBAdapter.openId();
        //userImg = myDBAdapter.openImg();
        myDBAdapter.close();


        if (!userId.equals("")){
            //Toast.makeText(getApplicationContext(), "login됨 : "+userId, Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "자동로그인 되었습니다.", Toast.LENGTH_SHORT).show();
            //SharedPreference.saveString(context, "userId", userId);
            //String prefId = SharedPreference.getString(context, "userId");
            //Log.d("저장된", prefId);
            //Toast.makeText(getApplicationContext(), "이미지 : "+userImg, Toast.LENGTH_SHORT).show();
            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    //intent로 아이디 넘기기
                    intent.putExtra("user_id", userId);
                    //sharedpref로 아이디 저장하기
                    //SharedPreference.saveString(context, "userId", userId);
                    intent.putExtra("checking", false);
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