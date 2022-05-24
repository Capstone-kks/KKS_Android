package com.example.kks.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Search {
    @SerializedName("key") @Expose
    private int key;
    @SerializedName("img") @Expose private Bitmap img;
    @SerializedName("title") @Expose private String title;
    @SerializedName("userid") @Expose private String userId;
    @SerializedName("id") @Expose private String id;
    @SerializedName("rate") @Expose private int rate;
    @SerializedName("likedcnt") @Expose private int likedCnt;
    @SerializedName("liked") @Expose private int liked;
    @SerializedName("text") @Expose private String text;
    @SerializedName("date") @Expose private String date;

    public Search(int k,  String t, String ui, String id, int r, int lc, int l, String c, String d){
        key = k;
        //img = i;
        title = t;
        userId = ui;
        this.id = id;
        rate = r;
        likedCnt = lc;
        liked = l;
        text = c;
        date = d;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) { this.userId = userId; }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getLikedCnt() {
        return likedCnt;
    }

    public void setLikedCnt(int likedCnt) {
        this.likedCnt = likedCnt;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
