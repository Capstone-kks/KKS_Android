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
    private String result, result2;

    String counts, status = "";

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

        counts = getLikeCount(datalist.get(position).getRecordIdx());
        status = getLikeStatus(datalist.get(position).getRecordIdx(), user);

        Glide.with(context).load(datalist.get(position).getImgUrl()).apply(RequestOptions.bitmapTransform(new CropCircleTransformation())).into(holder.img);
        holder.title.setText(datalist.get(position).getTitle());
        holder.userNickName.setText(name);
        holder.rate.setText(String.valueOf(datalist.get(position).getRate()));
        holder.text.setText(datalist.get(position).getContent());
        holder.date.setText(datalist.get(position).getPostDate());

        new Thread(new Runnable() {
            boolean isRun = false;
            int value = 0;

            @Override
            public void run() {
                isRun = true;
                while ((isRun)) {
                    value += 1;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            //holder.counting.setText(getLikeCount(datalist.get(position).getRecordIdx()));
                            holder.counting.setText(counts);

                            if (status != null) {
                                if (status.equals("1")) {
                                    holder.heart.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_favorite_24));
                                    //Glide.with(context).load(ContextCompat.getDrawable(context, R.drawable.ic_baseline_favorite_24)).into(holder.heart);
                                }
                                else{
                                    holder.heart.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_favorite_border_24));
                                    //Glide.with(context).load(ContextCompat.getDrawable(context, R.drawable.ic_baseline_favorite_border_24)).into(holder.heart);
                                }
                            }

                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                }
            }
        }).start(); //start()붙이면 바로실행시킨다.



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

    public String getLikeCount(int recordIdx){

        RetrofitClient client = new RetrofitClient();
        Retrofit retrofit = client.setRetrofit();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Call<String> call = retrofitAPI.getCountLike(recordIdx);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()) {
                    String getstr = response.body().toString();
                    result = "" + getstr;
                    Log.d("좋아요카운트", getstr);
                    //System.out.println(getstr);
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("카운트 실패", Integer.toString(recordIdx));
                result = "0";
                t.printStackTrace();
            }
        });
        return result;
    }

    public String getLikeStatus(int recordIdx, String userId){

        RetrofitClient client = new RetrofitClient();
        Retrofit retrofit = client.setRetrofit();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Call<String> call = retrofitAPI.getLikeStatus(recordIdx, userId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()) {
                    String getstr = response.body().toString();
                    result2 = "" + getstr;
                    Log.d("좋아요 여부", getstr);
                    //System.out.println(getstr);
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("확인 실패", Integer.toString(recordIdx));
                result2 = "0";
                t.printStackTrace();
            }
        });
        return result2;
    }
}
