package com.mobile.vnews.activity.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.vnews.R;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by xuantang on 11/27/17.
 */

public class MeFragment extends Fragment implements MeContract.View {

    private MeContract.Presenter presenter;

    public static MeFragment getInstance() {
        return new MeFragment();
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
    public void setPresenter(MeContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        return view;
    }
}
