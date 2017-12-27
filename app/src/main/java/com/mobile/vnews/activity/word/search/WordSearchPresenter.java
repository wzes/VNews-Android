package com.mobile.vnews.activity.word.search;

import android.annotation.SuppressLint;
import android.os.Message;
import android.support.annotation.NonNull;

import com.mobile.vnews.activity.word.WordContract;
import com.mobile.vnews.activity.word.WordFragment;
import com.mobile.vnews.module.dao.WordDao;
import com.mobile.vnews.module.database.AppDatabase;
import com.mobile.vnews.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuantang on 11/27/17.
 */

public class WordSearchPresenter implements WordSearchContract.Presenter {
    // The tag for log
    private static final String TAG = WordSearchPresenter.class.getSimpleName();

    @NonNull
    private WordSearchFragment view;

    public WordSearchPresenter(@NonNull WordSearchFragment view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    private List<String> list;
    @SuppressLint("HandlerLeak")
    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    view.showResult(list);
                    break;
                case 1:
                    break;
            }
        }
    };

    @Override
    public void search(String word) {
        new Thread(() -> {
            try {
                AppDatabase appDatabase = AppDatabase.getDatabase(Utils.getContext());
                WordDao wordDao = appDatabase.getWordDao();
                List<String> words = new ArrayList<>();
                //words.addAll(wordDao.getWordsByName(word));
                words.addAll(wordDao.getSimpleWordsByLikeName(word + "%", 0, 20));
                list = words;
                handler.sendEmptyMessage(0);
            } catch (Exception e) {
                handler.sendEmptyMessage(1);
            }
        }).start();
    }
}
