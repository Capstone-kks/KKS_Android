package com.example.kks.search;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kks.R;
import com.example.kks.controller.RetrofitAPI;
import com.example.kks.controller.RetrofitClient;
import com.example.kks.databinding.FragmentSearchBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchFragment extends Fragment {

    //binding
    private FragmentSearchBinding binding;
    private View root;


    //정보
    private String userId, keyword;
    private int sort = 1;

    //UI
    private ImageButton search_btn;
    private EditText search_edt;
    private LinearLayout search_layout, recommend_layout;

    private TextView recommend_ment;
    private Gallery galleryView;
    private RecommendAdapter recommendAdapter;

    private RadioGroup Sort_rg;
    private RecyclerView recyclerView;
    private SearchResultAdapter SearchAdapter;

    private ArrayList<Recommend> RecommendList;
    private ArrayList<SearchTest> SearchList;


    //retrofit
    RetrofitClient client = new RetrofitClient();
    Retrofit retrofit = client.setRetrofit();
    RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        //기본 뷰
        search_btn = root.findViewById(R.id.btn_search);
        search_edt = root.findViewById(R.id.edt_search);
        search_layout = root.findViewById(R.id.search_layout);
        recommend_layout = root.findViewById(R.id.recommend_layout);

        //사용자 추천 뷰
        recommend_ment = root.findViewById(R.id.recommend_tv);
        galleryView = root.findViewById(R.id.recommend_gr);

        //검색화면 뷰
        Sort_rg = root.findViewById(R.id.search_sort_rg);
        recyclerView = root.findViewById(R.id.search_result_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext(), RecyclerView.VERTICAL, false));

        //로그인한 사용자의 아이디 가져오기
        SharedPreferences sharedPreferences = root.getContext().getSharedPreferences("userId", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("userId","");

        //TODO 사용자 닉네임 설정 - sharedPref사용하면?
        recommend_ment.setText("사용자 님 이런 게시물은 어떠세요?");

        //사용자 추천 더미데이터
        RecommendList = new ArrayList<Recommend>();
        RecommendList.add(new Recommend(1, "lolla44", "lolla44", "해리포터", 4.5f, "과거 볼드모트의 악행으로부터 유일하게 생존한 인물. 일명 살아남은 아이다. 생년월일은 1980년 7얼 31일이다.", "2022-03-21", "00"));
        RecommendList.add(new Recommend(2, "lolla44", "jupiter", "숨참고 럽 답", 3.5f, "네가 참 궁금해 그건 너도 마찬가지\n" +
                "이거면 충분해 쫓고 쫓는 이런 놀이\n" +
                "참을 수 없는 이끌림과 호기심\n" +
                "묘한 너와 나 두고 보면 알겠지\n" +
                "Woo 눈동자 아래로\n" +
                "Woo 감추고 있는 거\n" +
                "Woo yeah It's so bad It's good\n" +
                "난 그 맘을 좀 봐야겠어\n" +
                "Narcissistic, my god I love it\n" +
                "서로를 비춘 밤\n" +
                "아름다운 까만 눈빛 더 빠져 깊이\n" +
                "(넌 내게로 난 네게로)\n" +
                "숨 참고 love dive", "2022-04-5", "00"));
        recommendAdapter = new RecommendAdapter(galleryView.getContext(), RecommendList);
        galleryView.setAdapter(recommendAdapter);


        //사용자 추천 서버연결
        /*
        RecommendList = new ArrayList<Recommend>();
        retrofitAPI.getRecommend(userId).enqueue(new Callback<ArrayList<Recommend>>() {
            @Override
            public void onResponse(Call<ArrayList<Recommend>> call, Response<ArrayList<Recommend>> response) {
                if(response.isSuccessful()) {
                    ArrayList<Recommend> data = response.body();
                    RecommendList.clear();

                    for(int i = 0; i < data.size(); i++)
                        RecommendList.add(data.get(i));

                    recommendAdapter = new RecommendAdapter(root.getContext(), RecommendList);
                    galleryView.setAdapter(recommendAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Recommend>> call, Throwable t) {
                Log.e("사용자 추천 데이터 가져오기 실패",userId);
                t.printStackTrace();
            }
        });
        */


        //검색 결과
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword = search_edt.getText().toString();
                Log.d("검색 버튼 클릭",keyword);

                recommend_layout.setVisibility(View.GONE);
                search_layout.setVisibility(View.VISIBLE);

                SearchList = new ArrayList<SearchTest>();

                NewSearchResult();
            }
        });

        Sort_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.search_sort_date_rb)
                    sort = 1;
                else if(checkedId == R.id.search_sort_like_rb)
                    sort = 2;
                NewSearchResult();
            }
        });

        return root;
    }

    private void NewSearchResult(){
        retrofitAPI.getSearchResultTest(keyword, userId, sort).enqueue(new Callback<ArrayList<SearchTest>>() {
            @Override
            public void onResponse(Call<ArrayList<SearchTest>> call, Response<ArrayList<SearchTest>> response) {
                if(response.isSuccessful()){
                    ArrayList<SearchTest> data = response.body();
                    SearchList.clear();

                    for(int i = 0; i < data.size();i++)
                        SearchList.add(data.get(i));

                    SearchAdapter = new SearchResultAdapter(root.getContext(), SearchList);
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

/*
    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
*/
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
