package com.example.kks.controller;

import com.google.gson.annotations.SerializedName;

public class CatImg {
    @SerializedName("categoryId")
    private int categoryId;

    @SerializedName("imgUrl")
    private String imgUrl;

    @SerializedName("recordIdx")
    private int recordIdx;

    public int getCategoryId(){
        return categoryId;
    }
    public void setCategoryId(int categoryId){
        this.categoryId = categoryId;
    }

    public int getRecordIdx(){
        return recordIdx;
    }
    public void setRecordIdx(int recordIdx){
        this.recordIdx = recordIdx;
    }

    public String getImgUrl() {return imgUrl;}
    public void setImgUrl(String imgUrl){
        this.imgUrl = imgUrl;
    }
}
