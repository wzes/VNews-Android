package com.mobile.vnews.activity.news.detail;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.mobile.vnews.module.BasicResponse;
import com.mobile.vnews.module.bean.Message;
import com.mobile.vnews.module.bean.News;
import com.mobile.vnews.service.Api;
import com.mobile.vnews.service.DefaultObserver;

import org.json.JSONObject;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by xuantang on 12/21/17.
 */

public class NewsDetailPresenter implements NewsDetailContract.Presenter {

    private static final String TAG = NewsDetailPresenter.class.getSimpleName();

    private NewsDetailFragment mFragment;

    public NewsDetailPresenter(NewsDetailFragment mFragment) {
        this.mFragment = mFragment;
        mFragment.setPresenter(this);
    }


    @Override
    public void start() {

    }

    @Override
    public void load(int newsID) {
        Api.getApiService().getNewsDetail(newsID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<News>>(mFragment.getActivity(), true) {
                    @Override
                    public void onSuccess(BasicResponse<News> response) {
                        mFragment.showResults(response.getContent());
                    }

                    @Override
                    public void onFail(BasicResponse<News> response) {
                        mFragment.onShowFail();
                    }
                });
    }

    @Override
    public void search(String word) {

    }

    @Override
    public void like(String userID, int newID) {
        // json
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", userID);
        map.put("news_id", String.valueOf(newID));
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSON.toJSONString(map));

        Api.getApiService().likeNews(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<String>>(mFragment.getActivity()) {
                    @Override
                    public void onSuccess(BasicResponse<String> response) {
                        // TODO
                        Log.i(TAG, "onSuccess: ");
                    }
                });
    }

    @Override
    public void comment(Message message) {

    }

    @Override
    public void view(String userID, int newID) {
        // json
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", userID);
        map.put("news_id", String.valueOf(newID));
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSON.toJSONString(map));

        Api.getApiService().viewNews(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<String>>(mFragment.getActivity()) {
                    @Override
                    public void onSuccess(BasicResponse<String> response) {
                        Log.i(TAG, "onSuccess: ");
                    }
                });
    }
}
