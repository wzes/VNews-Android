package com.mobile.vnews.activity.me.info;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.mobile.vnews.application.AppPreferences;
import com.mobile.vnews.module.BasicResponse;
import com.mobile.vnews.module.bean.User;
import com.mobile.vnews.service.Api;
import com.mobile.vnews.service.DefaultObserver;

import java.io.File;

import io.netty.handler.codec.http.HttpHeaders;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by xuantang on 11/27/17.
 */

public class InfoSettingPresenter implements InfoSettingContract.Presenter {
    // The tag for log
    private static final String TAG = InfoSettingPresenter.class.getSimpleName();

    @NonNull
    private InfoSettingFragment view;

    public InfoSettingPresenter(@NonNull InfoSettingFragment view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void load(String userID) {
        Api.getApiService().getUser(userID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<User>>(view.getActivity()) {
                    @Override
                    public void onSuccess(BasicResponse<User> response) {
//                        Log.i(TAG, "onSuccess: " + response.getContent().size());
                        view.showResults(response.getContent());
                    }

                    @Override
                    public void onFail(BasicResponse<User> response) {
                        view.onFail();
                    }
                });
    }

    @Override
    public void updatePhoto(String filename) {
        //Log.e(TAG, "updatePhoto: " + filename + " " + type);
        // Compress photo
        Luban.with(view.getActivity())
                .load(filename)
                .ignoreBy(100)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {
                        String type = filename.substring(filename.lastIndexOf(".") + 1);
                        MultipartBody.Part photo = MultipartBody.Part.createFormData("photo",
                                file.getName(), RequestBody.create(MediaType.parse("image/" + type),
                                        file));
                        Api.getApiService().uploadPhoto(AppPreferences.getLoginUserID(), photo)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new DefaultObserver<BasicResponse<String>>(view.getActivity()) {
                                    @Override
                                    public void onSuccess(BasicResponse<String> response) {
                                        view.onUploadSuccess(filename);
                                    }

                                    @Override
                                    public void onFail(BasicResponse<String> response) {
                                        view.onUploadFail();
                                    }
                                });
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onUploadFail();
                    }
                }).launch();
    }

    @Override
    public void update(User user) {
        Api.getApiService().updateUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<User>>(view.getActivity()) {
                    @Override
                    public void onSuccess(BasicResponse<User> response) {
                        view.onUpdateSuccess(response.getContent());
                    }

                    @Override
                    public void onFail(BasicResponse<User> response) {
                        view.onUploadFail();
                    }
                });
    }
}
