package com.example.kks.feed

import com.example.kks.record.Record

interface FeedView {
    fun onGetFeedLoading()
    fun onGetFeedSuccess(result: ArrayList<Record>)
    fun onGetFeedFailure(code : Int, message: String)
}