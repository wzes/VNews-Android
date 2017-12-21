package com.mobile.vnews.activity.register;

/**
 * Created by xuantang on 12/13/17.
 */

public class PhonePresenter implements PhoneContract.Presenter {

    RegisterActivity activity;
    PhoneFragment phoneFragment;

    public PhonePresenter(RegisterActivity activity, PhoneFragment phoneFragment) {
        this.activity = activity;
        this.phoneFragment = phoneFragment;
    }


    @Override
    public void start() {

    }

    @Override
    public void send(String telephone) {

    }

    @Override
    public void valid(String telephone, String code) {

    }

    @Override
    public void load() {

    }
}
