package com.guanqing.subredditor.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.guanqing.subredditor.App;

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
     * @param username
     * @param password
     * @param avatarUri
     */
    public static void login(Context context, String username, String password, Uri avatarUri){
        String uri = avatarUri.toString();
        SharedPreferences.Editor editor = App.getInstance().getSharedPreferences(USER_PREFS, 0).edit();
        editor.putString(USER_NAME_KEY, username)
                .putString(USER_PASSWORD_KEY, password)
                .putString(USER_AVATAR_KEY, uri)
                .apply();
    }

    /**
     * get user name
     * @return username
     */
    public static String getUserName(){
        return App.getInstance().getSharedPreferences(USER_PREFS, 0).getString(USER_NAME_KEY, null);
    }

    /**
     * get password
     * @return password
     */
    public static String getPassword(){
        return App.getInstance().getSharedPreferences(USER_PREFS, 0).getString(USER_PASSWORD_KEY, null);
    }

    /**
     * get user's avatar's file path
     * @return avatarFilePath
     */
    public static Uri getAvatarFilePath(){
        String uri =  App.getInstance().getSharedPreferences(USER_PREFS, 0).getString(USER_AVATAR_KEY, null);
        return Uri.parse(uri);
    }

    /**
     * clear all user data when logout
     */
    public static void logout(){
        SharedPreferences pref = App.getInstance().getSharedPreferences(USER_PREFS, 0);
        pref.edit().clear().apply();
    }
}
