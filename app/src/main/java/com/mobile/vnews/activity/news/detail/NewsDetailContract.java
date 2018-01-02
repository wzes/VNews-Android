package com.mobile.vnews.activity.news.detail;

import android.support.annotation.NonNull;

import com.mobile.vnews.activity.base.BasePresenter;
import com.mobile.vnews.activity.base.BaseView;
import com.mobile.vnews.module.bean.Comment;
import com.mobile.vnews.module.bean.Message;
import com.mobile.vnews.module.bean.News;
import com.mobile.vnews.module.bean.Word;
import com.mobile.vnews.module.bean.WordCollect;

import java.util.List;

/**
 * Created by xuantang on 12/21/17.
 */

public class NewsDetailContract {
    interface View extends BaseView<Presenter> {
        void showResults(@NonNull News news);
        void showWord(@NonNull Word word);
        void onShowFail();
        void onSearchFail();
        void onLikeSuccess();
        void onLikeFail();
        void showComments(@NonNull List<Comment> comments);
        void onCommentFail();
        void onCommentSuccess(Message message);
        void onLogin();
        void onReplySuccess(Message message);
    }

    interface Presenter extends BasePresenter {
        void load(int newsID);
        void search(String word);
        void like(String userID, int newID);
        void likeComment(String userID, int comment_id);
        void dislikeComment(String userID, int comment_id);
        void dislikeNews(String userID, int news_id);
        void comment(Message message);
        void reply(Message message);
        void view(String userID, int newID);
        void addWordCollect(WordCollect wordCollect);
    }
}
