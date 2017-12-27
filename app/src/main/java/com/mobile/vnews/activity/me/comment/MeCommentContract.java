package com.mobile.vnews.activity.me.comment;

import android.support.annotation.NonNull;

import com.mobile.vnews.activity.base.BasePresenter;
import com.mobile.vnews.activity.base.BaseView;
import com.mobile.vnews.module.bean.Comment;
import com.mobile.vnews.module.bean.Message;

import java.util.List;

/**
 * Created by xuantang on 12/21/17.
 */

public class MeCommentContract {
    interface View extends BaseView<Presenter> {
        void showComments(@NonNull List<Comment> comments);
        void onCommentFail();
    }

    interface Presenter extends BasePresenter {
        void load(String userID);
    }
}
