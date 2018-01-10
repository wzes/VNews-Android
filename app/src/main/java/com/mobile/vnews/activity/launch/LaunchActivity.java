package com.mobile.vnews.activity.launch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.mobile.vnews.R;
import com.mobile.vnews.activity.intro.ActivityIntro;
import com.mobile.vnews.activity.main.MainActivity;
import com.mobile.vnews.application.AppPreferences;
import com.mobile.vnews.application.MyApplication;
import com.mobile.vnews.util.FileUtils;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        // initializeDatabase
        initialize();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // not first
            if (!AppPreferences.getLaunchInfo()) {
                AppPreferences.saveLaunchInfo(true);
                startActivity(new Intent(getApplicationContext(), ActivityIntro.class));
            } else {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
            finish();
        }
    };
    /**
     * Initialize database
     */
    private void initialize() {
        // update db or initialize db
        if (AppPreferences.getVersion().compareTo(MyApplication.currentVersion) < 0) {
            new Thread(() -> {
                AssetManager assetManager = getAssets();
                int totalSize;
                BlockingQueue<Integer> writeQueue;
                BlockingQueue<Integer> readQueue;
                try {
                    totalSize = FileUtils.getTotalSize(assetManager.open("initSize.dat"));
                    writeQueue = FileUtils.getQueueFromFile(assetManager.open("initSize.dat"), "write");
                    readQueue = FileUtils.getQueueFromFile(assetManager.open("sliceSize.dat"), "read");

                    // write to data
                    FileUtils.writeToFileBySlice(getApplicationContext(), "word", "/data/data/" + getPackageName() + "/databases/word.db",
                            totalSize, readQueue, writeQueue);
                    // update version
                    AppPreferences.saveVersion(MyApplication.currentVersion);
                    handler.sendEmptyMessage(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(1);
            }).start();
        }
    }
}
