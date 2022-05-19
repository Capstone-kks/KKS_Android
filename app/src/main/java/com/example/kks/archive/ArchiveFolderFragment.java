package com.example.kks.archive;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kks.MainActivity;
import com.example.kks.R;
import com.example.kks.databinding.FragmentArchiveFolderBinding;
import com.example.kks.info.pattern.SpendpatternActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ArchiveFolderFragment extends Fragment {

    private FragmentArchiveFolderBinding binding;
    private View root;

    String userId;

    int[] categories = {1, 10, 11, 12, 13, 14, 15, 16};
    String[] catlist = {"공연", "도서", "드라마", "연극/뮤지컬", "영화", "음악", "전시", "기타"};

    public static ArchiveFolderFragment newInstance() {
        return new ArchiveFolderFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       /*
        binding = FragmentArchiveFolderBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        //유경님 아카이브 작업 여기에서 작업하시면 됩니다!

        ImageButton btn = (ImageView) view.findViewById(R.id.iv_icon_map);

        int color = ContextCompat.getColor(getActivity(), R.color.color_red);

        iv_icon_map.setColorFilter(color);

        return root;
        */

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_archive_folder, container, false);
        binding.setArchiveFolderFragment(this);
        View view = binding.getRoot();

        int mint_color = ContextCompat.getColor(getActivity(), R.color.main_mint);

        //ArrayList<HashMap> catlist = new ArrayList<>(8);
        //카테고리 순서 : 공연 도서 드라마 연/뮤 영화 음악 전시 기타

        List<String> list = new ArrayList<String>();
        list = Arrays.asList(catlist);
        TextView[] cats = new TextView[list.size()];
        ImageButton[] btns = new ImageButton[list.size()];

        for (int i=0; i<8; i++){
            int catid = getResources().getIdentifier("textView" + (i+1), "id", getActivity().getPackageName());
            int btnid = getResources().getIdentifier("btn" + (i+1), "id", getActivity().getPackageName());
            cats[i] = view.findViewById(catid);
            btns[i] = view.findViewById(btnid);
            btns[i].setColorFilter(mint_color);

            cats[i].setText(catlist[i]);
        }

        //ImageButton btn1 = (ImageButton) view.findViewById(R.id.btn1);
        //int color = ContextCompat.getColor(getActivity(), R.color.main_mint);
        //btn1.setColorFilter(color);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void go0(View view){
        Intent intent = new Intent(getActivity(), GridActivity.class);
        intent.putExtra("categoryId", categories[0]);
        startActivity(intent);
    }

    public void go1(View view){
        Intent intent = new Intent(getActivity(), GridActivity.class);
        intent.putExtra("categoryId", categories[1]);
        startActivity(intent);
    }

    public void go2(View view){
        Intent intent = new Intent(getActivity(), GridActivity.class);
        intent.putExtra("categoryId", categories[2]);
        startActivity(intent);
    }

    public void go3(View view){
        Intent intent = new Intent(getActivity(), GridActivity.class);
        intent.putExtra("categoryId", categories[3]);
        startActivity(intent);
    }

    public void go4(View view){
        Intent intent = new Intent(getActivity(), GridActivity.class);
        intent.putExtra("categoryId", categories[4]);
        startActivity(intent);
    }

    public void go5(View view){
        Intent intent = new Intent(getActivity(), GridActivity.class);
        intent.putExtra("categoryId", categories[5]);
        startActivity(intent);
    }

    public void go6(View view){
        Intent intent = new Intent(getActivity(), GridActivity.class);
        intent.putExtra("categoryId", categories[6]);
        startActivity(intent);
    }

    public void go7(View view){
        Intent intent = new Intent(getActivity(), GridActivity.class);
        intent.putExtra("categoryId", categories[7]);
        startActivity(intent);
    }
}