package com.mobile.vnews.activity.message;


import android.support.annotation.NonNull;

import com.mobile.vnews.activity.base.BasePresenter;
import com.mobile.vnews.activity.base.BaseView;
import com.mobile.vnews.module.bean.Message;

import java.util.List;

public interface MessageContract {

    interface View extends BaseView<Presenter> {
        void showResults(@NonNull List<Message> list);
        void onShowFail();
    }

    interface Presenter extends BasePresenter {
        void load(String user_id);
    }

}
