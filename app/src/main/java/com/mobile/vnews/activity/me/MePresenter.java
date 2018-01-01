package com.mobile.vnews.activity.me;

import android.support.annotation.NonNull;

import com.mobile.vnews.application.AppPreferences;
import com.mobile.vnews.module.BasicResponse;
import com.mobile.vnews.module.bean.User;
import com.mobile.vnews.service.Api;
import com.mobile.vnews.service.DefaultObserver;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xuantang on 11/27/17.
 */

public class MePresenter implements MeContract.Presenter {
    // The tag for log
    private static final String TAG = MePresenter.class.getSimpleName();

    @NonNull
    private MeFragment view;

    public MePresenter(@NonNull MeFragment view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void load() {
        if (AppPreferences.getLoginState()) {
            Api.getApiService().getUser(AppPreferences.getLoginUserID())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DefaultObserver<BasicResponse<User>>(view.getActivity()) {
                        @Override
                        public void onSuccess(BasicResponse<User> response) {
                           view.show(response.getContent());
                        }

                        @Override
                        public void onFail(BasicResponse<User> response) {

                        }
                    });
        }
    }
}
