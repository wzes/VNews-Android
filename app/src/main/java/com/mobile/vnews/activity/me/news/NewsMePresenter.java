package com.mobile.vnews.activity.me.news;

import android.support.annotation.NonNull;

import com.mobile.vnews.application.AppPreferences;
import com.mobile.vnews.module.BasicResponse;
import com.mobile.vnews.module.bean.News;
import com.mobile.vnews.service.Api;
import com.mobile.vnews.service.DefaultObserver;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xuantang on 11/27/17.
 */

public class NewsMePresenter implements NewsMeContract.Presenter {
    // The tag for log
    private static final String TAG = NewsMePresenter.class.getSimpleName();

    @NonNull
    private NewsMeFragment view;

    public NewsMePresenter(@NonNull NewsMeFragment view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void load(int start, int count, String title) {
        if (title.equals("view")) {
            Api.getApiService().getViewNewsByUserID(AppPreferences.getLoginUserID())
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
        } else if (title.equals("like")) {
            Api.getApiService().getLikeNewsByUserID(AppPreferences.getLoginUserID())
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

    }
}
