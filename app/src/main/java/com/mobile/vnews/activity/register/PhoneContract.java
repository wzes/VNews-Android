package com.mobile.vnews.activity.register;


import com.mobile.vnews.activity.base.BasePresenter;
import com.mobile.vnews.activity.base.BaseView;
import com.mobile.vnews.module.bean.User;

public interface PhoneContract {

    interface View extends BaseView<Presenter> {
        void showMessage(String message);
        void onSuccess();
        void onFail();
        void onNext(String phone);
        void onLoading();
    }

    interface Presenter extends BasePresenter {
        void send(String phone);
        void valid(String phone, String code);
        void load();
    }
}
