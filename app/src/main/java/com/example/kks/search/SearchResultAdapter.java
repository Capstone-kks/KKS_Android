package com.example.kks.search;

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
import jp.wasabeef.glide.transformations.CropCircleTransformation;
public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchViewHolder> {

    private Context context;
    private ArrayList<SearchTest> datalist = null;

    public SearchResultAdapter(Context c, ArrayList<SearchTest> list){
        context = c;
        datalist = list;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_recyclerview_item,parent,false);
        SearchViewHolder viewHolder = new SearchViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {

        //holder.img.setImageBitmap(datalist.get(position).getImg());
        holder.title.setText(datalist.get(position).getTitle());
        holder.userNickName.setText(datalist.get(position).getNickName());
        holder.rate.setText(String.valueOf(datalist.get(position).getRate()));
        if(datalist.get(position).getLiked() == 0)
            holder.liked.setImageResource(R.drawable.ic_baseline_favorite_24);
        else if(datalist.get(position).getLiked() == 1)
            holder.liked.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        holder.likedCnt.setText(Integer.toString(datalist.get(position).getLikedCnt()));
        holder.text.setText(datalist.get(position).getText());
        holder.date.setText(datalist.get(position).getDate());
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
            liked = itemView.findViewById(R.id.search_result_recyclerview_item_like);
            likedCnt = itemView.findViewById(R.id.search_result_recyclerview_item_likeCount);
            text = itemView.findViewById(R.id.search_result_recyclerview_item_text);
            date = itemView.findViewById(R.id.search_result_recyclerview_item_date);
        }
    }
}
