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
        //inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        //this.context = parent.getContext();
        //LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        if (null == convertView) {
            convertView = inflater.inflate(R.layout.photo_box, parent, false);
        }

        //ImageView photo = new ImageView(context);
        ImageView photo = (ImageView) convertView.findViewById(R.id.miniphoto);

        photo.setPadding(1, 1, 1, 1);
        photo.setScaleType(ImageView.ScaleType.CENTER_CROP);
        photo.setLayoutParams(new GridView.LayoutParams(350,350));

        /*
        for (int i=0; i<getCount(); i++) {
            Log.d("test", "recordid = " + mData.get(i).getRecordIdx());
            Log.d("test", "resource = " + mData.get(i).getImgUrl());

            GlideApp.with(context).load(mData.get(i).getImgUrl()).into(photo);
            //Glide.with(context).load(mData.toArray()[position]).into(photo);
        }
        */
        Log.d("test", "resource = " + mData.get(position).getImgUrl());
        GlideApp.with(context).load(mData.get(position).getImgUrl()).into(photo);

        // 이미지를 클릭하는 경우
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //기록 확인 activity intent하기
                Intent intent = new Intent(context, DetailRecordActivity.class);
                int recordIdx = mData.get(position).getRecordIdx();
                intent.putExtra("recordIdx", recordIdx);
                Log.d("레코드", Integer.toString(recordIdx));
                //intent.putExtra("categoryId", categoryId);
                //입력받은 text 넘기기
                context.startActivity(intent);

                /*
                // dialog.xml 인플레이트
                View dialogView = View.inflate(context, R.layout.photo_box, null);

                // Dialog 생성
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                ImageView miniphoto = dialogView.findViewById(R.id.miniphoto);

                Glide.with(context).load(mData.get(position)).into(miniphoto);

                //miniphoto.setImageResource(mData.indexOf(position));
                builder.setView(dialogView);

                builder.setPositiveButton("닫기", null);

                AlertDialog dialog = builder.create();
                dialog.show();
                 */
            }
        });
        //return convertView;
        return photo;
    }
}