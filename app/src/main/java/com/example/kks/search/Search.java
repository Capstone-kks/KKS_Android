package com.example.kks.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Search {
    @SerializedName("recordIdx") @Expose private int recordIdx;
    @SerializedName("userId") @Expose private String userId;
    @SerializedName("nickName") @Expose private String nickName;
    @SerializedName("title") @Expose private String title;
    @SerializedName("rate") @Expose private float rate;
    @SerializedName("content") @Expose private String content;
    @SerializedName("postDate") @Expose private String postDate;
    @SerializedName("imgUrl") @Expose private String imgUrl;

    public Search(int recordIdx, String userId, String nickName, String title, float rate, String content, String postDate, String imgUrl) {
        this.recordIdx = recordIdx;
        this.userId = userId;
        this.nickName = nickName;
        this.title = title;
        this.rate = rate;
        this.content = content;
        this.postDate = postDate;
        this.imgUrl = imgUrl;
    }

    public int getRecordIdx() {
        return recordIdx;
    }

    public void setRecordIdx(int recordIdx) {
        this.recordIdx = recordIdx;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
