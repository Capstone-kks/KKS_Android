package com.example.kks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.kks.databinding.ActivityMyProfileBinding;
import com.example.kks.databinding.ActivityProfilePageBinding;

public class ProfilePageActivity extends AppCompatActivity {

    private ActivityProfilePageBinding binding;
    FragmentManager fragmentmanager;
    private Object MyProfileActivity;
    String userId, nickname;
    String userImg = "no";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        userId = intent.getStringExtra("user_id");
        nickname = intent.getStringExtra("nickname");
        userImg = intent.getStringExtra("userImage");

        binding = ActivityProfilePageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        //setContentView(R.layout.activity_profile_page);
        //binding.profileview.set

        fragmentmanager.beginTransaction().add(R.id.containerprofile, (Fragment) MyProfileActivity).commit();
    }
}