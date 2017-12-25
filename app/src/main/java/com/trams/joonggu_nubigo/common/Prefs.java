package com.trams.joonggu_nubigo.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by huydv0109 on 14/09/2015.
 */
public class Prefs {

    private Context mContext;
    private SharedPreferences mSharedPreferences;

    private static final String IS_SEARCH_PREF = "IS_SEARCH_PREF";

    private static final String DB_VERSION_PREF = "DB_VERSION_PREF";

    private static final String FIRST_RUN_PREF = "FIRST_RUN_PREF";


    public Prefs(Context context) {
        this.mContext = context;

        mSharedPreferences = mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);

    }

    public void setSearch(boolean search) {
        mSharedPreferences.edit().putBoolean(IS_SEARCH_PREF, search).apply();
    }

    public boolean isSearch() {
        return mSharedPreferences.getBoolean(IS_SEARCH_PREF, false);
    }

    public void setDbVersion(int version) {
        mSharedPreferences.edit().putInt(DB_VERSION_PREF, version).apply();
    }

    public int getDbVersion() {
        return mSharedPreferences.getInt(DB_VERSION_PREF, 0);
    }


    public void setFirstRun(boolean firstRun) {
        mSharedPreferences.edit().putBoolean(FIRST_RUN_PREF, firstRun).apply();
    }

    public boolean isFirstRun() {
        return mSharedPreferences.getBoolean(FIRST_RUN_PREF, true);
    }

}
