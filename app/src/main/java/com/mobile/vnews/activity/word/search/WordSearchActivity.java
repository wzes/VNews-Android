package com.mobile.vnews.activity.word.search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mobile.vnews.R;

public class WordSearchActivity extends AppCompatActivity {
    private WordSearchFragment mWordSearchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_search);

        if (savedInstanceState != null) {
            mWordSearchFragment = (WordSearchFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, WordSearchFragment.class.getSimpleName());
        } else {
            mWordSearchFragment = WordSearchFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.word_search_container, mWordSearchFragment, WordSearchFragment.class.getSimpleName())
                    .commit();
        }

        new WordSearchPresenter(mWordSearchFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mWordSearchFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState,
                    WordSearchFragment.class.getSimpleName(), mWordSearchFragment);
        }
    }
}
