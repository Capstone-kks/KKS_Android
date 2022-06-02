package com.example.kks.archive;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.kks.R;
import com.example.kks.controller.CatImg;
import com.example.kks.controller.GlideApp;
import com.example.kks.record.DetailRecordActivity;

import org.json.JSONException;

import java.util.ArrayList;

public class PhotoListAdapter extends BaseAdapter {


    Context context;
    private LayoutInflater inflater;

    private ArrayList<CatImg> mData;


    private OnItemClickListener mListener = null;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mListener = (OnItemClickListener) onItemClickListener;
    }
    public  static interface  OnItemClickListener{
        void onItemClick(View v, int pos) throws JSONException;
    }

    public PhotoListAdapter(Context context, ArrayList<CatImg> mData) {
        //super(context, R.layout.photo_box, mData);

        this.context = context;
        this.mData = mData;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        //Log.d("test", "listsize = " + mData.size());
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.photo_box, parent, false);
        }

        //ImageView photo = new ImageView(context);
        ImageView photo = (ImageView) convertView.findViewById(R.id.miniphoto);

        photo.setPadding(1, 1, 1, 1);
        photo.setScaleType(ImageView.ScaleType.CENTER_CROP);
        photo.setLayoutParams(new GridView.LayoutParams(350,350));


        GlideApp.with(context).load(mData.get(position).getImgUrl()).into(photo);


        return photo;
    }
}