package com.mobile.vnews.activity.news;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mobile.vnews.module.BasicResponse;
import com.mobile.vnews.module.bean.News;
import com.mobile.vnews.service.Api;
import com.mobile.vnews.service.DefaultObserver;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.FormBody;
import okhttp3.RequestBody;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by xuantang on 11/27/17.
 */

public class NewsPresenter implements NewsContract.Presenter {
    // The tag for log
    private static final String TAG = NewsPresenter.class.getSimpleName();

    @NonNull
    private NewsFragment view;

    public NewsPresenter(@NonNull NewsFragment view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void load(int start, int count) {
        Api.getApiService().getHotNews(5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<News>>>(view.getActivity()) {
                    @Override
                    public void onSuccess(BasicResponse<List<News>> response) {
//                        Log.i(TAG, "onSuccess: " + response.getContent().size());
                        view.showHeader(response.getContent());
                    }

                    @Override
                    public void onFail(BasicResponse<List<News>> response) {
                        view.onFail();
                    }
                });
        Api.getApiService().getNews(start, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<News>>>(view.getActivity()) {
                    @Override
                    public void onSuccess(BasicResponse<List<News>> response) {
//                        Log.i(TAG, "onSuccess: " + response.getContent().size());
                        view.showResults(response.getContent());
                    }

                    @Override
                    public void onFail(BasicResponse<List<News>> response) {
                        view.onFail();
                    }
                });
    }

    @Override
    public void loadMore(int start, int count) {
        Api.getApiService().getNews(start, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<News>>>(view.getActivity()) {
                    @Override
                    public void onSuccess(BasicResponse<List<News>> response) {
                        view.showMore(response.getContent());
                    }

                    @Override
                    public void onFail(BasicResponse<List<News>> response) {
                        view.onFail();
                    }
                });
    }
}
