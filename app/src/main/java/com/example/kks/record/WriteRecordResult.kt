package com.example.kks.record

import com.google.gson.annotations.SerializedName

data class WriteRecordResult(
    @SerializedName("notice") val notice: String, // 내용
)
