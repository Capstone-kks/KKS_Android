package com.example.kks.controller;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

public class Records {
    @SerializedName("recordIdx")
    private int recordIdx;

    @SerializedName("userId")
    private String userId;

    @SerializedName("title")
    private String title;

    @SerializedName("categoryId")
    private int categoryId;

    @SerializedName("rate")
    private int rate;

    @SerializedName("content")
    private String content;

    @SerializedName("postPublic")
    private int postPublic;

    @SerializedName("imgUrl")
    private String imgUrl;

    @SerializedName("postDate")
    private String postDate;

    @SerializedName("commentCount")
    private int commentCount;

    public int getRecordIdx(){ return recordIdx; }
    public void setRecordIdx(int recordIdx){ this.recordIdx = recordIdx;}

    public String getUserId(){ return userId; }
    public void setUserId(String userId){this.userId = userId;}

    public String getTitle(){return title;}
    public void setTitle(){this.title=title;}

    public String getImgUrl(){return imgUrl;}
    public void setImgUrl(Bitmap img) {
        this.imgUrl = imgUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRate() {
        return rate;
    }

    public String getContent() {
        return content;
    }
    public String getPostDate() { return postDate;}

    public void setRate(int rate) {
        this.rate = rate;
    }

}
