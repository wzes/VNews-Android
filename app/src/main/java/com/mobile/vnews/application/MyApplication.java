package com.mobile.vnews.application;

import android.app.Application;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.mobile.vnews.util.Utils;
import com.squareup.leakcanary.LeakCanary;


public class MyApplication extends Application {

    // The tag for log
    private static final String TAG = MyApplication.class.getSimpleName();

    public static final String curentVertion = "0";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate() {
        super.onCreate();
        // init it in the function of onCreate in ur Application
        Utils.init(this);

        // AppCache.setContext(this);

        // AppPreferences.saveVersion("0");

        String version = AppPreferences.getVersion();
        // update db or initialize db
        if (version.compareTo(curentVertion) < 0) {

        }



        // For MyCrashHandler
        MyCrashHandler.getInstance(this);

        // For LeakCanary
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

}
