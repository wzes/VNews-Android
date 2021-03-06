package com.mobile.vnews.application;

import android.content.SharedPreferences;

import com.mobile.vnews.util.Utils;

public class AppPreferences {

    private static final String KEY_APP_VERSION = "app_version";
    private static final String KEY_APP_LAUNCH_FIRST = "app_launch_first";

    private static final String KEY_APP_LAST_LOGIN_USER_IMAGE = "app_last_login_user_image";

    private static final String KEY_APP_LOGIN_USER_ID = "app_login_user_id";
    private static final String KEY_APP_LOGIN_USER_NAME = "app_login_user_name";
    private static final String KEY_APP_LOGIN_USER_IMAGE = "app_login_user_image";
    private static final String KEY_APP_LOGIN_USER_MOTTO = "app_login_user_motto";
    private static final String KEY_APP_LOGIN_STATE = "app_login_state";
    private static final String KEY_APP_NOTIFICATION = "app_notice";

    private static final String KEY_APP_LAST_GET_MSG = "app_last_get_msg";

    public static void saveLastGetMsgTimestamp(long timestamp) {
        saveLong(KEY_APP_LAST_GET_MSG, timestamp);
    }
    public static long getLastGetMsgTimestamp() {
        return getLong(KEY_APP_LAST_GET_MSG);
    }


    public static void saveNotification(boolean notice) {
        saveBoolean(KEY_APP_NOTIFICATION, notice);
    }
    public static boolean getNotification() {
       return getBoolean(KEY_APP_NOTIFICATION);
    }

    public static void saveLoginUsername(String username) {
        saveString(KEY_APP_LOGIN_USER_NAME, username);
    }
    public static void saveLoginUserImage(String userImage) {
        saveString(KEY_APP_LOGIN_USER_IMAGE, userImage);
    }
    public static void saveLoginUserMotto(String userImage) {
        saveString(KEY_APP_LOGIN_USER_MOTTO, userImage);
    }
    public static String getLoginUserMotto() {
        return getString(KEY_APP_LOGIN_USER_MOTTO);
    }
    public static String getLoginUsername() {
        return getString(KEY_APP_LOGIN_USER_NAME);
    }
    public static String getLoginUserImage() {
        return getString(KEY_APP_LOGIN_USER_IMAGE);
    }
    public static void saveLoginUserID(String userID) {
        saveString(KEY_APP_LOGIN_USER_ID, userID);
    }

    public static String getLoginUserID() {
        return getString(KEY_APP_LOGIN_USER_ID);
    }

    public static void saveLoginState(Boolean state) {
        saveBoolean(KEY_APP_LOGIN_STATE, state);
    }

    public static boolean getLoginState() {
        return getBoolean(KEY_APP_LOGIN_STATE);
    }

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
    private static void saveLong(String key, long value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putLong(key, value);
        editor.apply();
    }
    private static long getLong(String key) {
        return getSharedPreferences().getLong(key, 1514619494840L);
    }
    private static boolean getBoolean(String key) {
        return getSharedPreferences().getBoolean(key, false);
    }
    private static void saveString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.apply();
    }

    private static String getString(String key) {
        return getSharedPreferences().getString(key, "");
    }

    private static SharedPreferences getSharedPreferences() {
        return Utils.getContext().getSharedPreferences("App", 0);
    }

}
