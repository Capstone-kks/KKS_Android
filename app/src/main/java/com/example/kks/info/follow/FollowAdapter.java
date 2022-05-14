package com.example.kks.info.follow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kks.R;

import java.util.ArrayList;

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
        //SearchResultAdapter.SearchViewHolder viewHolder = new SearchResultAdapter.SearchViewHolder(view);
        return new FollowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowViewHolder holder, int position) {

        //holder.img.setImageBitmap(datalist.get(position).getImg());
        holder.userId.setText(datalist.get(position).getUserId());
    }


    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class FollowViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView userId;

        public FollowViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.follow_user_img);
            userId = itemView.findViewById(R.id.follow_user_id);
        }
    }
}
