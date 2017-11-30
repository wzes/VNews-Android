package com.mobile.vnews.fragment.news;

import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by xuantang on 11/27/17.
 */

public class NewsPresenter implements NewsContract.Presenter {
    // The tag for log
    private static final String TAG = NewsPresenter.class.getSimpleName();

    @NonNull
    private NewsContract.View view;

    public NewsPresenter(@NonNull NewsContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void start() {

    }


}
