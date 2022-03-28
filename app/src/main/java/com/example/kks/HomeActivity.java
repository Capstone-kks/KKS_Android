package com.example.kks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.kks.Login.myDBAdapter;
import com.example.kks.Pattern.SpendpatternActivity;
import com.example.kks.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    String userId, nickname;
    String userImg = "no";
    boolean checking=false;

    myDBAdapter myDBAdapter;

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
        checking = intent.getExtras().getBoolean("checking");

        //Toast.makeText(getApplicationContext(), "checking : "+checking, Toast.LENGTH_SHORT).show();
        maintainId(userId, checking);
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

    public void goPattern(View view){
        //Intent intent = new Intent(getApplicationContext(), PatternContent.class);
        Intent intent = new Intent(getApplicationContext(), SpendpatternActivity.class);
        intent.putExtra("user_id", userId);
        intent.putExtra("nickname", nickname);
        intent.putExtra("userImage", userImg);
        startActivity(intent);
        finish();
    }

    public void goMain2(View view){
        Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
        /*
        intent.putExtra("user_id", userId);
        intent.putExtra("nickname", nickname);
        intent.putExtra("userImage", userImg);
        */
        startActivity(intent);
        finish();
    }

    public void maintainId(String userId, boolean checking) {
        myDBAdapter = new myDBAdapter(this);

        if (checking == true) {
            //id 정보 db에 저장
            myDBAdapter.open();
            myDBAdapter.clear();
            myDBAdapter.insert(userId);
            myDBAdapter.close();
            Toast.makeText(getApplicationContext(), "db에 log정보 추가 : " + userId, Toast.LENGTH_LONG).show();
        }
    }
}