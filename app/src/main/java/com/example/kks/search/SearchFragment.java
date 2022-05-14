package com.example.kks.search;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kks.R;
import com.example.kks.databinding.FragmentSearchBinding;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;

    private String userId;

    private ImageButton search_btn;
    private EditText search_edt;

    private RecyclerView recyclerView;
    private SearchResultAdapter adapter;

    private ArrayList<Search> SearchList;

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

        //검색 결과 - 미완 (더미 데이터 셋팅)
        //검색 더미 데이터 추가
        //BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_launcher_background);
        //Bitmap bitmap = getBitmapFromVectorDrawable(root.getContext(), R.drawable.ic_launcher_background);
        SearchList = new ArrayList<Search>();
        SearchList.add(new Search(1, "나른한 제주도","lolla44", userId, 5, 3, 0, "", "2021-08-05-23-27"));
        SearchList.add(new Search(2, "능력보다 더 인정받는 일잘러의 DNA","capst0ne", userId, 4, 6, 1, "직장 생활이 그렇게 버겁지만은 않다. 버겁다고 생각하면 한없이 버겁기도 하다. 밑 줄 그어가며 꼼꼼히 읽었으니 가까운 지인에게 도움이 될 거야,라며 툭 건네고 싶다. 직장 생활 까짓것 별거 아닌데 말이지. 그래도 혹시 모르니 한 번 읽어봐,라면서.", "2022-04-05-16-35"));
        SearchList.add(new Search(3, "봄날의 조기축구","dbms456", userId, 5, 37, 0, "", "2022-04-05-03-20"));
        SearchList.add(new Search(4, "최악의 공포연극 괴담","jupiter", userId, 1, 2, 1, "배우들이.. 목소리는 참 큰데... 감정이 없이 책을 읽고 있습니다.", "2021-09-25-18-03"));
        SearchList.add(new Search(5, "신비한 동물들과 덤블도어의 비밀","2133211949", userId, 3, 1, 0, "그린델왈드 외모는 멋져지고 능력은 찐따됨 ", "2022-04-28-01-18"));
        SearchList.add(new Search(6, "BOAT","2133211949", userId, 5, 14, 0, "반복이지만 띵곡임", "2022-03-29-01-21"));
        SearchList.add(new Search(7, "LOVE DIVE","2133211949", userId, 4, 92, 1, "아이돌 노래스럽지 않고 매력적임. 춤도 좋아.", "2022-04-30-01-22"));
        SearchList.add(new Search(8, "또오해영","2133211949", userId, 4, 78, 1, "오해영을 보면 마음이 아프다.", "2022-04-15-12-00"));
        adapter = new SearchResultAdapter(root.getContext(), SearchList);
        recyclerView.setAdapter(adapter);

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
