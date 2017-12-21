package com.mobile.vnews.activity.news.detail;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mobile.vnews.R;
import com.mobile.vnews.application.AppPreferences;
import com.mobile.vnews.module.bean.Message;
import com.mobile.vnews.module.bean.News;
import com.mobile.vnews.util.TimeUtils;
import com.mobile.vnews.util.select.WordSelectedTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by xuantang on 12/21/17.
 */

public class NewsDetailFragment extends Fragment implements NewsDetailContract.View {

    @BindView(R.id.news_details_image)
    ImageView mNewsDetailsImage;
    @BindView(R.id.news_details_toolbar)
    Toolbar mNewsDetailsToolbar;
    @BindView(R.id.news_details_toolbar_layout)
    CollapsingToolbarLayout mNewsDetailsToolbarLayout;
    @BindView(R.id.news_details_app_bar)
    AppBarLayout mNewsDetailsAppBar;
    @BindView(R.id.news_detail_source)
    TextView mNewsDetailSource;
    @BindView(R.id.news_detail_source_layout)
    LinearLayout mNewsDetailSourceLayout;
    @BindView(R.id.news_detail_content)
    WordSelectedTextView mNewsDetailContent;
    @BindView(R.id.news_details_nested_scroll_view)
    NestedScrollView mNewsDetailsNestedScrollView;
    @BindView(R.id.news_details_coordinator_layout)
    CoordinatorLayout mNewsDetailsCoordinatorLayout;
    Unbinder unbinder;
    @BindView(R.id.news_detail_description)
    TextView mNewsDetailDescription;
    @BindView(R.id.news_detail_date)
    TextView mNewsDetailDate;
    private NewsDetailContract.Presenter mPresenter;

    private static int newID;

    @Override
    public void onResume() {
        super.onResume();

        // TODO
        // get username
        mPresenter.view(AppPreferences.getLoginUserID(), newID);
        mPresenter.load(newID);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public static NewsDetailFragment newInstance(int newID) {
        NewsDetailFragment.newID = newID;
        return new NewsDetailFragment();
    }

    @Override
    public void setPresenter(NewsDetailContract.Presenter presenter) {
        this.mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showResults(@NonNull News news) {
        mNewsDetailContent.setText(news.getContent(), TextView.BufferType.SPANNABLE);
        mNewsDetailSource.setText(news.getSource());
        mNewsDetailDescription.setText(news.getDescription());
        mNewsDetailDate.setText(TimeUtils.millis2String(news.getPublishedAt()));

        Glide.with(getActivity()).load(news.getImage()).into(mNewsDetailsImage);

        mNewsDetailContent.setOnWordSelectedClickListener(word -> mPresenter.search(word));
    }

    @Override
    public void showWord(@NonNull String word) {

    }

    @Override
    public void onShowFail() {

    }

    @Override
    public void onSearchFail() {

    }

    @Override
    public void onLikeSuccess() {

    }

    @Override
    public void onLikeFail() {

    }

    @Override
    public void onCommentFail() {

    }

    @Override
    public void onCommentSuccess(Message message) {

    }

    @Override
    public void onLogin() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
