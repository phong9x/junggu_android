package com.trams.joonggu_nubigo.common;

import android.os.Environment;

/**
 * Created by Administrator on 27/10/2015.
 */
public class Constant {

    public static final boolean DEBUG = true;

    public static final int SPLASH_TIME = 2000;

    public static final int STORE_TIME = 3000;

    public static final String PREF_NAME = "MindLink";

    public static final String SESSION = "SESSION";

    public static final String DB_NAME = "database.sqlite";

    public static final String DOWNLOADED_ZIP_DB_PATH = Environment.getExternalStorageDirectory() + "/db.zip";

    public static final String NAVER_MAP_API_KEY = "c322d8f081ef93b16b8b3ff282d205a8";

    public static final boolean CACHE_ON_DISK = true;
    public static final boolean CACHE_ON_MEMORY = false;
    public static final float WIDTH_HEIGHT_IMG_DETAIL = 350f;
    public static final float WIDTH_HEIGHT_IMG_PRESENT = 200f;


}
