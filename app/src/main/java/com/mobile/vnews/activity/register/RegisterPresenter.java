package com.mobile.vnews.activity.register;

/**
 * Created by xuantang on 12/13/17.
 */

public class RegisterPresenter implements RegisterContract.Presenter {

    RegisterActivity activity;
    RegisterFragment fragment;

    public RegisterPresenter(RegisterActivity activity, RegisterFragment fragment) {
        this.activity = activity;
        this.fragment = fragment;
    }


    @Override
    public void start() {

    }

    @Override
    public void register(String telephone, String username, String password) {

    }
}
