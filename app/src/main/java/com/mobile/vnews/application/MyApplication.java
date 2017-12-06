package com.mobile.vnews.application;

import android.app.Application;
import android.content.res.AssetManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.mobile.vnews.util.CompressUtils;
import com.mobile.vnews.util.Utils;
import com.squareup.leakcanary.LeakCanary;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


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

        AppPreferences.saveVersion("0");

        String version = AppPreferences.getVersion();
        // update db or initialize db
        if (version.compareTo(curentVertion) < 0) {
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

    private void initDatabase() {
        new Thread(() -> {
            AssetManager assetManager = getAssets();
            try {
                InputStream inputStream = assetManager.open("word.lz4");

                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

                File file = new File("/data/data/" + getPackageName() + "/databases/word.db");

                if (!file.exists()) {
                    file.createNewFile();
                }
                byte[] content = new byte[inputStream.available()];
                Log.i(TAG, "content.length: " + inputStream.available());
                FileOutputStream fileOutputStream = new FileOutputStream(file);

                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

                // copy file to data
                byte[] buf = new byte[1024];
                int count;
                int index = 0;
                while ((count = bufferedInputStream.read(buf)) != -1) {
                    //bufferedOutputStream.write(buf, 0, count);
                    System.arraycopy(buf, 0, content, index*1024, count);
                    index++;
                }

                Log.i(TAG, "initDatabase: " + content.length);
                // uncompress
                byte[] uncompress = CompressUtils.lz4Decompress(content);
                // write
                bufferedOutputStream.write(uncompress, 0, uncompress.length);
                // close
                bufferedInputStream.close();
                bufferedOutputStream.close();
                inputStream.close();
                fileOutputStream.close();
                AppPreferences.saveVersion(curentVertion);
                Log.i(TAG, "Copy is over");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
