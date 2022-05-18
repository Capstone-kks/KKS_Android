package com.example.kks.archive;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;

import com.example.kks.R;
import com.example.kks.databinding.ActivityGridBinding;
import com.example.kks.databinding.ActivitySearchResultBinding;

public class SearchResultActivity extends AppCompatActivity {

    public static Activity act;
    int recordIdx=0;
    int categoryId;
    static String exentered;
    static String entered;

    private ActivitySearchResultBinding binding;

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