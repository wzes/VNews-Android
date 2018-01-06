package com.mobile.vnews.activity.launch;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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
        // initializeDatabase
        initializeDatabase();
        // not first
        if (!AppPreferences.getLaunchInfo()) {
            AppPreferences.saveLaunchInfo(true);
            startActivity(new Intent(this, ActivityIntro.class));
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
    }

    /**
     * Initialize database
     */
    private void initializeDatabase() {
        // update db or initialize db
        if (AppPreferences.getVersion().compareTo(MyApplication.currentVersion) < 0) {
            new Thread(() -> {
                AssetManager assetManager = getAssets();
                int totalSize;
                BlockingQueue<Integer> writeQueue = null;
                BlockingQueue<Integer> readQueue = null;
                try {
                    totalSize = FileUtils.getTotalSize(assetManager.open("initSize.dat"));
                    writeQueue = FileUtils.getQueueFromFile(assetManager.open("initSize.dat"), "write");
                    readQueue = FileUtils.getQueueFromFile(assetManager.open("sliceSize.dat"), "read");

                    // write to data
                    FileUtils.writeToFileBySlice(getApplicationContext(), "word", "/data/data/" + getPackageName() + "/databases/word.db",
                            totalSize, readQueue, writeQueue);
                    // update version
                    AppPreferences.saveVersion(MyApplication.currentVersion);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
