package com.mobile.vnews.activity.me.info;


import android.support.annotation.NonNull;

import com.mobile.vnews.activity.base.BasePresenter;
import com.mobile.vnews.activity.base.BaseView;
import com.mobile.vnews.module.bean.News;
import com.mobile.vnews.module.bean.User;

import java.util.List;

public interface InfoSettingContract {

    interface View extends BaseView<Presenter> {
        void showResults(@NonNull User user);
        void onFail();
    }

    interface Presenter extends BasePresenter {
        void load(String userID);
        void updatePhoto(String filename);
        void update(User user);
    }
}
