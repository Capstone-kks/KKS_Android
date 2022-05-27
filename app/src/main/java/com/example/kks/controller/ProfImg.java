package com.example.kks.controller;

import com.google.gson.annotations.SerializedName;

public class ProfImg {
    @SerializedName("userImg")
    private String userImg;

    public String getUserImg(){
        return userImg;
    }
    public void setUserImg(String userImg){
        this.userImg = userImg;
    }
}
