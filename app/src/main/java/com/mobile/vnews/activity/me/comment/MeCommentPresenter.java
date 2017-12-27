package com.mobile.vnews.activity.me.comment;

import com.mobile.vnews.module.BasicResponse;
import com.mobile.vnews.module.bean.Comment;
import com.mobile.vnews.module.bean.Word;
import com.mobile.vnews.service.Api;
import com.mobile.vnews.service.DefaultObserver;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xuantang on 12/21/17.
 */

public class MeCommentPresenter implements MeCommentContract.Presenter {

    private static final String TAG = MeCommentPresenter.class.getSimpleName();

    private MeCommentFragment mFragment;

    private Word mWord;

    public MeCommentPresenter(MeCommentFragment mFragment) {
        this.mFragment = mFragment;
        mFragment.setPresenter(this);
    }


    @Override
    public void start() {

    }

    @Override
    public void load(String userID) {
        // Get comments
        Api.getApiService().getMyComments(userID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<Comment>>>(mFragment.getActivity(), true) {
                    @Override
                    public void onSuccess(BasicResponse<List<Comment>> response) {
                        mFragment.showComments(response.getContent());
                    }

                    @Override
                    public void onFail(BasicResponse<List<Comment>> response) {
                        mFragment.onCommentFail();
                    }
                });
    }

}
