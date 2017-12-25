package com.trams.joonggu_nubigo.dbmanager;

import android.content.Context;

import com.trams.joonggu_nubigo.dao.Field;
import com.trams.joonggu_nubigo.dao.FieldDao;

import java.util.List;


/**
 * Created by HuyDV on 07/11/2015.
 */
public class FieldManager extends DatabaseManager {

    private static final String TAG = FieldManager.class.getCanonicalName();

    private static FieldManager instance;

    private FieldManager(final Context context) {
        init(context);
    }

    public static FieldManager getInstance(Context context) {

        if (instance == null) {
            instance = new FieldManager(context);
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

    public synchronized List<Field> getFields() {
        List<Field> fields = null;
        try {
            openReadableDb();
            FieldDao fieldDao = daoSession.getFieldDao();
            fields = fieldDao.loadAll();

            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fields;
    }

}
