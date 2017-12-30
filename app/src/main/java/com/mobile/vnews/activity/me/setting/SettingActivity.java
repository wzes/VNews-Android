package com.mobile.vnews.activity.me.setting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mobile.vnews.R;
import com.mobile.vnews.activity.BaseActivity;
import com.mobile.vnews.activity.login.LoginActivity;
import com.mobile.vnews.application.AppPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SettingActivity extends AppCompatActivity {

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

    Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        unbinder = ButterKnife.bind(this);
    }

    @OnClick({R.id.setting_about_layout, R.id.setting_version_layout, R.id.setting_clear_layout, R.id.setting_login_out_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.setting_about_layout:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("https://github.com/wzes/VNews-Android");
                intent.setData(content_url);
                startActivity(intent);
                break;
            case R.id.setting_version_layout:
                Toast.makeText(this, "V1.0", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting_clear_layout:
                Toast.makeText(this, "清除完成", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting_login_out_layout:
                startActivity(new Intent(this, LoginActivity.class));
                clearLoginInfo();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
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
