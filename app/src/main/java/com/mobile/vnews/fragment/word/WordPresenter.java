package com.mobile.vnews.fragment.word;

import android.support.annotation.NonNull;

import com.mobile.vnews.fragment.news.NewsContract;

/**
 * Created by xuantang on 11/27/17.
 */

public class WordPresenter implements WordContract.Presenter {
    // The tag for log
    private static final String TAG = WordPresenter.class.getSimpleName();

    @NonNull
    private WordContract.View view;

    public WordPresenter(@NonNull WordContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void start() {

    }


}
