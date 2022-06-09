package com.example.kks.controller;

import com.example.kks.info.follow.Follow;
import com.example.kks.info.myrecord.MyRecord;
import com.example.kks.login.PostUser;
import com.example.kks.search.Recommend;
import com.example.kks.search.Search;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @POST("/api/login")
    Call<PostUser> postData(@Body PostUser postUser);

    @GET("/api/login/get")
    Call<PostUser> getUser(@Query("userId") String userId);


    @GET("/api/record/countall")
    Call<String> getcountall(@Query("userId") String userId);

    @GET("/api/record/countmonth")
    Call<String> getcountmonth(@Query("userId") String userId, @Query("postDate") String date);

    @GET("/api/archive/category")
    Call<List<CatImg>> getcatimg(@Query("userId") String userId, @Query("categoryId") int categoryId);


    //검색
    @GET("/api/record/search/keywordtest")
    Call<ArrayList<Search>> getSearchResultTest(@Query("keyword") String keyword, @Query("loginUserId") String loginUserId, @Query("sort") int sort);


    //사용자 추천
    @GET("api/record/recommend")
    Call<ArrayList<Recommend>> getRecommend(@Query("categoryId") int categoryId, @Query("userId") String userId);


    //프로필
    @GET("api/info/follower")
    Call<ArrayList<Follow>> getFollower(@Query("userId") String userId);

    @GET("api/info/following")
    Call<ArrayList<Follow>> getFollowing(@Query("userId") String userId);

    @GET("api/info/follow/apply")
    Call<String> requestFollow(@Query("followerIdx") String followerIdx, @Query("followingIdx") String followingIdx);

    @GET("api/info/follow/cancel")
    Call<String> cancelFollow(@Query("followerIdx") String followerIdx, @Query("followingIdx") String followingIdx);

    @GET("api/info/follow/status")
    Call<String> getFollowStatus(@Query("userId") String userId, @Query("followId") String followId);

    @GET("api/info/myrecord")
    Call<ArrayList<MyRecord>> getMyRecords(@Query("userId") String userId);

    @GET("api/info/otherrecord")
    Call<ArrayList<MyRecord>> getotherRecords(@Query("userId") String userId);

    @GET("api/info/liked")
    Call<ArrayList<MyRecord>> getLikedRecords(@Query("userId") String userId);

    @POST("api/info/withdrawal")
    Call<String> WithdrawalUser(@Body String userId);


    //아카이브
    @GET("/api/archive/search")
    Call<ArrayList<Records>> getArchiveSearch(@Query("userId") String userId, @Query("categoryId") int categoryId, @Query("keyword") String keyword);


    //for 아카이브 검색 - 좋아요 개수 반환
    @GET("/api/likes/countlike")
    Call<String> getCountLike(@Query("recordIdx") int recordIdx);

    //for 아카이브 검색 = 좋아요 여부 반환
    @GET("/api/likes/getstatus")
    Call<String> getLikeStatus(@Query("recordIdx") int recordIdx, @Query("userId") String userId);

    //예슬 - 아카이브 검색 좋아요 test
    @GET("/api/likes/getlikecnttest")
    Call<Integer> getRecordLikeCntTest(@Query("recordIdx") int recordIdx);
    @GET("/api/likes/getlikeactivetest")
    Call<String> getRecordLikeActiveTest(@Query("recordIdx") int recordIdx, @Query("userId") String userId);

    //닉네임 수정
    @PUT("/api/login/updatename/userId={userId}")
    Call<Name> editName(@Path("userId") String userId, @Body Name name);

    //카카오프사 수정
    @PUT("/api/login/updateimage/userId={userId}")
    Call<ProfImg> editImage(@Path("userId") String userId, @Body ProfImg profimg);

    //일반 사진 프사 수정
    @Multipart
    @PUT("/api/login/updateimagefile/userId={userId}")
    Call<ProfImg> editImagefile(@Path("userId") String userId, @Part MultipartBody.Part images, @Part("ProfImg") ProfImg profimg);

}
