package com.example.kks.archive;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.kks.R;
import com.example.kks.controller.GlideApp;
import com.example.kks.controller.Records;
import com.example.kks.controller.RetrofitAPI;
import com.example.kks.controller.RetrofitClient;
import com.example.kks.record.DetailRecordActivity;

import java.util.ArrayList;
import java.util.Arrays;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class aSearchResultAdapter extends RecyclerView.Adapter<aSearchResultAdapter.SearchViewHolder> {

    private Context context;
    private ArrayList<Records> datalist = null;
    static String name, user;
    private String result;
    private String status;

    Handler handler = new Handler();

    public aSearchResultAdapter(Context c, ArrayList<Records> list, String nickname, String userId){
        context = c;
        datalist = list;
        name = nickname;
        user = userId;
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

        //counts = getLikeCount(datalist.get(position).getRecordIdx());
        //status = getLikeStatus(datalist.get(position).getRecordIdx(), user);

        Glide.with(context).load(datalist.get(position).getImgUrl()).apply(RequestOptions.bitmapTransform(new CropCircleTransformation())).into(holder.img);
        holder.title.setText(datalist.get(position).getTitle());
        holder.userNickName.setText(name);
        holder.rate.setText(String.valueOf(datalist.get(position).getRate()));
        holder.text.setText(datalist.get(position).getContent());
        holder.date.setText(datalist.get(position).getPostDate());

        RetrofitClient client = new RetrofitClient();
        Retrofit retrofit = client.setRetrofit();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Call<String> call = retrofitAPI.getCountLike(datalist.get(position).getRecordIdx());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()) {
                    String getstr = response.body().toString();
                    result = "" + getstr;
                    holder.counting.setText(result);
                    Log.d("??????????????????", result);
                    //System.out.println(getstr);
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //Log.d("????????? ??????", Integer.toString(recordIdx));
                holder.counting.setText("0");
                t.printStackTrace();
            }
        });

        Call<String> call2 = retrofitAPI.getLikeStatus(datalist.get(position).getRecordIdx(), user);
        call2.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()) {
                    if(response.body() != null) {
                        status = response.body().toString();
                        //result2 = "" + getstr;
                        Log.d("????????? ??????", status);
                    }
                    else {
                        status = "0";
                    }

                    if(status.equals("0"))
                        holder.heart.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                    else if(status.equals("1"))
                        holder.heart.setImageResource(R.drawable.ic_baseline_favorite_24);
                        Log.d(String.valueOf(position)+"active : ",status);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //Log.d("?????? ??????", Integer.toString(recordIdx));
                status = "0";
                t.printStackTrace();
            }
        });


        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //?????? ?????? activity intent??????
                Intent intent = new Intent(context, DetailRecordActivity.class);
                int recordIdx = datalist.get(position).getRecordIdx();
                intent.putExtra("recordIdx", recordIdx);
                Log.d("?????????", Integer.toString(recordIdx));
                //intent.putExtra("categoryId", categoryId);
                //???????????? text ?????????
                context.startActivity(intent);
            }
        });

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //?????? ?????? activity intent??????
                Intent intent = new Intent(context, DetailRecordActivity.class);
                int recordIdx = datalist.get(position).getRecordIdx();
                intent.putExtra("recordIdx", recordIdx);
                Log.d("?????????", Integer.toString(recordIdx));
                //intent.putExtra("categoryId", categoryId);
                //???????????? text ?????????
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        ImageView img, heart;
        TextView title, userNickName, rate, text, date, counting;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.search_result_recyclerview_item_imgView);
            title = itemView.findViewById(R.id.search_result_recyclerview_item_title);
            userNickName = itemView.findViewById(R.id.search_result_recyclerview_item_user);
            rate = itemView.findViewById(R.id.search_result_recyclerview_item_rate);
            text = itemView.findViewById(R.id.search_result_recyclerview_item_text);
            date = itemView.findViewById(R.id.search_result_recyclerview_item_date);
            counting = itemView.findViewById(R.id.search_result_recyclerview_item_likeCount);
            heart = itemView.findViewById(R.id.search_result_recyclerview_item_like);
        }

    }
}
