package com.mobile.vnews.service.client;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.mobile.vnews.application.AppPreferences;


@SuppressLint("Registered")
public class MessageService extends Service {

    private static MessageClient messageClient;
    private static final String HOST = "10.0.1.52";
    private static final int PORT = 10001;

    /**
     *
     * @return messageClient
     */
    public static MessageClient getMessageClient() {
        return messageClient;
    }

    public void onCreate() {
        try {
            // if login
            if (AppPreferences.getLoginState()) {
                messageClient = new MessageClient(HOST, PORT, AppPreferences.getLoginUserID());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        if (messageClient != null) {
            messageClient.stopConn();
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
