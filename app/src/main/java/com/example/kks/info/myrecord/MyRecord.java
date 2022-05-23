package com.example.kks.info.myrecord;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyRecord {
    @SerializedName("recordIdx") @Expose
    private int recordIdx;
    @SerializedName("img") @Expose
    private String img;

    public int getRecordIdx() {
        return recordIdx;
    }

    public void setRecordIdx(int recordIdx) {
        this.recordIdx = recordIdx;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
