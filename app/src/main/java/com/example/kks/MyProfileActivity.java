package com.example.kks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
    NameViewModel NameViewModel;

    String userId, nickname, newName;
    String userImg = "no";

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
        //binding = DataBindingUtil.setContentView(this, R.layout.activity_my_profile);
        binding = ActivityMyProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

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
        //NameViewModel = new NameViewModel();

        //Toast.makeText(getApplicationContext(), "newname : "+newName, Toast.LENGTH_LONG).show();
        String editName = binding.editName.getText().toString();
        nickname = editName;
        Toast.makeText(getApplicationContext(), "newname : "+nickname, Toast.LENGTH_LONG).show();

        //서버에도 닉네임 변경하여 저장하기


    }
}
