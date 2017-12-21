package com.mobile.vnews.activity.news.detail;

import android.support.annotation.NonNull;

import com.mobile.vnews.activity.base.BasePresenter;
import com.mobile.vnews.activity.base.BaseView;
import com.mobile.vnews.module.bean.Message;
import com.mobile.vnews.module.bean.News;

/**
 * Created by xuantang on 12/21/17.
 */

public class NewsDetailContract {
    interface View extends BaseView<Presenter> {
        void showResults(@NonNull News news);
        void showWord(@NonNull String word);
        void onShowFail();
        void onSearchFail();
        void onLikeSuccess();
        void onLikeFail();
        void onCommentFail();
        void onCommentSuccess(Message message);
        void onLogin();
    }

    interface Presenter extends BasePresenter {
        void load(int newsID);
        void search(String word);
        void like(String userID, int newID);
        void comment(Message message);
        void view(String userID, int newID);
    }
}
