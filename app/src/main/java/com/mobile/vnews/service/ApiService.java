package com.mobile.vnews.service;

import com.mobile.vnews.module.BasicResponse;
import com.mobile.vnews.module.bean.News;
import com.mobile.vnews.module.bean.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    String HOST = "http://localhost:9909/";
    String API_SERVER_URL = HOST + "vnews/";

    @Headers("Cache-Control: public, max-age=86400") //  设置缓存
    @GET("news")
    Observable<BasicResponse<List<News>>> getNews(@Query("start") int start,
                                                  @Query("count") int count);

    @Headers("Cache-Control: public, max-age=86400")
    @GET("news")
    Observable<BasicResponse<List<News>>> getNewsByCategory(
            @Query("category") String category,
            @Query("start") int start,
            @Query("count") int count);

    @Headers("Cache-Control: public, max-age=86400")
    @FormUrlEncoded
    @POST("login")
    Observable<BasicResponse<User>> login(@Field("username") String username,
                                          @Field("password") String password);

    @Headers("Cache-Control: public, max-age=86400")
    @PUT("user")
    Observable<BasicResponse<User>> updateUser(@Body User user);

    @Headers("Cache-Control: public, max-age=86400")
    @FormUrlEncoded
    @POST("register")
    Observable<BasicResponse<String>> register(String username, String password, String telephone);

    @Headers("Cache-Control: public, max-age=86400")
    @GET("/user/tel/{telephone}")
    Observable<BasicResponse<String>> checkPhone(@Path("telephone") String telephone);

    @Headers("Cache-Control: public, max-age=86400")
    @GET("/user/{ID}")
    Observable<BasicResponse<User>> getUser(@Path("ID") String ID);

}
