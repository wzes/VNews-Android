package com.mobile.vnews.activity.register;

import com.mobile.vnews.module.BasicResponse;
import com.mobile.vnews.module.bean.News;
import com.mobile.vnews.service.Api;
import com.mobile.vnews.service.DefaultObserver;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xuantang on 12/13/17.
 */

public class PhonePresenter implements PhoneContract.Presenter {

    RegisterActivity mActivity;
    PhoneFragment mFragment;

    public PhonePresenter(RegisterActivity activity, PhoneFragment phoneFragment) {
        this.mActivity = activity;
        this.mFragment = phoneFragment;
        mFragment.setPresenter(this);
    }

    public RegisterActivity getmActivity() {
        return mActivity;
    }

    @Override
    public void start() {

    }

    @Override
    public void send(String phone) {
        Api.getApiService().checkPhone(phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<String>>(mFragment.getActivity()) {
                    @Override
                    public void onSuccess(BasicResponse<String> response) {
                        if (response.getContent().equals("false")) {
                            mFragment.onFail("此号码已被注册");
                        }
                        mFragment.onSuccess(response.getContent());
                    }

                    @Override
                    public void onFail(BasicResponse<String> response) {
                        mFragment.onFail("网络不太好");
                    }
                });
    }

    @Override
    public void load() {

    }

    @Override
    public void onNext() {
        mActivity.changeTab(1);
    }

}
