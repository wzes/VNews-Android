package com.mobile.vnews.activity.news.detail.comment;

import android.support.annotation.NonNull;

import com.mobile.vnews.activity.base.BasePresenter;
import com.mobile.vnews.activity.base.BaseView;
import com.mobile.vnews.module.bean.Comment;
import com.mobile.vnews.module.bean.Message;

import java.util.List;

/**
 * Created by xuantang on 12/21/17.
 */

public class NewsCommentContract {
    interface View extends BaseView<Presenter> {
        void showComments(@NonNull List<Comment> comments);
        void onCommentFail();
        void onCommentSuccess(Message message);
        void onLogin();
        void onLikeSuccess();
        void onLikeFail();
    }

    interface Presenter extends BasePresenter {
        void load(int newsID, int floor);
        void like(String userID, int newID);
        void comment(Message message);
        void likeComment(String userID, int comment_id);
        void dislikeComment(String userID, int comment_id);
    }
}
