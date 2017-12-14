package com.mobile.vnews.activity.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.vnews.activity.login.LoginFragment;

/**
 * Created by xuantang on 12/13/17.
 */

public class PhoneFragment extends Fragment implements PhoneContract.View {

    PhonePresenter phonePresenter;

    public PhoneFragment() {

    }

    public static PhoneFragment newInstance() {
        PhoneFragment fragment = new PhoneFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setPresenter(PhoneContract.Presenter presenter) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFail() {

    }

    @Override
    public void onNext(String telephone) {

    }

    @Override
    public void onLoading() {

    }
}
