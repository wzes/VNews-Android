package com.mobile.vnews.activity.word.recite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mobile.vnews.R;

public class WordReciteActivity extends AppCompatActivity {

    private WordReciteFragment mWordReciteFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_recite);
        String tag = getIntent().getStringExtra("tag");

        if (savedInstanceState != null) {
            mWordReciteFragment = (WordReciteFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, WordReciteFragment.class.getSimpleName());
        } else {
            mWordReciteFragment = WordReciteFragment.newInstance(tag);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.word_recite_container, mWordReciteFragment, WordReciteFragment.class.getSimpleName())
                    .commit();
        }

        new WordRecitePresenter(mWordReciteFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mWordReciteFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState,
                    WordReciteFragment.class.getSimpleName(), mWordReciteFragment);
        }
    }
}
