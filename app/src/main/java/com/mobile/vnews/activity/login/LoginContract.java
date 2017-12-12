package com.mobile.vnews.activity.login;


import com.mobile.vnews.activity.base.BasePresenter;
import com.mobile.vnews.activity.base.BaseView;
import com.mobile.vnews.module.bean.User;

public interface LoginContract {

    interface View extends BaseView<Presenter> {
        void showUserImage(String image);
        void showMessage(String message);
        void onSuccess();
        void onFail();
        void register();
    }

    interface Presenter extends BasePresenter {
        void login(String username, String password);
        void loadLastLoginUser();

    }

}
