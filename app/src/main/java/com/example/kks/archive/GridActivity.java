package com.example.kks.archive;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.kks.R;
import com.example.kks.controller.CatImg;
import com.example.kks.controller.ExpandableHeightGridView;
import com.example.kks.controller.RetrofitAPI;
import com.example.kks.controller.RetrofitClient;
import com.example.kks.databinding.ActivityGridBinding;
import com.example.kks.record.DetailRecordActivity;
import com.example.kks.search.Search;
import com.example.kks.search.SearchResultAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GridActivity extends AppCompatActivity {

    int[] categories = {1, 10, 11, 12, 13, 14, 15, 16};
    String[] catlist = {"공연", "도서", "드라마", "연극/뮤지컬", "영화", "음악", "전시", "기타"};
    String userId;
    int categoryId;
    int recordIdx=0;
    static String entered;
    static GridView gridView1;
    static ExpandableHeightGridView gridView;

    private ActivityGridBinding binding;

    public static Activity act;

    private ArrayList<Search> SearchList;
    private SearchResultAdapter SearchAdapter;

    //retrofit
    RetrofitClient client = new RetrofitClient();
    Retrofit retrofit = client.setRetrofit();
    RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

    //public static ArrayList<CatImg> list = new ArrayList<>();
    PhotoListAdapter adapter;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_grid);

        act = this;

        Intent intent = getIntent();
        categoryId = intent.getIntExtra("categoryId", 0);
        Log.i("카테고리넘버", Integer.toString(categoryId));

        binding = ActivityGridBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //로그인한 사용자의 아이디 가져오기
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("userId", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("userId","");

        //서버에서 이미지, recordidx 리스트 받아오기
        getServer();

        gridView = (ExpandableHeightGridView) findViewById(R.id.grid);
        gridView.setExpanded(true);

//        binding.edtSearch.setOnKeyListener(new View.OnKeyListener(){
//
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if(keyCode==KeyEvent.KEYCODE_ENTER){
//                    searchResult(binding.edtSearch);
//
//                    return true;
//                }else
//                    return false;
//            }
//        });
        //gridView1 = findViewById(R.id.grid);

        /*
        new Thread(new Runnable() {
            boolean isRun = false;
            int value = 0;

            @Override
            public void run() {
                isRun = true;
                while ((isRun)) {
                    value += 1;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //그리드뷰 넣어주기
                            //adapter = new PhotoListAdapter(GridActivity.this, arr);
                            binding.grid.setAdapter(adapter);

                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                }
            }
        }).start(); //start()붙이면 바로실행시킨다.
        */


    }

    public void finish(View view){
        act.finish();
    }

    public void searchResult(View view){
        Intent intent = new Intent(getApplicationContext(), SearchResultActivity.class);
        intent.putExtra("recordIdx", recordIdx);
        intent.putExtra("categoryId", categoryId);
        //입력받은 text 넘기기
        entered = binding.edtSearch.getText().toString();
        Log.i("입력", entered);
        intent.putExtra("entered", entered);
        startActivity(intent);
    }

    public void getServer(){

        Call<List<CatImg>> call = retrofitAPI.getcatimg(userId, categoryId);
        call.enqueue(new Callback<List<CatImg>>() {
            @Override
            public void onResponse(Call<List<CatImg>> call, Response<List<CatImg>> response) {
                if(response.isSuccessful()){
                    List<CatImg> data = response.body();

                    ArrayList<CatImg> list = new ArrayList<>();

                    int i =0;
                    for(CatImg item : data){
                        list.add(item);
                        //Log.d("이미지", list.get(i).getImgUrl());
                        i++;
                    }

                    if (i == 0){
                        binding.gridlayout.setVisibility(View.GONE);
                        binding.noresult.setVisibility(View.VISIBLE);
                    }
                    else {
                        adapter = new PhotoListAdapter(GridActivity.this, list);
                        binding.grid.setAdapter(adapter);

                        binding.grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                int recordIdx = list.get(position).getRecordIdx();
                                Intent intent = new Intent(getApplicationContext(), DetailRecordActivity.class);
                                intent.putExtra("recordIdx", recordIdx);
                                startActivity(intent);
                            }
                        });
                    }

                }
            }

            @Override
            public void onFailure(Call<List<CatImg>> call, Throwable t) {
                Log.d("카운트 실패", userId);
                t.printStackTrace();
            }
        });
    }
}