package com.mobile.vnews.activity.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.vnews.R;
import com.mobile.vnews.fragment.BaseFragment;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by xuantang on 11/27/17.
 */

public class NewsFragment extends Fragment implements NewsContract.View {

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
    public void setPresenter(NewsContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        return view;
    }
}
