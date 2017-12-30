package com.mobile.vnews.activity.message;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mobile.vnews.application.AppPreferences;
import com.mobile.vnews.module.BasicResponse;
import com.mobile.vnews.module.bean.Message;
import com.mobile.vnews.module.dao.MessageDao;
import com.mobile.vnews.module.dao.WordDao;
import com.mobile.vnews.module.database.AppDatabase;
import com.mobile.vnews.service.Api;
import com.mobile.vnews.service.DefaultObserver;
import com.mobile.vnews.util.Utils;

import java.util.ArrayList;
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

    private List<Message> messages;

    public MessagePresenter(@NonNull MessageFragment view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @SuppressLint("HandlerLeak")
    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    view.showResults(messages);
                    break;
                case 1:
                    view.showResults(messages);
                    break;
                case 2:
                    view.onShowFail();
                    break;
            }
        }
    };


    @Override
    public void load(String user_id) {
        Api.getApiService().getMessages(user_id, AppPreferences.getLastGetMsgTimestamp())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<Message>>>(view.getActivity()) {
                    @Override
                    public void onSuccess(BasicResponse<List<Message>> response) {
                        if (messages == null) {
                            messages = new ArrayList<>();
                        }
                        messages.addAll(response.getContent());
                        // save
                        AppPreferences.saveLastGetMsgTimestamp(System.currentTimeMillis());
                        // get msg from database
                        getMsg();
                    }
                    @Override
                    public void onFail(BasicResponse<List<Message>> response) {
                        handler.sendEmptyMessage(2);
                    }
                });
    }

    public void getMsg() {
        try {
            new Thread(() -> {
                AppDatabase appDatabase = AppDatabase.getDatabase(Utils.getContext());
                MessageDao messageDao = appDatabase.getMessageDao();
                // add local database
                for (Message message : messages) {
                    messageDao.addMessage(message);
                }
                messages = messageDao.getMessage();
                handler.sendEmptyMessage(0);
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
            handler.sendEmptyMessage(1);
        }

    }
}
