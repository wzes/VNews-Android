package com.mobile.vnews.activity.me.info;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mobile.vnews.R;

public class InfoSettingActivity extends AppCompatActivity {

    private InfoSettingFragment mInfoSettingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_setting);
        if (savedInstanceState != null) {
            mInfoSettingFragment = (InfoSettingFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, InfoSettingFragment.class.getSimpleName());
        } else {
            mInfoSettingFragment = InfoSettingFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.info_setting_container, mInfoSettingFragment, InfoSettingFragment.class.getSimpleName())
                    .commit();
        }

        new InfoSettingPresenter(mInfoSettingFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mInfoSettingFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState,
                    InfoSettingFragment.class.getSimpleName(), mInfoSettingFragment);
        }
    }
}
