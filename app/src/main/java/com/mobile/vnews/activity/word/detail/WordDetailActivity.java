package com.mobile.vnews.activity.word.detail;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mobile.vnews.R;

public class WordDetailActivity extends AppCompatActivity {
    private WordDetailFragment mWordDetailFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_detail);
        String word = getIntent().getStringExtra("word");

        if (savedInstanceState != null) {
            mWordDetailFragment = (WordDetailFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, WordDetailFragment.class.getSimpleName());
        } else {
            mWordDetailFragment = WordDetailFragment.newInstance(word);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.word_detail_container, mWordDetailFragment, WordDetailFragment.class.getSimpleName())
                    .commit();
        }

        new WordDetailPresenter(mWordDetailFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mWordDetailFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState,
                    WordDetailFragment.class.getSimpleName(), mWordDetailFragment);
        }
    }
}
