package com.mobile.vnews.activity.word.recite;

import android.support.annotation.NonNull;

import com.mobile.vnews.activity.base.BasePresenter;
import com.mobile.vnews.activity.base.BaseView;
import com.mobile.vnews.module.bean.Word;
import com.mobile.vnews.module.bean.WordCollect;

import org.w3c.dom.ls.LSInput;

import java.util.List;

/**
 * Created by xuantang on 12/25/17.
 */

public class WordReciteContract {
    interface View extends BaseView<Presenter> {
        void showResults(@NonNull List<WordCollect> list);
        void changeWord(int position);
        void onShowFail();
    }

    interface Presenter extends BasePresenter {
        void load(String tag);
        void addCollect(WordCollect wordCollect);
        void removeCollect(WordCollect wordCollect);
    }
}
