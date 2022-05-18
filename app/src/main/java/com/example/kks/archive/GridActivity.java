package com.example.kks.archive;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.kks.R;
import com.example.kks.databinding.ActivityGridBinding;
import com.example.kks.databinding.ActivityLoginPageBinding;

public class GridActivity extends AppCompatActivity {

    int[] categories = {1, 10, 11, 12, 13, 14, 15, 16};
    String[] catlist = {"공연", "도서", "드라마", "연극/뮤지컬", "영화", "음악", "전시", "기타"};
    int categoryId;
    int recordIdx=0;
    static String entered;

    private ActivityGridBinding binding;

    public static Activity act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_grid);

        act = this;

        binding = ActivityGridBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent intent = getIntent();
        categoryId = intent.getIntExtra("categoryId", 0);
        Log.i("카테고리넘버", Integer.toString(categoryId));

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
    }
}