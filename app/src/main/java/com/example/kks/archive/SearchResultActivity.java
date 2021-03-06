package com.example.kks.archive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;

import com.example.kks.R;
import com.example.kks.controller.Records;
import com.example.kks.controller.RetrofitAPI;
import com.example.kks.controller.RetrofitClient;
import com.example.kks.databinding.ActivitySearchResultBinding;
import com.example.kks.login.PostUser;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchResultActivity extends AppCompatActivity {

    public static Activity act;
    int recordIdx=0;
    static int categoryId;
    static String exentered;
    static String userId;
    static String nickname;

    private ActivitySearchResultBinding binding;
    private RecyclerView recyclerView;

    private RadioGroup Sort_rg;
    //private ArrayList<SearchTest> SearchList;
    private ArrayList<Records> SearchList;
    private aSearchResultAdapter aSearchAdapter;

    //retrofit
    RetrofitClient client = new RetrofitClient();
    Retrofit retrofit = client.setRetrofit();
    RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_search_result);

        act = this;

        Intent intent = getIntent();
        recordIdx = intent.getIntExtra("recordIdx", 0);
        categoryId = intent.getIntExtra("categoryId", 0);
        exentered = intent.getStringExtra("entered");

        Log.i("카테고리넘버", Integer.toString(categoryId));
        Log.d("이전입력", exentered);

        binding = ActivitySearchResultBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //이전 검색어 띄워주기
        binding.edtSearch.setText(Editable.Factory.getInstance().newEditable(exentered));

        //검색화면 뷰
        Sort_rg = view.findViewById(R.id.search_sort_rg);
        recyclerView = view.findViewById(R.id.search_result_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false));

        //로그인한 사용자의 아이디 가져오기
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("userId", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("userId","");

        //서버에서 닉네임 가져오기
        RetrofitClient client = new RetrofitClient();
        Retrofit retrofit = client.setRetrofit();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Call<PostUser> call = retrofitAPI.getUser(userId);
        call.enqueue(new Callback<PostUser>() {
            @Override
            public void onResponse(Call<PostUser> call, Response<PostUser> response) {
                if(response.isSuccessful()){
                    PostUser data = response.body();
                    nickname = data.getNickName();
                    //userImg = data.getUserImg();
                    Log.d("이름", nickname);
                    //Log.d("이미지", userImg);
                }
            }

            @Override
            public void onFailure(Call<PostUser> call, Throwable t) {
                Log.d("실패", userId);
                t.printStackTrace();
            }
        });


        //이전 검색어로 검색
        SearchList = new ArrayList<Records>();
        NewSearchResult(exentered, view);
        binding.searchResultRecyclerview.setAdapter(aSearchAdapter);

        binding.edtSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    String keyword = binding.edtSearch.getText().toString();
                    Log.d("검색 버튼 클릭",keyword);

                    //SearchList = new ArrayList<SearchTest>();
                    SearchList = new ArrayList<Records>();
                    NewSearchResult(keyword, view);
                    return true;
                } else
                    return false;
            }
        });

    }

    public void finish(View view){
        act.finish();
    }

    public void searchResult(View view){
        String keyword = binding.edtSearch.getText().toString();
        Log.d("검색 버튼 클릭",keyword);

        //SearchList = new ArrayList<SearchTest>();
        SearchList = new ArrayList<Records>();
        NewSearchResult(keyword, view);

        //binding.searchResultRecyclerview.setAdapter(aSearchAdapter);
    }

    private void NewSearchResult(String keyword, View view){
        //retrofitAPI.getSearchResultTest(keyword, userId, 1).enqueue(new Callback<ArrayList<SearchTest>>() {
        retrofitAPI.getArchiveSearch(userId, categoryId, keyword).enqueue(new Callback<ArrayList<Records>>() {
            @Override
            public void onResponse(Call<ArrayList<Records>> call, Response<ArrayList<Records>> response) {
                Log.e("서버아이디",userId);
                if(response.isSuccessful()){
                    ArrayList<Records> data = response.body();
                    SearchList.clear();

                    int i;
                    for (i = 0; i < data.size(); i++)
                        SearchList.add(data.get(i));

                    if (i == 0) {
                        binding.searchLayout.setVisibility(View.GONE);
                        binding.noresult.setVisibility(View.VISIBLE);
                    }
                    else {
                        binding.searchLayout.setVisibility(View.VISIBLE);
                        binding.noresult.setVisibility(View.GONE);
                        aSearchAdapter = new aSearchResultAdapter(view.getContext(), SearchList, nickname, userId);
                        recyclerView.setAdapter(aSearchAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Records>> call, Throwable t) {
                Log.e("검색 결과 가져오기 실패",keyword);
                t.printStackTrace();
            }
        });
    }
}