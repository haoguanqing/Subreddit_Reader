package com.guanqing.subredditor.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

/**
 * Created by Guanqing on 2015/12/1.
 */
public class SharedPrefUtil {

    public static final String USER_PREFS = "USER_PREFERENCE";
    public static final String USER_NAME_KEY = "USER_NAME_KEY";
    public static final String USER_PASSWORD_KEY = "USER_PASSWORD_KEY";
    public static final String USER_AVATAR_KEY = "USER_AVATAR_KEY";

    /**
     * save user information when login
     * @param context
     * @param username
     * @param password
     * @param avatarUri
     */
    public static void login(Context context, String username, String password, Uri avatarUri){
        String uri = avatarUri.toString();
        SharedPreferences.Editor editor = context.getSharedPreferences(USER_PREFS, 0).edit();
        editor.putString(USER_NAME_KEY, username)
                .putString(USER_PASSWORD_KEY, password)
                .putString(USER_AVATAR_KEY, uri)
                .apply();
    }

    /**
     * get user name
     * @param context
     * @return username
     */
    public static String getUserName(Context context){
        return context.getSharedPreferences(USER_PREFS, 0).getString(USER_NAME_KEY, null);
    }

    /**
     * get user's avatar's file path
     * @param context
     * @return avatarFilePath
     */
    public static Uri getAvatarFilePath(Context context){
        String uri =  context.getSharedPreferences(USER_PREFS, 0).getString(USER_AVATAR_KEY, null);
        return Uri.parse(uri);
    }

    /**
     * clear all user data when logout
     * @param context
     */
    public static void logout(Context context){
        SharedPreferences pref = context.getSharedPreferences(USER_PREFS, 0);
        pref.edit().clear().apply();
    }
}
