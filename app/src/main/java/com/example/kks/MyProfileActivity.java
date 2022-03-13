package com.example.kks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ObservableField;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.kks.databinding.ActivityLoginPageBinding;
import com.example.kks.databinding.ActivityMyProfileBinding;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Observable;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MyProfileActivity extends AppCompatActivity {

    private ActivityMyProfileBinding binding;

    String userId, nickname;
    String userImg = "no";
    private ObservableField<String> newName;


    public MyProfileActivity(){
        this.newName = new ObservableField<>("new Nickname");
    }

    public ObservableField<String> getText(){
        return newName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        userId = intent.getStringExtra("user_id");
        nickname = intent.getStringExtra("nickname");
        userImg = intent.getStringExtra("userImage");
        //System.out.println("image url : " + userImg);

        //dummy data
        //nickname = "굥";

        binding = ActivityMyProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        //setContentView(R.layout.activity_my_profile);

        binding.editName.setText(nickname);

        //get image using Glid lib
        Glide.with(this).load(userImg).apply(RequestOptions.bitmapTransform(new CropCircleTransformation())).into(binding.profileImg);


        //get image using url connection... but failed. don't know why
        /*
        if (!userImg.equals("no")) {
            {
                try {
                    URL url = new URL(userImg);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    //BitmapFactory.Options options = new BitmapFactory.Options();
                    Bitmap originalBm = BitmapFactory.decodeStream(conn.getInputStream());
                    binding.profileImg.setImageBitmap(originalBm);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        */
    }

    public void changeName(View view){
        String editName = binding.editName.getText().toString();
        nickname = editName;

        //서버에도 닉네임 변경하여 저장하기


    }
}
