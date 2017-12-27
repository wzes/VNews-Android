package com.mobile.vnews.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mobile.vnews.R;
import com.mobile.vnews.activity.main.MainActivity;
import com.mobile.vnews.application.AppPreferences;
import com.mobile.vnews.module.bean.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class LoginFragment extends Fragment implements LoginContract.View {

    LoginPresenter loginPresenter;

    @BindView(R.id.login_register)
    Button loginRegister;
    @BindView(R.id.login_toolbar)
    Toolbar loginToolbar;
    @BindView(R.id.login_image)
    CircleImageView loginImage;
    @BindView(R.id.login_username)
    EditText loginUsername;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.login_sign)
    Button loginSign;
    Unbinder unbinder;

    public LoginFragment() {

    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        loginPresenter.loadLastLoginUser();
    }

    @Override
    public void setPresenter(LoginContract.Presenter mPresenter) {
        this.loginPresenter = (LoginPresenter) mPresenter;
    }

    @Override
    public void showUserImage(String image) {
        Glide.with(this)
                .load(image)
                .into(loginImage);
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void onSuccess(User user) {
        // save something
        AppPreferences.saveLoginUserID(user.getId());
        AppPreferences.saveLoginUsername(user.getUsername());
        AppPreferences.saveLoginUserImage(user.getImage());
        AppPreferences.saveLoginUserMotto(user.getMotto());
        AppPreferences.saveLoginState(true);
        // start activity
        startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish();
    }

    @Override
    public void onFail() {
        Toast.makeText(getActivity(), "登录失败！密码", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void register() {
        // start activity
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.login_register, R.id.login_sign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_register:

                break;
            case R.id.login_sign:
                if (TextUtils.isEmpty(loginUsername.getText())) {
                    Toast.makeText(getContext(), "请输入用户名！", Toast.LENGTH_SHORT).show();
                    loginUsername.setFocusable(true);
                    return;
                }

                if (TextUtils.isEmpty(loginUsername.getText())) {
                    Toast.makeText(getContext(), "请输入密码！", Toast.LENGTH_SHORT).show();
                    loginUsername.setFocusable(true);
                    return;
                }
                loginPresenter.login(loginUsername.getText().toString(),
                        loginPassword.getText().toString());
                break;
        }
    }
}
