package com.mobile.vnews.activity.register;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mobile.vnews.R;
import com.mobile.vnews.activity.login.LoginFragment;
import com.mobile.vnews.activity.login.LoginPresenter;

public class RegisterActivity extends AppCompatActivity {

    PhoneFragment phoneFragment;
    RegisterFragment registerFragment;

    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

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
        new PhonePresenter(this, phoneFragment);
        new RegisterPresenter(this, registerFragment);
        changeTab(0);
    }

    /**
     *
     * @param tab
     */
    public void changeTab(int tab) {
        if (tab == 0) {
            getSupportFragmentManager().beginTransaction()
                    .remove(registerFragment)
                    .commit();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.register_container, phoneFragment, PhoneFragment.class.getSimpleName())
                    .commit();
        } else {

            getSupportFragmentManager().beginTransaction()
                    .remove(phoneFragment)
                    .commit();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.register_container, registerFragment, RegisterFragment.class.getSimpleName())
                    .commit();
        }
    }
}
