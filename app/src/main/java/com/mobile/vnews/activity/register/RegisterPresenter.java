package com.mobile.vnews.activity.register;

import com.alibaba.fastjson.JSON;
import com.mobile.vnews.module.BasicResponse;
import com.mobile.vnews.module.bean.User;
import com.mobile.vnews.service.Api;
import com.mobile.vnews.service.DefaultObserver;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by xuantang on 12/13/17.
 */

public class RegisterPresenter implements RegisterContract.Presenter {

    RegisterActivity mActivity;
    RegisterFragment mFragment;

    public RegisterPresenter(RegisterActivity activity, RegisterFragment fragment) {
        this.mActivity = activity;
        this.mFragment = fragment;
        mFragment.setPresenter(this);
    }


    @Override
    public void start() {

    }

    @Override
    public void register(String username, String password) {
        HashMap<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        map.put("phone", mActivity.getPhone());
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSON.toJSONString(map));
        Api.getApiService()
                .register(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<String>>(mActivity) {
                    @Override
                    public void onSuccess(BasicResponse<String> response) {
                        mFragment.onSuccess();
                    }

                    @Override
                    public void onFail(BasicResponse<String> response) {
                        mFragment.onFail();
                    }

                });
    }
}
