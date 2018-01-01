package com.mobile.vnews.activity.me;


import android.support.annotation.NonNull;

import com.mobile.vnews.activity.base.BasePresenter;
import com.mobile.vnews.activity.base.BaseView;
import com.mobile.vnews.module.bean.User;

public interface MeContract {

    interface View extends BaseView<Presenter> {
        void show(@NonNull User user);
        void onFail();
    }

    interface Presenter extends BasePresenter {
        void load();
    }

}
