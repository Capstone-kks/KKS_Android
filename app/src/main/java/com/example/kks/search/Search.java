package com.example.kks.search;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Search{

    @SerializedName("recordIdx") @Expose private int recordIdx;
    @SerializedName("userId") @Expose private String userId;
    @SerializedName("title") @Expose private String title;
    @SerializedName("categoryId") @Expose private int categoryId;
    @SerializedName("rate") @Expose private float rate;
    @SerializedName("content") @Expose private String content;
    @SerializedName("postPublic") @Expose private int postPublic;
    @SerializedName("imgUrl") @Expose private String imgUrl;
    @SerializedName("postDate") @Expose private String postDate;
    @SerializedName("commentCount") @Expose private int commentCount;

    public Search(int i,  String ui, String t, int ci, float r, String c, int p, String img, String d, int cc){
        recordIdx = i;
        userId = ui;
        title = t;
        categoryId = ci;
        rate = r;
        content = c;
        postPublic = p;
        imgUrl = img;
        postDate = d;
        commentCount = cc;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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

    public int getPostPublic() {
        return postPublic;
    }

    public void setPostPublic(int postPublic) {
        this.postPublic = postPublic;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
