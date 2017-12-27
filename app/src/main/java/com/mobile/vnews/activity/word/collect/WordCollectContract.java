package com.mobile.vnews.activity.word.collect;


import com.mobile.vnews.activity.base.BasePresenter;
import com.mobile.vnews.activity.base.BaseView;
import com.mobile.vnews.module.bean.Word;
import com.mobile.vnews.module.bean.WordCollect;

import java.util.List;

public interface WordCollectContract {

    interface View extends BaseView<Presenter> {
        void showResults(List<WordCollect> list);
        void onShowFail();
    }

    interface Presenter extends BasePresenter {
        void load(String user_id);
        void addCollect(WordCollect wordCollect);
    }

}
