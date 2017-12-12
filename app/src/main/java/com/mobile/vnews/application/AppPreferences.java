package com.mobile.vnews.application;

import android.content.SharedPreferences;

import com.mobile.vnews.util.Utils;

public class AppPreferences {

    private static final String KEY_APP_VERSION = "app_version";
    private static final String KEY_APP_LAUNCH_FIRST = "app_launch_first";

    private static final String KEY_APP_LAST_LOGIN_USER_IMAGE = "app_last_login_user_image";

    public static void saveLastUserImage(String lastUserImage) {
        saveString(KEY_APP_LAST_LOGIN_USER_IMAGE, lastUserImage);
    }

    public static String getLastUserImage() {
        return getString(KEY_APP_LAST_LOGIN_USER_IMAGE);
    }

    public static void saveVersion(String version) {
        saveString(KEY_APP_VERSION, version);
    }

    public static String getVersion() {
        return getString(KEY_APP_VERSION);
    }

    public static void saveLaunchInfo(boolean value) {
        saveBoolean(KEY_APP_LAUNCH_FIRST, value);
    }

    public static boolean getLaunchInfo() {
        return getBoolean(KEY_APP_LAUNCH_FIRST);
    }

    private static void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
    private static boolean getBoolean(String key) {
        return getSharedPreferences().getBoolean(key, true);
    }
    private static void saveString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.apply();
    }

    private static String getString(String key) {
        return getSharedPreferences().getString(key, "0");
    }

    private static SharedPreferences getSharedPreferences() {
        return Utils.getContext().getSharedPreferences("App", 0);
    }

}
