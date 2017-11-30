package com.mobile.vnews.activity.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mobile.vnews.fragment.BaseFragment;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by xuantang on 11/27/17.
 */

public class MineFragment extends BaseFragment implements MineContract.View {

    private MineContract.Presenter presenter;

    public static MineFragment getInstance() {
        return new MineFragment();
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
    public void setPresenter(MineContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }
}
