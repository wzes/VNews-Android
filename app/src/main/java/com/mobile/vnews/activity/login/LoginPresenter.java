package com.mobile.vnews.activity.login;

import com.mobile.vnews.application.AppPreferences;
import com.mobile.vnews.module.BasicResponse;
import com.mobile.vnews.module.bean.User;
import com.mobile.vnews.service.Api;
import com.mobile.vnews.service.DefaultObserver;

import io.reactivex.schedulers.Schedulers;

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
        Api.getApiService()
                .login(username, password)
                .subscribeOn(Schedulers.io())
                .subscribe(new DefaultObserver<BasicResponse<User>>(loginFragment.getActivity()) {
                    @Override
                    public void onSuccess(BasicResponse<User> response) {
                        loginFragment.onSuccess();
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
