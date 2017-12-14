package com.mobile.vnews.activity.register;

/**
 * Created by xuantang on 12/13/17.
 */

public class PhonePresenter implements PhoneContract.Presenter {

    RegisterActivity activity;
    PhoneFragment telephoneFragment;

    public PhonePresenter(RegisterActivity activity, PhoneFragment telephoneFragment) {
        this.activity = activity;
        this.telephoneFragment = telephoneFragment;
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
