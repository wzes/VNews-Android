package com.mobile.vnews.activity.news.detail.comment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mobile.vnews.R;

public class NewsCommentActivity extends AppCompatActivity {
    NewsCommentFragment mNewsCommentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_comment);
        int newsID = getIntent().getIntExtra("newsID", 0);
        int floor = getIntent().getIntExtra("floor", 0);
        if (savedInstanceState != null) {
            mNewsCommentFragment = (NewsCommentFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, NewsCommentFragment.class.getSimpleName());
        } else {
            mNewsCommentFragment = NewsCommentFragment.newInstance(newsID, floor);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.news_comment_container, mNewsCommentFragment, NewsCommentFragment.class.getSimpleName())
                    .commit();
        }

        new NewsCommentPresenter(mNewsCommentFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mNewsCommentFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState,
                    NewsCommentFragment.class.getSimpleName(), mNewsCommentFragment);
        }
    }
}
