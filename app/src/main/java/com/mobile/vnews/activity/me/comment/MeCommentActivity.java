package com.mobile.vnews.activity.me.comment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mobile.vnews.R;

public class MeCommentActivity extends AppCompatActivity {
    MeCommentFragment mMeCommentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        //
        if (savedInstanceState != null) {
            mMeCommentFragment = (MeCommentFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState,
                            MeCommentFragment.class.getSimpleName());
        } else {
            mMeCommentFragment = MeCommentFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.news_detail_container, mMeCommentFragment,
                            MeCommentFragment.class.getSimpleName())
                    .commit();
        }
        new MeCommentPresenter(mMeCommentFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMeCommentFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState,
                    MeCommentFragment.class.getSimpleName(), mMeCommentFragment);
        }
    }
}
