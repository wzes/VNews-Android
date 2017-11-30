package com.mobile.vnews.activity.message;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mobile.vnews.fragment.BaseFragment;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by xuantang on 11/27/17.
 */

public class MessageFragment extends BaseFragment implements MessageContract.View {

    private MessageContract.Presenter presenter;

    public static MessageFragment getInstance() {
        return new MessageFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    public void setPresenter(MessageContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }
}
