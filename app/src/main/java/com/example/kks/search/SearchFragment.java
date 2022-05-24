package com.example.kks.search;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kks.R;
import com.example.kks.databinding.FragmentSearchBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;

    private String userId, keyword;

    private ImageButton search_btn;
    private EditText search_edt;

    private RecyclerView recyclerView;
    private SearchResultAdapter adapter;

    private ArrayList<SearchTest> SearchList;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        search_btn = root.findViewById(R.id.btn_search);
        search_edt = root.findViewById(R.id.edt_search);
        recyclerView = root.findViewById(R.id.search_result_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext(), RecyclerView.VERTICAL, false));

        //로그인한 사용자의 아이디 가져오기
        SharedPreferences sharedPreferences = root.getContext().getSharedPreferences("userId", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("userId","");

        SearchList = new ArrayList<SearchTest>();

        //사용자 추천


        //TODO 검색 정렬 순


        //검색 결과
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword = search_edt.getText().toString();
                Log.d("검색 버튼 클릭",keyword);

                RetrofitClient client = new RetrofitClient();
                Retrofit retrofit = client.setRetrofit();
                RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
                retrofitAPI.getSearchResultTest(keyword, userId, 1).enqueue(new Callback<ArrayList<SearchTest>>() {
                    @Override
                    public void onResponse(Call<ArrayList<SearchTest>> call, Response<ArrayList<SearchTest>> response) {
                        if(response.isSuccessful()){
                            ArrayList<SearchTest> data = response.body();
                            SearchList.clear();

                            for(int i = 0; i < data.size();i++)
                                SearchList.add(data.get(i));

                            adapter = new SearchResultAdapter(root.getContext(), SearchList);
                            recyclerView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<SearchTest>> call, Throwable t) {
                        Log.e("검색 결과 가져오기 실패",keyword);
                        t.printStackTrace();
                    }
                });
            }
        });

        return root;
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