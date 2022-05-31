package com.example.kks.login;

import com.google.gson.annotations.SerializedName;

public class PostUser {
    //@SerializedName("userIndex")
    //private int userIndex;

    @SerializedName("userId")
    private String userId;

    @SerializedName("nickName")
    private String nickName;

    @SerializedName("userImg")
    private String userImg;

    //public int getUserIndex() { return userIndex;}
    //public void setUserIndex(int userIndex) {this.userIndex = userIndex;}

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
