package com.mobile.vnews.fragment.mine;

import android.support.annotation.NonNull;

import com.mobile.vnews.fragment.news.NewsContract;

/**
 * Created by xuantang on 11/27/17.
 */

public class MinePresenter implements MineContract.Presenter {
    // The tag for log
    private static final String TAG = MinePresenter.class.getSimpleName();

    @NonNull
    private MineContract.View view;

    public MinePresenter(@NonNull MineContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void start() {

    }


}
