package com.mobile.vnews.activity.word.collect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mobile.vnews.R;

public class WordCollectActivity extends AppCompatActivity {
    private WordCollectFragment mWordCollectFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_collect);
        String collect = getIntent().getStringExtra("collect");

        if (savedInstanceState != null) {
            mWordCollectFragment = (WordCollectFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, WordCollectFragment.class.getSimpleName());
        } else {
            mWordCollectFragment = WordCollectFragment.newInstance(collect);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.word_collect_container, mWordCollectFragment, WordCollectFragment.class.getSimpleName())
                    .commit();
        }

        new WordCollectPresenter(mWordCollectFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mWordCollectFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState,
                    WordCollectFragment.class.getSimpleName(), mWordCollectFragment);
        }
    }
}
