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

    @Override
    public void load(String user_id) {
        List<String> books = new ArrayList<>();
        books.add("收藏");
        books.add("生词");
        books.add("熟记");
        view.showBooks(books);
    }
}
