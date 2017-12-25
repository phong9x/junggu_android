package com.trams.joonggu_nubigo.dbmanager;

import android.content.Context;

import com.trams.joonggu_nubigo.dao.Store;
import com.trams.joonggu_nubigo.dao.StoreDao;
import com.trams.joonggu_nubigo.utils.LogUtils;

import java.util.List;

/**
 * Created by Administrator on 12/11/2015.
 */
public class DbScrapHelper {

    private static final String TAG = DbScrapHelper.class.getName();

    public static List<Store> getStore(Context context) {
        List<Store> listStore;

        StoreDao storeDao = DBHelper.getIstance(context).getDaoSession().getStoreDao();

        List<Store> stores = storeDao.loadAll();

        DBHelper.getIstance(context).clearSession();

        LogUtils.d(TAG, "getStore , stores size : " + stores.size());
        for(int i=0;i<stores.size();i++){
            LogUtils.d(TAG,"store " + i + " : " + stores.get(i).toString());
        }

        return stores;

    }
}
