package com.example.kks.info.myrecord;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MyRecordAdapter extends BaseAdapter {

    private ArrayList<MyRecord> list;
    private Context c;

    public MyRecordAdapter(Context m, ArrayList<MyRecord> d){
        c = m;
        list = d;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public MyRecord getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getRecordIdx();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView = new ImageView(c);
        //imageView.setPadding(5, 5, 5, 5);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, GridView.LayoutParams.MATCH_PARENT));
        //TODO 그리드뷰 이미지 셋팅
        //Glide.with(c).load(list.get(position).getImg()).apply(RequestOptions.bitmapTransform(new CropCircleTransformation())).into(imageView);
        return imageView;
    }
}
