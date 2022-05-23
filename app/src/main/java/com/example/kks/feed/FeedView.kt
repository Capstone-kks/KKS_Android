package com.example.kks.feed

interface FeedView {
    fun onGetFeedLoading()
    fun onGetFeedSuccess(result: ArrayList<FeedResponse>)
    fun onGetFeedFailure(code : Int, message: String)
}