package com.mobile.vnews.activity.word.recite;

import android.annotation.SuppressLint;
import android.os.Message;

import com.mobile.vnews.module.bean.WordCollect;
import com.mobile.vnews.module.dao.WordDao;
import com.mobile.vnews.module.database.AppDatabase;
import com.mobile.vnews.util.Utils;

import java.util.List;

/**
 * Created by xuantang on 12/28/17.
 */

public class WordRecitePresenter implements WordReciteContract.Presenter {
    private WordReciteFragment mFragment;

    public WordRecitePresenter(WordReciteFragment wordReciteFragment) {
        this.mFragment = wordReciteFragment;
    }

    @Override
    public void start() {

    }
    private List<WordCollect> mList;

    @SuppressLint("HandlerLeak")
    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mFragment.showResults(mList);
                    break;
                case 1:
                    mFragment.onShowFail();
                    break;
            }
        }
    };

    @Override
    public void load(String tag) {
        new Thread(() -> {
            AppDatabase appDatabase = AppDatabase.getDatabase(Utils.getContext());
            WordDao wordDao = appDatabase.getWordDao();

            List<WordCollect> wordCollects = wordDao.getWordCollectsByTag(tag);
            if (wordCollects != null) {
                mList = wordCollects;
                handler.sendEmptyMessage(0);
            } else {
                handler.sendEmptyMessage(1);
            }
        }).start();
    }

    @Override
    public void addCollect(WordCollect wordCollect) {
        new Thread(() -> {
            AppDatabase appDatabase = AppDatabase.getDatabase(Utils.getContext());
            WordDao wordDao = appDatabase.getWordDao();
            try {
                wordDao.addWordCollect(wordCollect);
            } catch (Exception e) {

            }
        }).start();
    }

    @Override
    public void removeCollect(WordCollect wordCollect) {
        new Thread(() -> {
            AppDatabase appDatabase = AppDatabase.getDatabase(Utils.getContext());
            WordDao wordDao = appDatabase.getWordDao();
            try {
                wordDao.removeWordCollect(wordCollect);
            } catch (Exception e) {

            }
        }).start();
    }
}
