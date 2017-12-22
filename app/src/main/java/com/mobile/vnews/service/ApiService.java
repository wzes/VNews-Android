package com.mobile.vnews.service;

import com.mobile.vnews.module.BasicResponse;
import com.mobile.vnews.module.bean.Comment;
import com.mobile.vnews.module.bean.News;
import com.mobile.vnews.module.bean.User;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.internal.operators.observable.ObservableError;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by xuantang on 11/30/17.
 */

public interface ApiService {
    /**
     * 网络请求超时时间毫秒
     */
    int DEFAULT_TIMEOUT = 15000;

    String HOST = "http://192.168.1.109:9909/";
    String API_SERVER_URL = HOST + "vnews/";

    /* USER SYSTEM */

    /**
     *
     * @param requestBody
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json",
            "Cache-Control: public, max-age=86400"})
    @POST("login")
    Observable<BasicResponse<User>> login(@Body RequestBody requestBody);

    /**
     *
     * @param user
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json",
            "Cache-Control: public, max-age=86400"})
    @PUT("user")
    Observable<BasicResponse<User>> updateUser(@Body User user);

    /**
     *
     * @param requestBody
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json",
            "Cache-Control: public, max-age=86400"})
    @POST("register")
    Observable<BasicResponse<String>> register(@Body RequestBody requestBody);

    /**
     *
     * @param telephone
     * @return
     */
    @Headers({"Accept: application/json",
            "Cache-Control: public, max-age=86400"})
    @GET("/user/tel/{telephone}")
    Observable<BasicResponse<String>> checkPhone(@Path("telephone") String telephone);

    /**
     *
     * @param ID
     * @return
     */
    @Headers({"Accept: application/json",
            "Cache-Control: public, max-age=86400"})
    @GET("/user/{ID}")
    Observable<BasicResponse<User>> getUser(@Path("ID") String ID);

    /**
     *
     * @param ID
     * @param file
     * @return
     */
    @Headers({"Accept: application/json",
            "Cache-Control: public, max-age=86400"})
    @POST("/user/{ID}/image")
    @Multipart
    Observable<BasicResponse<String>> uploadPhoto(@Path("ID") String ID,
                                                  @Part MultipartBody.Part file);


    /* NEWS SYSTEM */

    /**
     *
     * @param start
     * @param count
     * @return
     */
    @Headers({"Accept: application/json"})
    @GET("news")
    Observable<BasicResponse<List<News>>> getNews(@Query("start") int start,
                                                  @Query("count") int count);

    /**
     *
     * @param category
     * @param start
     * @param count
     * @return
     */
    @Headers({"Accept: application/json",
            "Cache-Control: public, max-age=86400"})
    @GET("news/{category}")
    Observable<BasicResponse<List<News>>> getNewsByCategory(
            @Path("category") String category,
            @Query("start") int start,
            @Query("count") int count);

    /**
     *
     * @param count
     * @return
     */
    @Headers({"Accept: application/json",
            "Cache-Control: public, max-age=86400"})
    @GET("news/hots")
    Observable<BasicResponse<List<News>>> getHotNews(@Query("count") int count);

    /**
     *
     * @param news_id
     * @return
     */
    @Headers({"Accept: application/json",
            "Cache-Control: public, max-age=86400"})
    @GET("news/detail/{news_id}")
    Observable<BasicResponse<News>> getNewsDetail(@Path("news_id") int news_id);

    /**
     *
     * @param user_id
     * @return
     */
    @Headers({"Accept: application/json",
            "Cache-Control: public, max-age=86400"})
    @GET("news/{user_id}/likes")
    Observable<BasicResponse<List<News>>> getLikeNewsByUser(@Path("user_id") String user_id);

    /**
     *
     * @param requestBody
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json",
            "Cache-Control: public, max-age=86400"})
    @POST("news/like")
    Observable<BasicResponse<String>> likeNews(@Body RequestBody requestBody);

    /**
     *
     * @param user_id
     * @param news_id
     * @return
     */
    @Headers({"Accept: application/json",
            "Cache-Control: public, max-age=86400"})
    @GET("news/{user_id}/like/{news_id}")
    Observable<BasicResponse<String>> checkLikeNews(@Path("user_id") String user_id,
                                                        @Path("news_id") int news_id);

    /**
     *
     * @param user_id
     * @param news_id
     * @return
     */
    @Headers({"Accept: application/json",
            "Cache-Control: public, max-age=86400"})
    @DELETE("news/{user_id}/like/{news_id}")
    Observable<BasicResponse<String>> cancelLikeNews(@Path("user_id") String user_id,
                                                        @Path("news_id") int news_id);

    /**
     *
     * @param requestBody
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json",
            "Cache-Control: public, max-age=86400"})
    @POST("news/view")
    Observable<BasicResponse<String>> viewNews(@Body RequestBody requestBody);


    /**
     *
     * @param user_id
     * @return
     */
    @Headers({"Accept: application/json",
            "Cache-Control: public, max-age=86400"})
    @GET("news/{user_id}/views")
    Observable<BasicResponse<List<News>>> getViewNews(@Path("user_id") String user_id);

    /**
     *
     * @param news_id
     * @return
     */
    @Headers({"Accept: application/json",
            "Cache-Control: public, max-age=86400"})
    @GET("comment/{news_id}")
    Observable<BasicResponse<List<Comment>>> getNewsComments(@Path("news_id") String news_id);

}
