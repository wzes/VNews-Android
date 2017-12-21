package com.mobile.vnews.service;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;

import com.google.gson.JsonParseException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.mobile.vnews.R;
import com.mobile.vnews.module.BasicResponse;
import com.mobile.vnews.util.CommonDialogUtils;
import com.mobile.vnews.util.LogUtils;
import com.mobile.vnews.util.SwipeRefreshLayoutUtils;
import com.mobile.vnews.util.ToastUtils;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.mobile.vnews.service.DefaultObserver.ExceptionReason.CONNECT_ERROR;
import static com.mobile.vnews.service.DefaultObserver.ExceptionReason.CONNECT_TIMEOUT;
import static com.mobile.vnews.service.DefaultObserver.ExceptionReason.PARSE_ERROR;
import static com.mobile.vnews.service.DefaultObserver.ExceptionReason.UNKNOWN_ERROR;


/**
 * Created by zhpan on 2017/4/18.
 */

public abstract class DefaultObserver<T extends BasicResponse> implements Observer<T> {

    private Activity activity;
    // Activity 是否在执行onStop()时取消订阅
    private boolean isAddInStop = false;
    private boolean isAddDialog = false;
    private boolean isAddSwipeRefreshLayout = false;

    private CommonDialogUtils dialogUtils;
    private SwipeRefreshLayoutUtils swipeRefreshLayoutUtils;

    private T response;

    /**
     *
     * @param activity
     */
    public DefaultObserver(Activity activity) {
        this.activity = activity;
        if (isAddDialog) {
            dialogUtils = new CommonDialogUtils();
            dialogUtils.showProgress(activity);
        }

    }

    /**
     *
     * @param activity
     * @param swipeRefreshLayout
     */
    public DefaultObserver(Activity activity, SwipeRefreshLayout swipeRefreshLayout) {
        this.activity = activity;
        this.isAddSwipeRefreshLayout = true;
        swipeRefreshLayoutUtils = new SwipeRefreshLayoutUtils();
        swipeRefreshLayoutUtils.showProgress(swipeRefreshLayout);
    }

    /**
     *
     * @param activity
     * @param isAddDialog
     */
    public DefaultObserver(Activity activity, boolean isAddDialog) {
        this.activity = activity;
        this.isAddDialog = isAddDialog;
        if (isAddDialog) {
            dialogUtils = new CommonDialogUtils();
            dialogUtils.showProgress(activity);
        }

    }

    /**
     *
     * @param activity
     * @param isAddDialog
     * @param isShowLoading
     */
    public DefaultObserver(Activity activity, boolean isAddDialog, boolean isShowLoading) {
        this.activity = activity;
        this.isAddDialog = isAddDialog;
        if (isAddDialog) {
            dialogUtils=new CommonDialogUtils();
            if (isShowLoading) {
                dialogUtils.showProgress(activity,"Loading...");
            }
        }
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T response) {
        this.response = response;
    }

    /**
     * Dismiss
     */
    private void dismissProgress(){
        if(dialogUtils != null){
            dialogUtils.dismissProgress();
        }
        if(isAddSwipeRefreshLayout) {
            swipeRefreshLayoutUtils.dismissProgress();
        }
    }

    @Override
    public void onError(Throwable e) {
        LogUtils.e("Retrofit", e.getMessage());
        dismissProgress();
        if (e instanceof HttpException) {                   //   HTTP错误
            onException(ExceptionReason.BAD_NETWORK);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {     //   连接错误
            onException(CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {    //  连接超时
            onException(CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {            //  解析错误
            onException(PARSE_ERROR);
        } else {
            onException(UNKNOWN_ERROR);
        }
        onFail(response);
    }

    @Override
    public void onComplete() {
        dismissProgress();
        if (response.getCode() == 200) {
            onSuccess(response);
        } else {
            onFail(response);
        }
    }

    /**
     * 请求成功
     *
     * @param response 服务器返回的数据
     */
    abstract public void onSuccess(T response);

    /**
     * 服务器返回数据，但响应码不为200
     *
     * @param response 服务器返回的数据
     */
    public void onFail(T response) {
        String message = response.getMessage();
        if (TextUtils.isEmpty(message)) {
            ToastUtils.showLong(R.string.response_return_error);
        } else {
            ToastUtils.showLong(message);
        }
    }

    /**
     * 请求异常
     *
     * @param reason
     */
    public void onException(ExceptionReason reason) {
        switch (reason) {
            case CONNECT_ERROR:
                ToastUtils.showShort(R.string.connect_error);
                break;

            case CONNECT_TIMEOUT:
                ToastUtils.showShort(R.string.connect_timeout);
                break;

            case BAD_NETWORK:
                ToastUtils.showShort(R.string.bad_network);
                break;

            case PARSE_ERROR:
                ToastUtils.showShort(R.string.parse_error);
                break;

            case UNKNOWN_ERROR:
            default:
                ToastUtils.showShort(R.string.unknown_error);
                break;
        }
    }

    /**
     * 请求网络失败原因
     */
    public enum ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR,
    }
}
