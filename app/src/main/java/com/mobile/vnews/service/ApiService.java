package com.mobile.vnews.service;

import com.mobile.vnews.module.BasicResponse;
import com.mobile.vnews.module.bean.News;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by xuantang on 11/30/17.
 */

public interface ApiService {
    /**
     * 网络请求超时时间毫秒
     */
    int DEFAULT_TIMEOUT = 15000;

    String HOST = "http://ip:port/";
    String API_SERVER_URL = HOST + "vnews/";

    @Headers("Cache-Control: public, max-age=86400") //  设置缓存
    @GET("news/")
    Observable<BasicResponse<List<News>>> getNews();
}
