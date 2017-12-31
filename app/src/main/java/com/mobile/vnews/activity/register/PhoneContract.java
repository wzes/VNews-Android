package com.mobile.vnews.activity.register;


import com.mobile.vnews.activity.base.BasePresenter;
import com.mobile.vnews.activity.base.BaseView;
import com.mobile.vnews.module.bean.User;

public interface PhoneContract {

    interface View extends BaseView<Presenter> {
        void showMessage(String message);
        void onSuccess(String code);
        void onFail(String message);
        void onLoading();
    }

    interface Presenter extends BasePresenter {
        void send(String phone);
        void load();
        void onNext();
    }
}
