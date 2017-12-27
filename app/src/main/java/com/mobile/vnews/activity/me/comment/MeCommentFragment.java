package com.mobile.vnews.activity.me.comment;

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
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mobile.vnews.R;
import com.mobile.vnews.activity.news.detail.comment.NewsCommentActivity;
import com.mobile.vnews.application.AppPreferences;
import com.mobile.vnews.module.bean.Comment;
import com.mobile.vnews.module.bean.Message;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by xuantang on 12/21/17.
 */

public class MeCommentFragment extends Fragment implements MeCommentContract.View {

    @BindView(R.id.fragment_me_comment_toolbar)
    Toolbar mFragmentMeCommentToolbar;
    @BindView(R.id.fragment_me_comment_app_bar)
    AppBarLayout mFragmentMeCommentAppBar;
    @BindView(R.id.fragment_me_comment_recycler_view)
    RecyclerView mFragmentMeCommentRecyclerView;
    @BindView(R.id.fragment_me_comment_refresh_layout)
    SwipeRefreshLayout mFragmentMeCommentRefreshLayout;
    private MeCommentAdapter mMeCommentAdapter;
    private List<Comment> mList;

    Unbinder unbinder;
    private MeCommentContract.Presenter mPresenter;

    @Override
    public void onResume() {
        super.onResume();
        // TODO
        // get username
        mPresenter.load(AppPreferences.getLoginUserID());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me_comment, container, false);
        unbinder = ButterKnife.bind(this, view);
        MeCommentActivity activity = (MeCommentActivity) getActivity();
        activity.setSupportActionBar(mFragmentMeCommentToolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        setHasOptionsMenu(true);
        return view;
    }

    public static MeCommentFragment newInstance() {
        return new MeCommentFragment();
    }

    @Override
    public void setPresenter(MeCommentContract.Presenter mPresenter) {
        this.mPresenter = checkNotNull(mPresenter);
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
    public void showComments(@NonNull List<Comment> comments) {
        if (mMeCommentAdapter == null) {
            mList = comments;
            mMeCommentAdapter = new MeCommentAdapter(R.layout.comment_item, mList);

            // showDetail
            mMeCommentAdapter.setOnItemClickListener((adapter, view, position) -> {
                Intent intent = new Intent(getActivity(), NewsCommentActivity.class);
                intent.putExtra("newsID", mList.get(position).getNewID());
                intent.putExtra("floor", mList.get(position).getFloor());
                startActivity(intent);
            });

            // Like and reply
            mMeCommentAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                switch (view.getId()) {
                    case R.id.comment_item_like:
                        break;
                    case R.id.comment_item_reply:
                        break;
                }
                Toast.makeText(getActivity(), "ItemChildClick item " + position, Toast.LENGTH_SHORT).show();
            });
            mFragmentMeCommentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mFragmentMeCommentRecyclerView.setAdapter(mMeCommentAdapter);
        } else {
            mList.clear();
            mList.addAll(comments);
            // load more
            mMeCommentAdapter.loadMoreComplete();
            mMeCommentAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCommentFail() {
        Toast.makeText(getActivity(), "Server is error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
