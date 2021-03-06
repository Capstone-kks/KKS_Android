package com.example.kks.info.follow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.kks.R;
import com.example.kks.controller.RetrofitAPI;
import com.example.kks.controller.RetrofitClient;
import com.example.kks.info.myrecord.MyRecordActivity;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.FollowViewHolder> {

    private Context context;
    private ArrayList<Follow> datalist;
    private String userId, LoginUserId;
    private boolean check;

    private RetrofitClient client = new RetrofitClient();
    private Retrofit retrofit = client.setRetrofit();
    private RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

    public FollowAdapter(Context c, ArrayList<Follow> list, String ui, String lui, boolean ch){
        context = c;
        datalist = list;
        userId = ui;
        LoginUserId = lui;
        check = ch;
    }

    @NonNull
    @Override
    public FollowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.follow_list_recyclerview_item,parent,false);
        FollowViewHolder viewHolder = new FollowViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FollowViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(datalist.get(position).getImg()).apply(RequestOptions.bitmapTransform(new CropCircleTransformation())).into(holder.img);
        holder.nickName.setText(datalist.get(position).getNickname());

        if(check){
            if(LoginUserId.equals(datalist.get(position).getUserId())) {
                holder.like_imb.setVisibility(View.GONE);
            }
            else{
                String tempLoginUserId = "'" + LoginUserId + "'";
                String uI = "'" + datalist.get(position).getUserId() + "'";
                retrofitAPI.getFollowStatus(tempLoginUserId, uI).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.body() != null) {
                            if (response.body().equals("1")) {
                                holder.like_imb.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                                datalist.get(position).setFollowstatus(1);
                            }
                            else if (response.body().equals("0")) {
                                holder.like_imb.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
                                datalist.get(position).setFollowstatus(0);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e("followAdapter error", t.getMessage());
                    }
                });
            }
        }
        else
            holder.like_imb.setVisibility(View.GONE);


        holder.like_imb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(datalist.get(position).getFollowstatus() == 1){//????????? ??????
                    Log.d("????????? ?????? ?????? ??????","??????");
                    datalist.get(position).setFollowstatus(0);
                    //???????????? ??????
                    holder.like_imb.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
                    //?????? ??????
                    retrofitAPI.cancelFollow(userId, datalist.get(position).getUserId()).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.body() != null) {
                                int result = Integer.parseInt(response.body());
                                if(result == 1)
                                    Toast.makeText(context, datalist.get(position).getNickname() + "??? ????????? ??????", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.d("????????? ?????? ??????",call.toString());
                        }
                    });
                    //????????? ????????? ?????????
                    Toast.makeText(context, datalist.get(position).getNickname() + "?????? ????????? ??????", Toast.LENGTH_LONG).show();
                }
                else if(datalist.get(position).getFollowstatus() == 0){//????????? ??????
                    datalist.get(position).setFollowstatus(1);
                    //???????????? ??????
                    holder.like_imb.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                    //????????????
                    retrofitAPI.requestFollow(userId, datalist.get(position).getUserId()).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.body() != null) {
                                int result = Integer.parseInt(response.body());
                                if(result == 1)
                                    Toast.makeText(context, datalist.get(position).getNickname() + "?????? ?????????", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.d("????????? ?????? ??????",call.toString());
                        }
                    });
                    //????????? ????????? ?????????
                    Toast.makeText(context, datalist.get(position).getNickname() + "?????? ?????????", Toast.LENGTH_LONG).show();
                }

            }
        });

    }


    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class FollowViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView nickName;
        ImageButton like_imb;

        public FollowViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.follow_user_img);
            nickName = itemView.findViewById(R.id.follow_user_id);
            like_imb = itemView.findViewById(R.id.follow_imgbtn);

            if(FollowActivity.follow_num == 1 & userId.equals(LoginUserId))
                like_imb.setVisibility(View.GONE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    Intent intent = new Intent(context, MyRecordActivity.class);
                    intent.putExtra("userId",datalist.get(pos).getUserId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
