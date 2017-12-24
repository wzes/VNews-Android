package com.mobile.vnews.activity.message;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mobile.vnews.R;
import com.mobile.vnews.activity.news.NewsAdapter;
import com.mobile.vnews.activity.news.detail.NewsDetailActivity;
import com.mobile.vnews.application.AppPreferences;
import com.mobile.vnews.module.bean.Message;
import com.mobile.vnews.module.bean.News;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by xuantang on 11/27/17.
 */

public class MessageFragment extends Fragment implements MessageContract.View {

    @BindView(R.id.fragment_message_toolbar)
    Toolbar mFragmentMessageToolbar;
    @BindView(R.id.fragment_message_app_bar)
    AppBarLayout mFragmentMessageAppBar;
    @BindView(R.id.fragment_message_recycler_view)
    RecyclerView mFragmentMessageRecyclerView;
    @BindView(R.id.fragment_message_empty_view)
    LinearLayout mFragmentMessageEmptyView;
    @BindView(R.id.fragment_message_fresh_layout)
    SwipeRefreshLayout mFragmentMessageFreshLayout;
    Unbinder unbinder;
    private MessageContract.Presenter mPresenter;

    private MessageAdapter mMessageAdapter;
    private List<Message> mList;


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
    public void setPresenter(MessageContract.Presenter mPresenter) {
        this.mPresenter = checkNotNull(mPresenter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (AppPreferences.getLoginState()) {
            mPresenter.load(AppPreferences.getLoginUserID());
            Log.i("TAG", "onResume: " + AppPreferences.getLoginUserID());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void showResults(@NonNull List<Message> list) {
        if (mFragmentMessageFreshLayout != null) {
            mFragmentMessageFreshLayout.setRefreshing(false);
        }
        //
        if (mMessageAdapter == null) {
            mList = list;
            mMessageAdapter = new MessageAdapter(R.layout.message_item, mList);
            // on click
            mMessageAdapter.setOnItemClickListener((adapter, view, position) -> {
//                Intent intent = new Intent(getContext(), NewsDetailActivity.class);
//                intent.putExtra("newsID", mList.get(position).getID());
//                startActivity(intent);
            });

            mFragmentMessageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mFragmentMessageRecyclerView.setAdapter(mMessageAdapter);

            mFragmentMessageFreshLayout.setOnRefreshListener(() -> {
                if (AppPreferences.getLoginState()) {
                    mPresenter.load(AppPreferences.getLoginUserID());
                } else {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            mList.clear();
            mList.addAll(list);
            // load more
            mMessageAdapter.loadMoreComplete();
            mMessageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onShowFail() {
        mFragmentMessageFreshLayout.setRefreshing(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
