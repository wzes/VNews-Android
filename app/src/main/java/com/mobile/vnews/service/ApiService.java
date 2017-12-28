package com.mobile.vnews.service;

import com.mobile.vnews.module.BasicResponse;
import com.mobile.vnews.module.bean.Comment;
import com.mobile.vnews.module.bean.Message;
import com.mobile.vnews.module.bean.News;
import com.mobile.vnews.module.bean.User;
import com.mobile.vnews.module.bean.Word;

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
    int DEFAULT_TIMEOUT = 10000;

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
    @PUT("user_image")
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
    @GET("/user_image/tel/{telephone}")
    Observable<BasicResponse<String>> checkPhone(@Path("telephone") String telephone);

    /**
     *
     * @param ID
     * @return
     */
    @Headers({"Accept: application/json",
            "Cache-Control: public, max-age=86400"})
    @GET("/user_image/{ID}")
    Observable<BasicResponse<User>> getUser(@Path("ID") String ID);

    /**
     *
     * @param ID
     * @param file
     * @return
     */
    @Headers({"Accept: application/json",
            "Cache-Control: public, max-age=86400"})
    @POST("/user_image/{ID}/image")
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


    @Headers({"Accept: application/json",
            "Cache-Control: public, max-age=86400"})
    @GET("news/detail/{user_id}/{news_id}")
    Observable<BasicResponse<News>> getNewsDetailByUserID(@Path("user_id") String user_id,
                                                          @Path("news_id") int news_id);
    /**
     *
     * @param user_id
     * @return
     */
    @Headers({"Accept: application/json",
            "Cache-Control: public, max-age=86400"})
    @GET("news/{user_id}/likes")
    Observable<BasicResponse<List<News>>> getLikeNewsByUserID(@Path("user_id") String user_id);

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
    Observable<BasicResponse<List<News>>> getViewNewsByUserID(@Path("user_id") String user_id);

    /**
     *
     * @param news_id
     * @return
     */
    @Headers({"Accept: application/json",
            "Cache-Control: public, max-age=86400"})
    @GET("comment/{news_id}")
    Observable<BasicResponse<List<Comment>>> getNewsComments(@Path("news_id") String news_id);

    /**
     *
     * @param user_id
     * @return
     */
    @Headers({"Accept: application/json",
            "Cache-Control: public, max-age=86400"})
    @GET("comment/user/{user_id}")
    Observable<BasicResponse<List<Comment>>> getMyComments(@Path("user_id") String user_id);

    /**
     *
     * @param user_id
     * @param comment_id
     * @return
     */
    @Headers({"Accept: application/json",
            "Cache-Control: public, max-age=86400"})
    @GET("comment/{user_id}/like/{comment_id}")
    Observable<BasicResponse<String>> likeComment(@Path("user_id") String user_id,
                                                         @Path("comment_id") int comment_id);
    /**
     *
     * @param news_id
     * @param floor
     * @return
     */
    @Headers({"Accept: application/json",
            "Cache-Control: public, max-age=86400"})
    @GET("comment/{news_id}/{floor}")
    Observable<BasicResponse<List<Comment>>> getNewsCommentsByFloor(@Path("news_id") String news_id,
                                                             @Path("floor") String floor);

    /* Message System */

    @Headers({"Accept: application/json",
            "Cache-Control: public, max-age=86400"})
    @GET("message/{user_id}")
    Observable<BasicResponse<List<Message>>> getMessages(@Path("user_id") String user_id);


}
