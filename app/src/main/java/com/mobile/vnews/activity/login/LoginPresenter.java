package com.mobile.vnews.activity.login;

import com.alibaba.fastjson.JSON;
import com.mobile.vnews.application.AppPreferences;
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
 * Created by xuantang on 12/12/17.
 */

public class LoginPresenter implements LoginContract.Presenter {

    LoginFragment loginFragment;

    public LoginPresenter(LoginFragment loginFragment) {
        this.loginFragment = loginFragment;
        loginFragment.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void login(String username, String password) {
//        RequestBody formBody = new FormBody.Builder()
//                .add("username", username)
//                .add("password", password)
//                .build();
        HashMap<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSON.toJSONString(map));
        Api.getApiService()
                .login(requestBody )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<User>>(loginFragment.getActivity()) {
                    @Override
                    public void onSuccess(BasicResponse<User> response) {
                        loginFragment.onSuccess(response.getContent());
                    }

                    @Override
                    public void onFail(BasicResponse<User> response) {
                        loginFragment.onFail();
                    }

                });
    }

    @Override
    public void loadLastLoginUser() {
        if (!AppPreferences.getVersion().equals("0")) {
            loginFragment.showUserImage(AppPreferences.getVersion());
        }
    }
}
