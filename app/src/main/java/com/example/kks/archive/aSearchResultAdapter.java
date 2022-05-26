package com.example.kks.archive;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.example.kks.controller.GlideApp;
import com.example.kks.controller.Records;

import com.example.kks.record.DetailRecordActivity;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class aSearchResultAdapter extends RecyclerView.Adapter<aSearchResultAdapter.SearchViewHolder> {

    private Context context;
    private ArrayList<Records> datalist = null;
    static String name;

    public aSearchResultAdapter(Context c, ArrayList<Records> list, String nickname){
        context = c;
        datalist = list;
        name = nickname;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_recyclerview_item,parent,false);
        SearchViewHolder viewHolder = new SearchViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(context).load(datalist.get(position).getImgUrl()).apply(RequestOptions.bitmapTransform(new CropCircleTransformation())).into(holder.img);
        holder.title.setText(datalist.get(position).getTitle());
        holder.userNickName.setText(name);
        holder.rate.setText(String.valueOf(datalist.get(position).getRate()));
        holder.text.setText(datalist.get(position).getContent());
        holder.date.setText(datalist.get(position).getPostDate());

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //기록 확인 activity intent하기
                Intent intent = new Intent(context, DetailRecordActivity.class);
                int recordIdx = datalist.get(position).getRecordIdx();
                intent.putExtra("recordIdx", recordIdx);
                Log.d("레코드", Integer.toString(recordIdx));
                //intent.putExtra("categoryId", categoryId);
                //입력받은 text 넘기기
                context.startActivity(intent);
            }
        });

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //기록 확인 activity intent하기
                Intent intent = new Intent(context, DetailRecordActivity.class);
                int recordIdx = datalist.get(position).getRecordIdx();
                intent.putExtra("recordIdx", recordIdx);
                Log.d("레코드", Integer.toString(recordIdx));
                //intent.putExtra("categoryId", categoryId);
                //입력받은 text 넘기기
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title, userNickName, rate, text, date;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.search_result_recyclerview_item_imgView);
            title = itemView.findViewById(R.id.search_result_recyclerview_item_title);
            userNickName = itemView.findViewById(R.id.search_result_recyclerview_item_user);
            rate = itemView.findViewById(R.id.search_result_recyclerview_item_rate);
            text = itemView.findViewById(R.id.search_result_recyclerview_item_text);
            date = itemView.findViewById(R.id.search_result_recyclerview_item_date);
        }

    }
}
