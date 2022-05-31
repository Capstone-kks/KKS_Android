package com.example.kks.info.myrecord;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import com.example.kks.controller.GlideApp;
import java.util.ArrayList;

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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView = new ImageView(c);
        imageView.setPadding(1, 1, 1, 1);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(350,350));
        GlideApp.with(c).load(list.get(position).getImg()).into(imageView);

        return imageView;
    }
}
