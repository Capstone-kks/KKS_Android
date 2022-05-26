package com.example.kks.info.liked;

public class RecordLike {
    private String status;
    private int likeCnt;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getLikeCnt() {
        return likeCnt;
    }

    public void setLikeCnt(int likeCnt) {
        this.likeCnt = likeCnt;
    }

    public RecordLike(String status, int likeCnt) {
        this.status = status;
        this.likeCnt = likeCnt;
    }
}
