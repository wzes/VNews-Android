package com.mobile.vnews.activity.news.detail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mobile.vnews.R;

public class NewsDetailActivity extends AppCompatActivity {

    NewsDetailFragment mNewsDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        int newID = getIntent().getIntExtra("newsID", 0);

        if (savedInstanceState != null) {
            mNewsDetailFragment = (NewsDetailFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, NewsDetailFragment.class.getSimpleName());
        } else {
            mNewsDetailFragment = NewsDetailFragment.newInstance(newID);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.news_detail_container, mNewsDetailFragment, NewsDetailFragment.class.getSimpleName())
                    .commit();
        }

        new NewsDetailPresenter(mNewsDetailFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mNewsDetailFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState,
                    NewsDetailFragment.class.getSimpleName(), mNewsDetailFragment);
        }
    }
}
