package com.mobile.vnews.activity.me.info;

import android.support.annotation.NonNull;

import com.mobile.vnews.module.BasicResponse;
import com.mobile.vnews.module.bean.User;
import com.mobile.vnews.service.Api;
import com.mobile.vnews.service.DefaultObserver;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xuantang on 11/27/17.
 */

public class InfoSettingPresenter implements InfoSettingContract.Presenter {
    // The tag for log
    private static final String TAG = InfoSettingPresenter.class.getSimpleName();

    @NonNull
    private InfoSettingFragment view;

    public InfoSettingPresenter(@NonNull InfoSettingFragment view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void load(String userID) {
        Api.getApiService().getUser(userID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<User>>(view.getActivity()) {
                    @Override
                    public void onSuccess(BasicResponse<User> response) {
//                        Log.i(TAG, "onSuccess: " + response.getContent().size());
                        view.showResults(response.getContent());
                    }

                    @Override
                    public void onFail(BasicResponse<User> response) {
                        view.onFail();
                    }
                });
    }

    @Override
    public void updatePhoto(String filename) {

    }

    @Override
    public void update(User user) {

    }
}
