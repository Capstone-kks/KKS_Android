package com.example.kks.info.follow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.kks.R;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.FollowViewHolder> {

    private Context context;
    private ArrayList<Follow> datalist;

    public FollowAdapter(Context c, ArrayList<Follow> list){
        context = c;
        datalist = list;
    }

    @NonNull
    @Override
    public FollowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.follow_list_recyclerview_item,parent,false);
        return new FollowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowViewHolder holder, int position) {

        Glide.with(context).load(datalist.get(position).getImg()).apply(RequestOptions.bitmapTransform(new CropCircleTransformation())).into(holder.img);
        holder.userId.setText(datalist.get(position).getUserId());

        holder.like_imb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:좋아요 취소 & 리스트 삭제
            }
        });
    }


    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class FollowViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView userId;
        public ImageButton like_imb;

        public FollowViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.follow_user_img);
            userId = itemView.findViewById(R.id.follow_user_id);
            like_imb = itemView.findViewById(R.id.follow_imgbtn);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO:내 정보 상세페이지 이동 - userId 이용해서 서버에서 이미지 받아서 액티비티로 넘기기
                }
            });
        }
    }
}
