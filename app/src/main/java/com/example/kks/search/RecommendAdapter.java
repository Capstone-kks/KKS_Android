package com.example.kks.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kks.R;

import java.util.ArrayList;

public class RecommendAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Recommend> datalist = null;

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

            //ImageView img
            holder.title = convertView.findViewById(R.id.recommend_item_title);
            holder.date = convertView.findViewById(R.id.recommend_item_date);
            holder.rate = convertView.findViewById(R.id.recommend_item_rate);
            holder.like = convertView.findViewById(R.id.recommend_item_likeCnt);
            holder.text = convertView.findViewById(R.id.recommend_item_content);
        }

        holder.title.setText(datalist.get(position).getTitle());
        holder.date.setText(datalist.get(position).getPostDate());
        holder.rate.setText(String.valueOf(datalist.get(position).getRate()));
        //holder.like.setText(datalist.get(position).getLikeCnt());
        holder.text.setText(datalist.get(position).getContent());

        return convertView;
    }

    static class ViewHolder{
        ImageView img;
        TextView title, date, rate, like, text;
    }
}
