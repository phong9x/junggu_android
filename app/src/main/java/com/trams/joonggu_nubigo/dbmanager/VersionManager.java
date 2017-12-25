package com.trams.joonggu_nubigo.dbmanager;

import android.content.Context;

import com.trams.joonggu_nubigo.dao.Version;
import com.trams.joonggu_nubigo.dao.VersionDao;


/**
 * Created by HuyDV on 07/11/2015.
 */
public class VersionManager extends DatabaseManager {

    private static final String TAG = VersionManager.class.getSimpleName();

    private static VersionManager instance;

    public VersionManager(final Context context) {
        init(context);
    }

    public static VersionManager getInstance(Context context) {

        if (instance == null) {
            instance = new VersionManager(context);
        }

        return instance;
    }

    @Override
    public void closeDbConnections() {
        super.closeDbConnections();
        if (instance != null) {
            instance = null;
        }
    }

    public synchronized int getNewestVersion() {
        try {
            openReadableDb();
            VersionDao versionDao = daoSession.getVersionDao();
            Version version = versionDao.queryBuilder().orderDesc(VersionDao.Properties.Name).limit(1).unique();
            daoSession.clear();
            return Integer.parseInt(version.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
