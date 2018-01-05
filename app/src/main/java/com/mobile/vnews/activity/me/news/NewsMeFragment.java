package com.mobile.vnews.activity.me.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.vnews.R;
import com.mobile.vnews.activity.news.detail.NewsDetailActivity;
import com.mobile.vnews.module.bean.News;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by xuantang on 11/27/17.
 */

public class NewsMeFragment extends Fragment implements NewsMeContract.View {

    @BindView(R.id.fragment_news_me_recycler_view)
    RecyclerView mNewsMeRecyclerView;
    @BindView(R.id.fragment_news_me_refresh_layout)
    SwipeRefreshLayout mNewsMeRefreshLayout;
    Unbinder unbinder;
    @BindView(R.id.fragment_news_me_toolbar)
    Toolbar mFragmentNewsMeToolbar;
    @BindView(R.id.fragment_news_me_app_bar)
    AppBarLayout mFragmentNewsMeAppBar;
    private NewsMeContract.Presenter mPresenter;
    private NewsMeAdapter mNewsMeAdapter;
    private List<News> mList;

    private static String title;

    public static NewsMeFragment newInstance(String title) {
        NewsMeFragment.title = title;
        return new NewsMeFragment();
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
        View view = inflater.inflate(R.layout.fragment_news_me, container, false);
        unbinder = ButterKnife.bind(this, view);
        NewsMeActivity activity = (NewsMeActivity) getActivity();
        activity.setSupportActionBar(mFragmentNewsMeToolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        setHasOptionsMenu(true);
        if (title.equals("like")) {
            mFragmentNewsMeToolbar.setTitle(R.string.news_likes);
        } else {
            mFragmentNewsMeToolbar.setTitle(R.string.news_views);
        }

        mNewsMeRefreshLayout.setOnRefreshListener(() -> {
            // load data
            mPresenter.load(0, 8, title);
        });
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            getActivity().onBackPressed();
        }
        return true;
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
        mNewsMeRefreshLayout.setRefreshing(true);
        mPresenter.load(0, 8, title);
    }


    @Override
    public void showResults(@NonNull List<News> list) {
        //
        if (mNewsMeRefreshLayout != null) {
            mNewsMeRefreshLayout.setRefreshing(false);
        }
        //
        if (mNewsMeAdapter == null) {
            mList = list;
            mNewsMeAdapter = new NewsMeAdapter(R.layout.news_item_body, mList);
            // on click
            mNewsMeAdapter.setOnItemClickListener((adapter, view, position) -> {
                Intent intent = new Intent(getContext(), NewsDetailActivity.class);
                intent.putExtra("newsID", mList.get(position).getId());
                startActivity(intent);
            });
            mNewsMeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mNewsMeRecyclerView.setAdapter(mNewsMeAdapter);

        } else {
            mList.clear();
            mList.addAll(list);
            // load more
            mNewsMeAdapter.loadMoreComplete();
            mNewsMeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFail() {
        // set false
        if (mNewsMeRefreshLayout != null) {
            mNewsMeRefreshLayout.setRefreshing(false);
        }
        if (mNewsMeAdapter != null) {
            mNewsMeAdapter.loadMoreEnd();
        }
    }

    @Override
    public void setPresenter(NewsMeContract.Presenter mPresenter) {
        this.mPresenter = checkNotNull(mPresenter);
    }
}
