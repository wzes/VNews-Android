package com.mobile.vnews.activity.news;


import android.support.annotation.NonNull;

import com.mobile.vnews.activity.base.BasePresenter;
import com.mobile.vnews.activity.base.BaseView;
import com.mobile.vnews.module.bean.News;

import java.util.List;

public interface NewsContract {

    interface View extends BaseView<Presenter> {
        void showResults(@NonNull List<News> list);
        void showMore(@NonNull List<News> list);
        void showHeader(@NonNull List<News> list);
        void onFail();
    }

    interface Presenter extends BasePresenter {
        void load(int start, int count);
        void loadMore(int start, int count);
    }

}
