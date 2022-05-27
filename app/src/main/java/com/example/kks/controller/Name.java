package com.example.kks.controller;

import com.google.gson.annotations.SerializedName;

public class Name {
    @SerializedName("nickName")
    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
