package com.example.kks.info.myrecord;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyRecord {
    @SerializedName("recordIdx") @Expose
    private int recordIdx;
    @SerializedName("imgUrl") @Expose
    private String imgUrl;

    public MyRecord(int recordIdx, String imgUrl) {
        this.recordIdx = recordIdx;
        this.imgUrl = imgUrl;
    }

    public int getRecordIdx() {
        return recordIdx;
    }

    public void setRecordIdx(int recordIdx) {
        this.recordIdx = recordIdx;
    }

    public String getImg() {
        return imgUrl;
    }

    public void setImg(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
