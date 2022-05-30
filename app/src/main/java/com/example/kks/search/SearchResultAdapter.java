package com.example.kks.search;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.example.kks.controller.RetrofitAPI;
import com.example.kks.controller.RetrofitClient;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchViewHolder> {

    private Context context;
    private ArrayList<SearchTest> datalist = null;
    private String userId;

    private String status;
    private int likeCnt = -1;

    public SearchResultAdapter(Context c, ArrayList<SearchTest> list, String ui){
        context = c;
        datalist = list;
        userId = ui;
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
        holder.userNickName.setText(datalist.get(position).getNickName());
        holder.rate.setText(String.valueOf(datalist.get(position).getRate()));
        holder.text.setText(datalist.get(position).getContent());
        holder.date.setText(datalist.get(position).getPostDate());

        RetrofitClient client = new RetrofitClient();
        Retrofit retrofit = client.setRetrofit();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        retrofitAPI.getRecordLikeCntTest(datalist.get(position).getRecordIdx()).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.body() != null)
                    likeCnt = response.body();
                else
                    likeCnt = 0;
                holder.like.setText(String.valueOf(likeCnt));
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) { holder.like.setText("0");}
        });

        retrofitAPI.getRecordLikeActiveTest(datalist.get(position).getRecordIdx(), userId).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body() != null)
                    status = response.body();
                else
                    status = "INACTIVE";

                if(status.equals("INACTIVE"))
                    holder.likeiv.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                else if(status.equals("ACTIVE"))
                    holder.likeiv.setImageResource(R.drawable.ic_baseline_favorite_24);Log.d(String.valueOf(position)+"active : ",status);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                holder.likeiv.setImageResource(R.drawable.ic_baseline_favorite_border_24);
            }
        });

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        ImageView img, likeiv;
        TextView title, userNickName, rate, text, date, like;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.search_result_recyclerview_item_imgView);
            title = itemView.findViewById(R.id.search_result_recyclerview_item_title);
            userNickName = itemView.findViewById(R.id.search_result_recyclerview_item_user);
            rate = itemView.findViewById(R.id.search_result_recyclerview_item_rate);
            text = itemView.findViewById(R.id.search_result_recyclerview_item_text);
            date = itemView.findViewById(R.id.search_result_recyclerview_item_date);
            like = itemView.findViewById(R.id.search_result_recyclerview_item_likeCount);
            likeiv = itemView.findViewById(R.id.search_result_recyclerview_item_like);
        }
    }
}
