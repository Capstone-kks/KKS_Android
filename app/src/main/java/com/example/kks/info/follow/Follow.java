package com.example.kks.info.follow;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Follow {

    @SerializedName("img") @Expose
    private Bitmap img;
    @SerializedName("userid") @Expose
    private String userId;

    public Follow(String ui){
        userId = ui;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) { this.userId = userId; }
}
