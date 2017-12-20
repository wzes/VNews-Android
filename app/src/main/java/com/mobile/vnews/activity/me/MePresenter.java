package com.mobile.vnews.activity.me;

import android.support.annotation.NonNull;

/**
 * Created by xuantang on 11/27/17.
 */

public class MePresenter implements MeContract.Presenter {
    // The tag for log
    private static final String TAG = MePresenter.class.getSimpleName();

    @NonNull
    private MeContract.View view;

    public MePresenter(@NonNull MeContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
