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
import android.view.View;

import com.example.kks.R;
import com.example.kks.databinding.ActivityGridBinding;
import com.example.kks.databinding.ActivitySearchResultBinding;
import com.example.kks.search.Search;
import com.example.kks.search.SearchResultAdapter;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {

    public static Activity act;
    int recordIdx=0;
    int categoryId;
    static String exentered;
    static String entered;
    String userId;

    private ActivitySearchResultBinding binding;
    private RecyclerView recyclerView;
    private SearchResultAdapter adapter;

    private ArrayList<Search> SearchList;

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

        recyclerView = view.findViewById(R.id.search_result_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false));

        //로그인한 사용자의 아이디 가져오기
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("userId", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("userId","");

        //검색 결과 - 미완 (더미 데이터 셋팅)
        //검색 더미 데이터 추가
        //BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_launcher_background);
        //Bitmap bitmap = getBitmapFromVectorDrawable(root.getContext(), R.drawable.ic_launcher_background);
        //SearchList = new ArrayList<Search>();
        //SearchList.add(new Search(1, "나른한 제주도","lolla44", userId, 5, 3, 0, "", "2021-08-05-23-27"));
        //SearchList.add(new Search(2, "능력보다 더 인정받는 일잘러의 DNA","capst0ne", userId, 4, 6, 1, "직장 생활이 그렇게 버겁지만은 않다. 버겁다고 생각하면 한없이 버겁기도 하다. 밑 줄 그어가며 꼼꼼히 읽었으니 가까운 지인에게 도움이 될 거야,라며 툭 건네고 싶다. 직장 생활 까짓것 별거 아닌데 말이지. 그래도 혹시 모르니 한 번 읽어봐,라면서.", "2022-04-05-16-35"));
        //SearchList.add(new Search(3, "봄날의 조기축구","dbms456", userId, 5, 37, 0, "", "2022-04-05-03-20"));
        //SearchList.add(new Search(4, "최악의 공포연극 괴담","jupiter", userId, 1, 2, 1, "배우들이.. 목소리는 참 큰데... 감정이 없이 책을 읽고 있습니다.", "2021-09-25-18-03"));
        //SearchList.add(new Search(5, "신비한 동물들과 덤블도어의 비밀","2133211949", userId, 3, 1, 0, "그린델왈드 외모는 멋져지고 능력은 찐따됨 ", "2022-04-28-01-18"));
        //SearchList.add(new Search(6, "BOAT","2133211949", userId, 5, 14, 0, "반복이지만 띵곡임", "2022-03-29-01-21"));
        //SearchList.add(new Search(7, "LOVE DIVE","2133211949", userId, 4, 92, 1, "아이돌 노래스럽지 않고 매력적임. 춤도 좋아.", "2022-04-30-01-22"));
        //SearchList.add(new Search(8, "또오해영","2133211949", userId, 4, 78, 1, "오해영을 보면 마음이 아프다.", "2022-04-15-12-00"));
        //adapter = new SearchResultAdapter(view.getContext(), SearchList);
        recyclerView.setAdapter(adapter);

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
        Intent intent = new Intent(getApplicationContext(), SearchResultActivity.class);
        intent.putExtra("recordIdx", recordIdx);
        intent.putExtra("categoryId", categoryId);
        //입력받은 text 넘기기
        entered = binding.edtSearch.getText().toString();
        Log.i("입력", entered);
        intent.putExtra("entered", entered);
        startActivity(intent);
        act.finish();
    }
}