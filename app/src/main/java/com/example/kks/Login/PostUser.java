package com.example.kks.Login;

import com.google.gson.annotations.SerializedName;

public class PostUser {
    @SerializedName("userId")
    private String userId;

    @SerializedName("nickName")
    private String nickName;

    @SerializedName("userImg")
    private String userImg;

    public String getUserId(){
        return userId;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getNickName(){
        return nickName;
    }
    public void setNickName(String nickName){
        this.nickName = nickName;
    }

    public String getUserImg(){
        return userImg;
    }
    public void setUserImg(String userImg){
        this.userImg = userImg;
    }
}
