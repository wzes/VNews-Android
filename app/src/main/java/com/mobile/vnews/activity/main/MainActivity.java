package com.mobile.vnews.activity.main;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.mobile.vnews.R;
import com.mobile.vnews.activity.BaseActivity;
import com.mobile.vnews.activity.message.MessageFragment;
import com.mobile.vnews.activity.message.MessagePresenter;
import com.mobile.vnews.activity.me.MeFragment;
import com.mobile.vnews.activity.me.MePresenter;
import com.mobile.vnews.activity.news.NewsFragment;
import com.mobile.vnews.activity.news.NewsPresenter;
import com.mobile.vnews.activity.word.WordFragment;
import com.mobile.vnews.activity.word.WordPresenter;
import com.mobile.vnews.util.BottomNavigationViewHelper;

public class MainActivity extends BaseActivity {
    // The tag for log
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String KEY_BOTTOM_NAVIGATION_VIEW_SELECTED_ID =
            "KEY_BOTTOM_NAVIGATION_VIEW_SELECTED_ID";
    
    private NewsFragment newsFragment;
    private WordFragment wordFragment;
    private MessageFragment messageFragment;
    private MeFragment meFragment;
    
    private NewsPresenter newsPresenter;
    private WordPresenter wordPresenter;
    private MessagePresenter messagePresenter;
    private MePresenter mePresenter;

    // The private views
    private BottomNavigationView bottomNavigationView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize the fragments
        initFragments(savedInstanceState);
        
        // Initialize the private views
        initViews();
        // Create the presenters
        newsPresenter = new NewsPresenter(newsFragment);
        wordPresenter = new WordPresenter(wordFragment);
        messagePresenter = new MessagePresenter(messageFragment);
        mePresenter = new MePresenter(meFragment);

        if (savedInstanceState != null) {
            int id = savedInstanceState.getInt(KEY_BOTTOM_NAVIGATION_VIEW_SELECTED_ID,
                    R.id.bottom_navigation_news);
            switch (id) {
                case R.id.bottom_navigation_news: {
                    showFragment(newsFragment);

                    break;
                }

                case R.id.bottom_navigation_word: {
                    showFragment(wordFragment);

                    break;
                }

                case R.id.bottom_navigation_message: {
                    showFragment(messageFragment);

                    break;
                }

                case R.id.bottom_navigation_mine: {
                    showFragment(meFragment);

                    break;
                }

                default: {
                    break;
                }
            }
        } else {
            showFragment(newsFragment);
        }

        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.bottom_navigation_news: {
                            showFragment(newsFragment);

                            break;
                        }

                        case R.id.bottom_navigation_word: {
                            showFragment(wordFragment);

                            break;
                        }

                        case R.id.bottom_navigation_message: {
                            showFragment(messageFragment);

                            break;
                        }

                        case R.id.bottom_navigation_mine: {
                            showFragment(meFragment);

                            break;
                        }

                        default: {
                            break;
                        }
                    }

                    getSupportFragmentManager().beginTransaction().commit();

                    return true;
                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_BOTTOM_NAVIGATION_VIEW_SELECTED_ID,
                bottomNavigationView.getSelectedItemId());
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (newsFragment.isAdded()) {
            fragmentManager.putFragment(outState, NewsFragment.class.getSimpleName(),
                    newsFragment);
        }
        if (wordFragment.isAdded()) {
            fragmentManager.putFragment(outState, WordFragment.class.getSimpleName(),
                    wordFragment);
        }
        if (messageFragment.isAdded()) {
            fragmentManager.putFragment(outState, MessageFragment.class.getSimpleName(),
                    messageFragment);
        }
        if (meFragment.isAdded()) {
            fragmentManager.putFragment(outState, MeFragment.class.getSimpleName(),
                    meFragment);
        }
    }

    /**
     * Initializes the private views
     */
    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
    }

    /**
     * TODO
     * @param savedInstanceState
     */
    private void initFragments(Bundle savedInstanceState) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            newsFragment = NewsFragment.getInstance();
            wordFragment = WordFragment.getInstance();
            messageFragment = MessageFragment.getInstance();
            meFragment = MeFragment.getInstance();
        } else {
            newsFragment = (NewsFragment) fragmentManager.getFragment(savedInstanceState,
                    NewsFragment.class.getSimpleName());
            wordFragment = (WordFragment) fragmentManager.getFragment(savedInstanceState,
                    WordFragment.class.getSimpleName());
            messageFragment = (MessageFragment) fragmentManager.getFragment(savedInstanceState,
                    MessageFragment.class.getSimpleName());
            meFragment = (MeFragment) fragmentManager.getFragment(savedInstanceState,
                    MeFragment.class.getSimpleName());
        }

        if (!newsFragment.isAdded()) {
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_content, newsFragment, NewsFragment.class.getSimpleName())
                    .commit();
        }

        if (!wordFragment.isAdded()) {
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_content, wordFragment, WordFragment.class.getSimpleName())
                    .commit();
        }

        if (!messageFragment.isAdded()) {
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_content, messageFragment, MessageFragment.class.getSimpleName())
                    .commit();
        }

        if (!meFragment.isAdded()) {
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_content, meFragment, MeFragment.class.getSimpleName())
                    .commit();
        }
    }

    /**
     * TODO
     * @param fragment
     */
    private void showFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        if (fragment instanceof NewsFragment) {
            fm.beginTransaction()
                    .show(newsFragment)
                    .hide(wordFragment)
                    .hide(messageFragment)
                    .hide(meFragment)
                    .commit();

        } else if (fragment instanceof WordFragment) {
            fm.beginTransaction()
                    .show(wordFragment)
                    .hide(newsFragment)
                    .hide(messageFragment)
                    .hide(meFragment)
                    .commit();
        } else if (fragment instanceof MessageFragment) {
            fm.beginTransaction()
                    .show(messageFragment)
                    .hide(newsFragment)
                    .hide(wordFragment)
                    .hide(meFragment)
                    .commit();
        } else if (fragment instanceof MeFragment) {
            fm.beginTransaction()
                    .show(meFragment)
                    .hide(newsFragment)
                    .hide(wordFragment)
                    .hide(messageFragment)
                    .commit();
        }
    }

}
