package com.mobile.vnews.activity.register;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mobile.vnews.R;
import com.mobile.vnews.activity.login.LoginFragment;
import com.mobile.vnews.activity.login.LoginPresenter;

public class RegisterActivity extends AppCompatActivity {

    PhoneFragment phoneFragment;
    RegisterFragment registerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (savedInstanceState != null) {
            phoneFragment = (PhoneFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, PhoneFragment.class.getSimpleName());
            registerFragment = (RegisterFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, RegisterFragment.class.getCanonicalName());
        } else {
            phoneFragment = PhoneFragment.newInstance();
            registerFragment = RegisterFragment.newInstance();
        }

        changeTab(0);

        new PhonePresenter(this, phoneFragment);
    }

    /**
     *
     * @param tab
     */
    public void changeTab(int tab) {
        if (tab == 0) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, phoneFragment, PhoneFragment.class.getSimpleName())
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, registerFragment, PhoneFragment.class.getSimpleName())
                    .commit();
        }
    }
}
