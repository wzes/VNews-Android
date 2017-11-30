package com.mobile.vnews.fragment.message;

import android.support.annotation.NonNull;

import com.mobile.vnews.fragment.news.NewsContract;

/**
 * Created by xuantang on 11/27/17.
 */

public class MessagePresenter implements MessageContract.Presenter {
    // The tag for log
    private static final String TAG = MessagePresenter.class.getSimpleName();

    @NonNull
    private MessageContract.View view;

    public MessagePresenter(@NonNull MessageContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void start() {

    }


}
