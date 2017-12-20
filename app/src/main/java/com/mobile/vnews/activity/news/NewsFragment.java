package com.mobile.vnews.activity.news;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mobile.vnews.R;
import com.mobile.vnews.module.bean.News;
import com.mobile.vnews.util.loader.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by xuantang on 11/27/17.
 */

public class NewsFragment extends Fragment implements NewsContract.View {

    @BindView(R.id.fragment_news_recycler_view)
    RecyclerView mNewsRecyclerView;
    @BindView(R.id.fragment_news_refresh_layout)
    SwipeRefreshLayout mNewsRefreshLayout;
    Unbinder unbinder;
    private NewsContract.Presenter mPresenter;
    private NewsAdapter mNewsAdapter;
    private List<News> mList;


    private View header;
    private Banner banner;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        header = getActivity().getLayoutInflater().inflate(R.layout.news_item_header, container, false);
        banner = header.findViewById(R.id.fragment_news_banner);
        unbinder = ButterKnife.bind(this, view);
        mNewsRefreshLayout.setOnRefreshListener(() -> {
            // load data
            mPresenter.load(0, 8);
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        // set false
        if (mNewsRefreshLayout != null) {
            mNewsRefreshLayout.setRefreshing(true);
        }
        mPresenter.load(0, 8);
    }


    @Override
    public void showResults(@NonNull List<News> list) {
        //
        if (mNewsRefreshLayout != null) {
            mNewsRefreshLayout.setRefreshing(false);
        }
        //
        if (mNewsAdapter == null) {
            mList = list;
            mNewsAdapter = new NewsAdapter(R.layout.news_item_body, mList);
            // on click
            mNewsAdapter.setOnItemChildClickListener((adapter, view, position) -> {

            });
            // load more
            mNewsAdapter.setEnableLoadMore(false);
            mNewsAdapter.setOnLoadMoreListener(() -> mPresenter.loadMore(mList.size(), 8));
            mNewsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mNewsRecyclerView.setAdapter(mNewsAdapter);
            mNewsAdapter.setHeaderView(header);

        } else {
            mList.clear();
            mList.addAll(list);
            // load more
            mNewsAdapter.loadMoreComplete();
            mNewsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showMore(@NonNull List<News> list) {
        mList.addAll(list);
        // check data is over
        if (list.size() == 0) {
            mNewsAdapter.loadMoreEnd();
        } else {
            mNewsAdapter.loadMoreComplete();
        }
    }

    @Override
    public void showHeader(@NonNull List<News> list) {
        List<String> images = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (News news : list) {
            images.add(news.getImage());
            titles.add(news.getTitle());
        }
        banner.setImages(images)
                .setBannerStyle(5)
                .setBannerTitles(titles)
                .setDelayTime(5000)
                .setIndicatorGravity(7)
                .setImageLoader(new GlideImageLoader())
                .start();
    }

    @Override
    public void onFail() {
        // set false
        if (mNewsRefreshLayout != null) {
            mNewsRefreshLayout.setRefreshing(false);
        }
        mNewsAdapter.loadMoreEnd();
    }

    @Override
    public void setPresenter(NewsContract.Presenter presenter) {
        this.mPresenter = checkNotNull(presenter);
    }


}
