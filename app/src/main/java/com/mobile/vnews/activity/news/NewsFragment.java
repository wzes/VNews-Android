package com.mobile.vnews.activity.news;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mobile.vnews.fragment.BaseFragment;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by xuantang on 11/27/17.
 */

public class NewsFragment extends BaseFragment implements NewsContract.View {

    private NewsContract.Presenter presenter;

    public static NewsFragment getInstance() {
        return new NewsFragment();
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
    public void setPresenter(NewsContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }
}
