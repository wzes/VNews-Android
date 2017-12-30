package com.mobile.vnews.activity.word.detail;

import android.annotation.SuppressLint;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.mobile.vnews.activity.word.WordContract;
import com.mobile.vnews.activity.word.WordFragment;
import com.mobile.vnews.module.bean.Word;
import com.mobile.vnews.module.bean.WordCollect;
import com.mobile.vnews.module.dao.WordDao;
import com.mobile.vnews.module.database.AppDatabase;
import com.mobile.vnews.module.parse.WordParser;
import com.mobile.vnews.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuantang on 11/27/17.
 */

public class WordDetailPresenter implements WordDetailContract.Presenter {
    // The tag for log
    private static final String TAG = WordDetailPresenter.class.getSimpleName();

    @NonNull
    private WordDetailFragment mFragment;

    public WordDetailPresenter(@NonNull WordDetailFragment mFragment) {
        this.mFragment = mFragment;
        this.mFragment.setPresenter(this);
    }

    @Override
    public void start() {

    }

    private Word mWord;

    @SuppressLint("HandlerLeak")
    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mFragment.showResult(mWord);
                    break;
                case 1:
                    break;
            }
        }
    };

    @Override
    public void load(String word) {
        new Thread(() -> {
            AppDatabase appDatabase = AppDatabase.getDatabase(Utils.getContext());
            WordDao wordDao = appDatabase.getWordDao();

            List<Word> words = wordDao.getWordsByName(word);

            if (words.size() == 0) {
                words = wordDao.getWordsByName(word.toLowerCase());
            }
            //Log.i(TAG, "search result: " + words.size() + " " + word);
            if (words.size() != 0) {
                try {
//                    Log.i(TAG, "search: ---------------"  + word + " " + words.size() + "\n"
//                            + JSON.toJSONString(words.get(0)) + "\n" +
//                    words.get(0).getVoice());
                    WordParser wordParser = new WordParser();
                    mWord = wordParser.parse(words);
                    //Log.i(TAG, "search: " + JSON.toJSONString(mWord));
                } catch (Exception e) {
                    handler.sendEmptyMessage(1);
                    return;
                }
                if (mWord != null) {
                    handler.sendEmptyMessage(0);
                } else {
                    handler.sendEmptyMessage(1);
                }
            } else {
                mFragment.onSearchFail();
            }

        }).start();
    }

    @Override
    public void addCollect(WordCollect wordCollect) {
        new Thread(() -> {
            AppDatabase appDatabase = AppDatabase.getDatabase(Utils.getContext());
            WordDao wordDao = appDatabase.getWordDao();
            wordDao.removeWordCollect(wordCollect);
            try {
                wordDao.addWordCollect(wordCollect);
            } catch (Exception e) {

            }
        }).start();
    }
}
