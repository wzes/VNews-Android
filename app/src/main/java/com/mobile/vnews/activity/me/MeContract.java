package com.mobile.vnews.activity.me;


import com.mobile.vnews.activity.base.BasePresenter;
import com.mobile.vnews.activity.base.BaseView;

public interface MeContract {

    interface View extends BaseView<Presenter> {
        void show(boolean loginState);
    }

    interface Presenter extends BasePresenter {
        void load();
    }

}
