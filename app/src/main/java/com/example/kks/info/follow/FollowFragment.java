package com.example.kks.info.follow;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kks.R;
import com.example.kks.databinding.FragmentFollowBinding;

import java.util.ArrayList;

public class FollowFragment extends Fragment {

    private FragmentFollowBinding binding;

    public static int follow_num;//0이면 내가 팔로우 하는 사람들, 1이면 나를 팔로우 하는 사람들
    private String userId;

    private RecyclerView recyclerView;
    private FollowAdapter adapter;
    private ArrayList<Follow> followList;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFollowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //로그인한 사용자의 아이디 가져오기
        SharedPreferences sharedPreferences = root.getContext().getSharedPreferences("userId", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("userId","");

        recyclerView = root.findViewById(R.id.follow_fragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        followList = new ArrayList<Follow>();
        followList.add(new Follow("dbms456"));
        followList.add(new Follow("jupiter"));
        followList.add(new Follow("capst0ne"));
        adapter = new FollowAdapter(root.getContext(), followList);
        recyclerView.setAdapter(adapter);

        return root;
    }
}
