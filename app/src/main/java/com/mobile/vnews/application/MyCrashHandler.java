package com.mobile.vnews.application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyCrashHandler implements Thread.UncaughtExceptionHandler {

    private static MyCrashHandler instance;

    private Application application;
    private Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;

    private static final String TAG = "MyCrashHandler";

    private MyCrashHandler(Context context) {
        Log.d(TAG, "Instantiate MyCrashHandler");
        application = (Application) context.getApplicationContext();
        defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * Returns the single instance of MyCrashHandler
     * @param context The current context
     * @return The single instance of MyCrashHandler
     */
    public static MyCrashHandler getInstance(Context context) {
        MyCrashHandler inst = instance;
        if (inst == null) {
            synchronized (MyCrashHandler.class) {
                inst = instance;
                if (inst == null) {
                    inst = new MyCrashHandler(context.getApplicationContext());
                    instance = inst;
                }
            }
        }

        return inst;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable exception) {
        try {
            saveLog(thread, exception);
            exception.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (defaultUncaughtExceptionHandler != null) {
                Log.d(TAG, "defaultUncaughtExceptionHandler != null");
                Log.d(TAG, "Let defaultUncaughtExceptionHandler handle the Exception.");
                defaultUncaughtExceptionHandler.uncaughtException(thread, exception);
                restartApplication();
            } else {
                exception.printStackTrace();
            }
        }
    }

    private void saveLog(Thread thread, Throwable exception) throws Exception {
        FileOutputStream fileOutputStream = application.getApplicationContext()
                .openFileOutput("SlidingInputMethod.log", Context.MODE_APPEND);

        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append("=======================================================\n");

        long currentTimeMillis = System.currentTimeMillis();
        String timeString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date(currentTimeMillis));
        stringBuffer.append("System.currentTimeMillis() = ");
        stringBuffer.append(String.valueOf(currentTimeMillis));
        stringBuffer.append("\n");
        stringBuffer.append(timeString);
        stringBuffer.append("\n");

        stringBuffer.append("thread.getName() = \"");
        stringBuffer.append(thread.getName());
        stringBuffer.append("\"\n");

        stringBuffer.append("exception.getMessage() = \"");
        stringBuffer.append(exception.getMessage());
        stringBuffer.append("\"\n");

        stringBuffer.append("----------------------------------\n");

        StringWriter stringWriter = new StringWriter();
        exception.printStackTrace(new PrintWriter(stringWriter));
        stringBuffer.append(stringWriter.toString());

        stringBuffer.append("\n\n");

        fileOutputStream.write(stringBuffer.toString().getBytes());
        fileOutputStream.close();
    }

    /**
     * Restarts this application
     */
    private void restartApplication() {
        Context context = application.getApplicationContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

}
