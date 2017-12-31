package com.mobile.vnews.activity.launch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.mobile.vnews.R;
import com.mobile.vnews.activity.BaseActivity;
import com.mobile.vnews.activity.main.MainActivity;

public class LaunchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }
}
