package com.mobile.vnews.activity.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.mobile.vnews.activity.login.LoginActivity;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by xuantang on 12/13/17.
 */

public class RegisterFragment extends Fragment implements RegisterContract.View {

    RegisterContract.Presenter mPresenter;

    @BindView(R.id.register_toolbar)
    Toolbar mRegisterToolbar;
    @BindView(R.id.register_username)
    EditText mRegisterUsername;
    @BindView(R.id.register_password)
    EditText mRegisterPassword;
    @BindView(R.id.register_password_again)
    EditText mRegisterPasswordAgain;
    @BindView(R.id.register_done)
    Button mRegisterDone;
    Unbinder unbinder;

    public RegisterFragment() {

    }

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        unbinder = ButterKnife.bind(this, view);
        RegisterActivity activity = (RegisterActivity) getActivity();
        activity.setSupportActionBar(mRegisterToolbar);
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
    public void setPresenter(RegisterContract.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void onSuccess() {
        Toast.makeText(getContext(), "注册成功", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

    @Override
    public void onFail() {

    }

    @Override
    public void onLoading() {
        Toast.makeText(getContext(), "正在注册～", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.register_done)
    public void onViewClicked() {
        String username = "";
        String password = "";
        if (!TextUtils.isEmpty(mRegisterUsername.getText())) {
            username = mRegisterUsername.getText().toString();
        } else {
            Toast.makeText(getContext(), "用户名不能为空", Toast.LENGTH_SHORT).show();
            mRegisterUsername.requestFocus();
        }
        if (!TextUtils.isEmpty(mRegisterPassword.getText())) {
            password = mRegisterPassword.getText().toString();
        } else {
            Toast.makeText(getContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
            mRegisterPassword.requestFocus();
        }
        if (!TextUtils.isEmpty(mRegisterPassword.getText())) {
            if (!TextUtils.isEmpty(mRegisterPasswordAgain.getText())) {
                if (password.equals(mRegisterPasswordAgain.getText().toString())){
                    mPresenter.register(username, password);
                } else {
                    Toast.makeText(getContext(), "两次密码不唯一", Toast.LENGTH_SHORT).show();
                    mRegisterPassword.setText("");
                    mRegisterPasswordAgain.setText("");
                    mRegisterPassword.requestFocus();
                }
            } else {
                Toast.makeText(getContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
                mRegisterPasswordAgain.requestFocus();
            }
        } else {
            Toast.makeText(getContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
            mRegisterPassword.requestFocus();
        }
    }
}
