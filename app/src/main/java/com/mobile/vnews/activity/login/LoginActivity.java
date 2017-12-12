package com.mobile.vnews.activity.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mobile.vnews.R;

public class LoginActivity extends AppCompatActivity {

    LoginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (savedInstanceState != null) {
            loginFragment = (LoginFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, LoginFragment.class.getSimpleName());
        } else {
            loginFragment = LoginFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, loginFragment, LoginFragment.class.getSimpleName())
                    .commit();
        }

        new LoginPresenter(loginFragment);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (loginFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState,
                    LoginFragment.class.getSimpleName(), loginFragment);
        }
    }
}
