package com.mobile.vnews.activity.launch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;

import com.mobile.vnews.R;
import com.mobile.vnews.activity.BaseActivity;
import com.mobile.vnews.activity.intro.ActivityIntro;
import com.mobile.vnews.activity.main.MainActivity;
import com.mobile.vnews.application.AppPreferences;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // not first
        if (!AppPreferences.getLaunchInfo()) {
            AppPreferences.saveLaunchInfo(true);
            startActivity(new Intent(this, ActivityIntro.class));
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
    }
}
