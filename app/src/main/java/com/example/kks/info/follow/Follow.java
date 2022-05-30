package com.example.kks.info.follow;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Follow {

    @SerializedName("userId") @Expose
    private String userId;
    @SerializedName("userImg") @Expose
    private String img;
    @SerializedName("nickName") @Expose
    private String nickname;

    private int followstatus;
    public int getFollowstatus() {
        return followstatus;
    }
    public void setFollowstatus(int followstatus) {
        this.followstatus = followstatus;
    }


    public Follow(String userId, String img, String nickname) {
        this.userId = userId;
        this.img = img;
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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
