package com.mobile.vnews.activity.message;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mobile.vnews.module.BasicResponse;
import com.mobile.vnews.module.bean.Message;
import com.mobile.vnews.service.Api;
import com.mobile.vnews.service.DefaultObserver;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xuantang on 11/27/17.
 */

public class MessagePresenter implements MessageContract.Presenter {
    // The tag for log
    private static final String TAG = MessagePresenter.class.getSimpleName();

    @NonNull
    private MessageFragment view;

    public MessagePresenter(@NonNull MessageFragment view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void start() {

    }


    @Override
    public void load(String user_id) {
        Api.getApiService().getMessages(user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<Message>>>(view.getActivity()) {
                    @Override
                    public void onSuccess(BasicResponse<List<Message>> response) {
                        Log.i(TAG, "onSuccess: " + response.getContent().size());
                        view.showResults(response.getContent());
                    }

                    @Override
                    public void onFail(BasicResponse<List<Message>> response) {
                        view.onShowFail();
                    }
                });
    }
}
