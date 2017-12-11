package com.mobile.vnews.application;

import android.app.Application;
import android.content.res.AssetManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.mobile.vnews.module.bean.Word;
import com.mobile.vnews.module.dao.WordDao;
import com.mobile.vnews.module.database.AppDatabase;
import com.mobile.vnews.util.FileUtils;
import com.mobile.vnews.util.Utils;
import com.squareup.leakcanary.LeakCanary;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;


public class MyApplication extends Application {

    // The tag for log
    private static final String TAG = MyApplication.class.getSimpleName();

    public static final String currentVersion = "1";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate() {
        super.onCreate();
        // init it in the function of onCreate in ur Application
        Utils.init(this);

        // AppCache.setContext(this);

        AppPreferences.saveVersion("1");

        String version = AppPreferences.getVersion();

        test();

        // update db or initialize db
        if (version.compareTo(currentVersion) < 0) {
            initDatabase();
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

    /**
     *
     */
    private void test() {
        new Thread(() -> {
            AppDatabase appDatabase = AppDatabase.getDatabase(getApplicationContext());
            WordDao wordDao = appDatabase.getWordDao();
            List<Word> words = wordDao.getWordsByName("take");
            System.out.println(words.size());
            for (Word word : words) {
                Log.i(TAG, "onCreate: " + words.size() + "------------" + word.getMeans());
            }
        }).start();
    }

    /**
     * Initialize database
     */
    private void initDatabase() {
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

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


}
