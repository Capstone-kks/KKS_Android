package com.example.kks.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.kks.R;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchViewHolder> {

    private Context context;
    private ArrayList<Search> datalist = null;

    public SearchResultAdapter(Context c, ArrayList<Search> list){
        context = c;
        datalist = list;
    }

    @NonNull
    @Override
    public SearchResultAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_recyclerview_item,parent,false);
        SearchResultAdapter.SearchViewHolder viewHolder = new SearchResultAdapter.SearchViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {

        Glide.with(context).load(datalist.get(position).getImgUrl()).apply(RequestOptions.bitmapTransform(new CropCircleTransformation())).into(holder.img);
        holder.title.setText(datalist.get(position).getTitle());
        holder.userId.setText(datalist.get(position).getUserId());
        holder.rate.setText(String.valueOf(datalist.get(position).getRate()));
        holder.text.setText(datalist.get(position).getContent());
        holder.date.setText(datalist.get(position).getPostDate());
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        ImageView img, liked;
        TextView title, userId, rate, likedCnt, text, date;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.search_result_recyclerview_item_imgView);
            title = itemView.findViewById(R.id.search_result_recyclerview_item_title);
            userId = itemView.findViewById(R.id.search_result_recyclerview_item_user);
            rate = itemView.findViewById(R.id.search_result_recyclerview_item_rate);
            text = itemView.findViewById(R.id.search_result_recyclerview_item_text);
            date = itemView.findViewById(R.id.search_result_recyclerview_item_date);
        }
    }
}
