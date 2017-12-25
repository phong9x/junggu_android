package com.trams.joonggu_nubigo.dbmanager;

import android.content.Context;

import com.trams.joonggu_nubigo.dao.Accessibility;
import com.trams.joonggu_nubigo.dao.AccessibilityDao;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by HuyDV on 07/11/2015.
 */
public class AccessibilityManager extends DatabaseManager {

    private static final String TAG = AccessibilityManager.class.getSimpleName();

    private static AccessibilityManager instance;

    private AccessibilityManager(final Context context) {
        init(context);
    }

    public static AccessibilityManager getInstance(Context context) {

        if (instance == null) {
            instance = new AccessibilityManager(context);
        }

        return instance;
    }

    @Override
    public synchronized void closeDbConnections() {
        super.closeDbConnections();
        if (instance != null) {
            instance = null;
        }
    }

    public synchronized List<Accessibility> getAccessibilities() {
        List<Accessibility> accessibilities = new ArrayList<>();
        try {
            openReadableDb();
            AccessibilityDao accessibilityDao = daoSession.getAccessibilityDao();
            accessibilities = accessibilityDao.loadAll();

            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessibilities;
    }

}
