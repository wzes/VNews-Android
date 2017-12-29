package com.mobile.vnews.activity.me.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.mobile.vnews.R;
import com.mobile.vnews.activity.BaseActivity;
import com.mobile.vnews.activity.login.LoginActivity;
import com.mobile.vnews.application.AppPreferences;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.fragment_news_me_toolbar)
    Toolbar mFragmentNewsMeToolbar;
    @BindView(R.id.fragment_news_me_app_bar)
    AppBarLayout mFragmentNewsMeAppBar;
    @BindView(R.id.setting_about_layout)
    LinearLayout mSettingAboutLayout;
    @BindView(R.id.setting_version_layout)
    LinearLayout mSettingVersionLayout;
    @BindView(R.id.setting_clear_layout)
    LinearLayout mSettingClearLayout;
    @BindView(R.id.setting_login_out_layout)
    LinearLayout mSettingLoginOutLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @OnClick({R.id.setting_about_layout, R.id.setting_version_layout, R.id.setting_clear_layout, R.id.setting_login_out_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.setting_about_layout:
                break;
            case R.id.setting_version_layout:
                break;
            case R.id.setting_clear_layout:
                break;
            case R.id.setting_login_out_layout:
                startActivity(new Intent(this, LoginActivity.class));
                clearLoginInfo();
                break;
        }
    }

    /**
     * Clear login info
     */
    private void clearLoginInfo() {
        AppPreferences.saveLoginState(false);
        AppPreferences.saveLastUserImage(AppPreferences.getLoginUserImage());
        finish();
    }
}
