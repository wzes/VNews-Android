package com.mobile.vnews.activity.word.detail;

import android.support.annotation.NonNull;

import com.mobile.vnews.activity.base.BasePresenter;
import com.mobile.vnews.activity.base.BaseView;
import com.mobile.vnews.module.bean.Word;
import com.mobile.vnews.module.bean.WordCollect;

/**
 * Created by xuantang on 12/25/17.
 */

public class WordDetailContract {
    interface View extends BaseView<Presenter> {
        void showResult(@NonNull Word word);
        void onSearchFail();
    }

    interface Presenter extends BasePresenter {
        void load(String word);
        void addCollect(WordCollect wordCollect);
    }
}
