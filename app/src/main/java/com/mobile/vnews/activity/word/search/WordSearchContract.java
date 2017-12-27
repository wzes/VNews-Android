package com.mobile.vnews.activity.word.search;


import android.support.annotation.NonNull;

import com.mobile.vnews.activity.base.BasePresenter;
import com.mobile.vnews.activity.base.BaseView;

import java.util.List;

public interface WordSearchContract {

    interface View extends BaseView<Presenter> {
        void showResult(@NonNull List<String> words);
        void showDetail(String word);
    }

    interface Presenter extends BasePresenter {
        void search(String word);
    }

}
