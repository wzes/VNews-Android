package com.mobile.vnews.activity.register;


import com.mobile.vnews.activity.base.BasePresenter;
import com.mobile.vnews.activity.base.BaseView;

public interface RegisterContract {

    interface View extends BaseView<Presenter> {
        void showMessage(String message);
        void onSuccess();
        void onFail();
        void onLoading();
    }

    interface Presenter extends BasePresenter {
        void register(String username, String password);
    }
}
