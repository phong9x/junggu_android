package com.trams.joonggu_nubigo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.trams.joonggu_nubigo.common.Constant;

/**
 *
 */
public class PreferUtils {

    private static final String PREFER_JOONGGU_NAME = "com_trams_joonggu";

    private static final String PREFER_IS_LOGIN = "is_login";
    private static final String PREFER_USER_ID = "user_id";

    public static void setLogin(Context context, boolean isLogin) {
        Editor editor = context.getSharedPreferences(PREFER_JOONGGU_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(PREFER_IS_LOGIN, isLogin);
        editor.commit();
    }

    public static boolean isLogin(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFER_JOONGGU_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(PREFER_IS_LOGIN, false);
    }

    public static void setUserId(Context context, int userId) {
        Editor editor = context.getSharedPreferences(PREFER_JOONGGU_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(PREFER_USER_ID, userId);
        editor.commit();
    }

    public static int getUserId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFER_JOONGGU_NAME, Context.MODE_PRIVATE);
        return preferences.getInt(PREFER_USER_ID,0);
    }

    public static final void saveSession(Context context,String session) {
        SharedPreferences prefs = context.getSharedPreferences(
                Constant.PREF_NAME, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constant.SESSION, session);
        editor.commit();
    }
}