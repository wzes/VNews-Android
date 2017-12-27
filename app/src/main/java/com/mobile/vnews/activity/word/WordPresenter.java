package com.mobile.vnews.activity.word;

import android.annotation.SuppressLint;
import android.os.Message;
import android.support.annotation.NonNull;
import android.widget.HeaderViewListAdapter;

import com.mobile.vnews.module.bean.Word;
import com.mobile.vnews.module.dao.WordDao;
import com.mobile.vnews.module.database.AppDatabase;
import com.mobile.vnews.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

/**
 * Created by xuantang on 11/27/17.
 */

public class WordPresenter implements WordContract.Presenter {
    // The tag for log
    private static final String TAG = WordPresenter.class.getSimpleName();

    @NonNull
    private WordFragment view;

    public WordPresenter(@NonNull WordFragment view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    private List<String> mBooks;
    @Override
    public void load(String user_id) {
        new Thread(() -> {
            AppDatabase appDatabase = AppDatabase.getDatabase(Utils.getContext());
            WordDao wordDao = appDatabase.getWordDao();
            try {
                mBooks = wordDao.getWordCollectType();
                handler.sendEmptyMessage(0);
            } catch (Exception e) {
                handler.sendEmptyMessage(1);
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    view.showBooks(mBooks);
                    break;
                case 1:
                    break;
            }
        }
    };
}
