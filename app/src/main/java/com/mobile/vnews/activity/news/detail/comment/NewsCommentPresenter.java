package com.mobile.vnews.activity.news.detail.comment;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.mobile.vnews.module.BasicResponse;
import com.mobile.vnews.module.bean.Comment;
import com.mobile.vnews.module.bean.Message;
import com.mobile.vnews.module.bean.Word;
import com.mobile.vnews.service.Api;
import com.mobile.vnews.service.DefaultObserver;
import com.mobile.vnews.service.client.MessageService;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by xuantang on 12/21/17.
 */

public class NewsCommentPresenter implements NewsCommentContract.Presenter {

    private static final String TAG = NewsCommentPresenter.class.getSimpleName();

    private NewsCommentFragment mFragment;

    private Word mWord;

    public NewsCommentPresenter(NewsCommentFragment mFragment) {
        this.mFragment = mFragment;
        mFragment.setPresenter(this);
    }


    @Override
    public void start() {

    }

    @Override
    public void load(int newsID, int floor) {
        // Get comments
        Api.getApiService().getNewsCommentsByFloor(String.valueOf(newsID), String.valueOf(floor))
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


    @Override
    public void like(String userID, int newID) {
        // json
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", userID);
        map.put("news_id", String.valueOf(newID));
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSON.toJSONString(map));

        Api.getApiService().likeNews(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<String>>(mFragment.getActivity()) {
                    @Override
                    public void onSuccess(BasicResponse<String> response) {
                        // TODO
                        Log.i(TAG, "onSuccess: ");
                    }
                });
    }

    @Override
    public void comment(Message message) {
        try {
            MessageService.getMessageClient().sendMessage(message);
            mFragment.onCommentSuccess(message);
        } catch (Exception e) {
            mFragment.onCommentFail();
        }
    }
}
