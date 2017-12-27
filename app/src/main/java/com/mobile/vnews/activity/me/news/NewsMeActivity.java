package com.mobile.vnews.activity.me.news;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mobile.vnews.R;

public class NewsMeActivity extends AppCompatActivity {
    private NewsMeFragment mNewsMeFragment;

    private String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_me);
        title = getIntent().getStringExtra("title");
        if (savedInstanceState != null) {
            mNewsMeFragment = (NewsMeFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, NewsMeFragment.class.getSimpleName());
        } else {
            mNewsMeFragment = NewsMeFragment.newInstance(title);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.news_me_container, mNewsMeFragment, NewsMeFragment.class.getSimpleName())
                    .commit();
        }

        new NewsMePresenter(mNewsMeFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mNewsMeFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState,
                    NewsMeFragment.class.getSimpleName(), mNewsMeFragment);
        }
    }
}
