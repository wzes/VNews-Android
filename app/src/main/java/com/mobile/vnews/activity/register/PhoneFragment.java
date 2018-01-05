package com.mobile.vnews.activity.register;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobile.vnews.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by xuantang on 12/13/17.
 */

public class PhoneFragment extends Fragment implements PhoneContract.View {

    PhoneContract.Presenter mPresenter;
    @BindView(R.id.register_telephone_toolbar)
    Toolbar mRegisterTelephoneToolbar;
    @BindView(R.id.register_phone)
    EditText mRegisterPhone;
    @BindView(R.id.register_phone_code)
    EditText mRegisterPhoneCode;
    @BindView(R.id.register_next)
    Button mRegisterNext;

    Unbinder unbinder;
    @BindView(R.id.register_code_get)
    Button mRegisterCodeGet;

    private String code;

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
        View view = inflater.inflate(R.layout.fragment_register_phone, container, false);
        unbinder = ButterKnife.bind(this, view);
        RegisterActivity activity = (RegisterActivity) getActivity();
        activity.setSupportActionBar(mRegisterTelephoneToolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            getActivity().onBackPressed();
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setPresenter(PhoneContract.Presenter mPresenter) {
        this.mPresenter = checkNotNull(mPresenter);
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void onSuccess(String code) {
        this.code = code;
    }

    @Override
    public void onFail(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (mTimer != null) {
            mTimer.cancel();
        }
    }
    private Timer mTimer;

    private int time = 60;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (time > 0) {
                time--;
                mRegisterCodeGet.setText("获取验证码(" + time + "s)");
            } else {
                mRegisterCodeGet.setClickable(true);
            }
        }
    };
    private void setTimerTask() {
        time = 60;
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        }, 0, 1000);
    }
    @OnClick({R.id.register_code_get, R.id.register_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register_code_get:
                if (!TextUtils.isEmpty(mRegisterPhone.getText())) {
                    mPresenter.send(mRegisterPhone.getText().toString());
                    setTimerTask();
                    mRegisterCodeGet.setClickable(false);
                } else {
                    Toast.makeText(getActivity(), "请输入号码", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.register_next:
                if (!TextUtils.isEmpty(mRegisterPhoneCode.getText())) {
                    if (TextUtils.isEmpty(code)) {
                        Toast.makeText(getActivity(), "签证码错误", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (mRegisterPhoneCode.getText().toString().equals(code)) {
                        mPresenter.onNext();
                    }
                } else {
                    Toast.makeText(getActivity(), "签证码错误", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
