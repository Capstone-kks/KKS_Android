package com.example.kks.info.follow;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Follow {

    @SerializedName("userImg") @Expose
    private String img;
    @SerializedName("userId") @Expose
    private String userId;

    public Follow(String ui){
        userId = ui;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) { this.userId = userId; }
}
