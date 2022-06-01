package com.example.kks.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kks.R;
import com.example.kks.controller.GlideApp;
import com.example.kks.controller.RetrofitAPI;
import com.example.kks.controller.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RecommendAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Recommend> datalist = null;
    private int likeCnt = -1;

    public RecommendAdapter(Context c, ArrayList<Recommend> list){
        context = c;
        datalist = list;
    }

    @Override
    public int getCount() {
        return datalist.size();
    }

    @Override
    public Object getItem(int position) {
        return datalist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if(convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_recommend_item, parent, false);

            holder.img = convertView.findViewById(R.id.recommend_item_img);
            holder.title = convertView.findViewById(R.id.recommend_item_title);
            holder.date = convertView.findViewById(R.id.recommend_item_date);
            holder.rate = convertView.findViewById(R.id.recommend_item_rate);
            holder.like = convertView.findViewById(R.id.recommend_item_likeCnt);
            holder.text = convertView.findViewById(R.id.recommend_item_content);
        }

        GlideApp.with(context).load(datalist.get(position).getImgUrl()).into(holder.img);
        holder.title.setText(datalist.get(position).getTitle());
        holder.date.setText(datalist.get(position).getPostDate());
        holder.rate.setText(String.valueOf(datalist.get(position).getRate()));
        holder.text.setText(datalist.get(position).getContent());

        RetrofitClient client = new RetrofitClient();
        Retrofit retrofit = client.setRetrofit();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        ViewHolder finalHolder = holder;
        retrofitAPI.getRecordLikeCntTest(datalist.get(position).getRecordIdx()).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.body() != null)
                    likeCnt = response.body();
                else
                    likeCnt = 0;
                finalHolder.like.setText(String.valueOf(likeCnt));
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) { finalHolder.like.setText("0");}
        });

        return convertView;
    }

    static class ViewHolder{
        ImageView img;
        TextView title, date, rate, like, text;
    }
}
