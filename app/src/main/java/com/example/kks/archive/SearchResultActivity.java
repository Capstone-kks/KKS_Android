package com.example.kks.archive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import com.example.kks.R;
import com.example.kks.controller.RetrofitAPI;
import com.example.kks.controller.RetrofitClient;
import com.example.kks.databinding.ActivityGridBinding;
import com.example.kks.databinding.ActivitySearchResultBinding;
import com.example.kks.search.Search;
import com.example.kks.search.SearchResultAdapter;
import com.example.kks.search.SearchTest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchResultActivity extends AppCompatActivity {

    public static Activity act;
    int recordIdx=0;
    int categoryId;
    static String exentered;
    static String entered;
    String userId;

    private ActivitySearchResultBinding binding;
    private RecyclerView recyclerView;

    private RadioGroup Sort_rg;
    private ArrayList<SearchTest> SearchList;
    private SearchResultAdapter SearchAdapter;

    //retrofit
    RetrofitClient client = new RetrofitClient();
    Retrofit retrofit = client.setRetrofit();
    RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

    Handler handler = new Handler();


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


        //이전 검색어로 검색
        SearchList = new ArrayList<SearchTest>();
        NewSearchResult(exentered, view);

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
                            binding.searchResultRecyclerview.setAdapter(SearchAdapter);

                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                }
            }
        }).start(); //start()붙이면 바로실행시킨다.


    }

    public void finish(View view){
        //Fragment fragment = new ArchiveFolderFragment();
        //FragmentManager fragmentManager = getSupportFragmentManager();
        //fragmentManager.beginTransaction().replace(R.id.mainFrameLayout, fragment).commit();

        //Intent intent = new Intent(SpendpatternActivity.this, ActForFragmentArchiveFolderActivity.class);
        //startActivity(intent);
        act.finish();
    }

    public void searchResult(View view){
        String keyword = binding.edtSearch.getText().toString();
        Log.d("검색 버튼 클릭",keyword);

        SearchList = new ArrayList<SearchTest>();
        NewSearchResult(keyword, view);

        /*
        Intent intent = new Intent(getApplicationContext(), SearchResultActivity.class);
        intent.putExtra("recordIdx", recordIdx);
        intent.putExtra("categoryId", categoryId);
        //입력받은 text 넘기기
        entered = binding.edtSearch.getText().toString();
        Log.i("입력", entered);
        intent.putExtra("entered", entered);
        startActivity(intent);
        act.finish();
         */
    }

    private void NewSearchResult(String keyword, View view){
        retrofitAPI.getSearchResultTest(keyword, userId, 1).enqueue(new Callback<ArrayList<SearchTest>>() {
            @Override
            public void onResponse(Call<ArrayList<SearchTest>> call, Response<ArrayList<SearchTest>> response) {
                if(response.isSuccessful()){
                    ArrayList<SearchTest> data = response.body();
                    SearchList.clear();

                    for(int i = 0; i < data.size();i++)
                        SearchList.add(data.get(i));

                    SearchAdapter = new SearchResultAdapter(view.getContext(), SearchList);
                    recyclerView.setAdapter(SearchAdapter);

                }
            }

            @Override
            public void onFailure(Call<ArrayList<SearchTest>> call, Throwable t) {
                Log.e("검색 결과 가져오기 실패",keyword);
                t.printStackTrace();
            }
        });
    }
}