package com.mobile.vnews.activity.word;


import com.mobile.vnews.activity.base.BasePresenter;
import com.mobile.vnews.activity.base.BaseView;

import java.util.List;

public interface WordContract {

    interface View extends BaseView<Presenter> {
        void showBooks(List<String> books);
    }

    interface Presenter extends BasePresenter {
        void load(String user_id);
    }

}
