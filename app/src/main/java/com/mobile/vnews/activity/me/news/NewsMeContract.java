package com.mobile.vnews.activity.me.news;


import android.support.annotation.NonNull;

import com.mobile.vnews.activity.base.BasePresenter;
import com.mobile.vnews.activity.base.BaseView;
import com.mobile.vnews.module.bean.News;

import java.util.List;

public interface NewsMeContract {

    interface View extends BaseView<Presenter> {
        void showResults(@NonNull List<News> list);
        void onFail();
    }

    interface Presenter extends BasePresenter {
        void load(int start, int count, String title);
    }
}
