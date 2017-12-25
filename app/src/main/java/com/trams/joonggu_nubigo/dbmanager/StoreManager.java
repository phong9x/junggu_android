package com.trams.joonggu_nubigo.dbmanager;

import android.content.Context;

import com.trams.joonggu_nubigo.dao.CategoryDao;
import com.trams.joonggu_nubigo.dao.Store;
import com.trams.joonggu_nubigo.dao.StoreDao;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.WhereCondition;

/**
 * Created by Dang on 09/11/2015.
 */
public class StoreManager extends DatabaseManager {

    private static final String TAG = StoreManager.class.getCanonicalName();

    private static StoreManager instance;

    public StoreManager(final Context context) {
        init(context);
    }

    public static StoreManager getInstance(Context context) {

        if (instance == null) {
            instance = new StoreManager(context);
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

    public synchronized List<Store> getStores(List<Long> filterAccess, long parentId) {
        List<Store> stores = new ArrayList<>();
        try {
            openReadableDb();
            StoreDao storeDao = daoSession.getStoreDao();
            if (parentId == 0) {
                stores = storeDao.loadAll();
            } else {
                String stringRawquery =
                        StoreDao.Properties.CatId.columnName +
                                " in ( select " + CategoryDao.Properties.Id.columnName + " from category where " + CategoryDao.Properties.ParentId.columnName + " = " + parentId + " )" +
                                " and ( ";
                for (int i = 0; i < filterAccess.size(); i++) {
                    stringRawquery = stringRawquery +
                            StoreDao.Properties.AccessibilityList.columnName +
                            " like " +
                            "'%" +
                            filterAccess.get(i) +
                            "%'";
                    if (i < filterAccess.size() - 1) {
                        stringRawquery = stringRawquery + " or ";
                    }
                }
                stringRawquery = stringRawquery + " )";
                Query query = storeDao.queryBuilder().where(new WhereCondition.StringCondition(stringRawquery)).build();
                stores = query.list();
            }

            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stores;
    }

//    public synchronized List<Store> getStores(List<Long> filterAccess, long catId) {
//        List<Store> stores = new ArrayList<>();
//        try {
//            openReadableDb();
//            StoreDao storeDao = daoSession.getStoreDao();
//            if (catId == 0) {
//                stores = storeDao.loadAll();
//            } else {
//                String stringRawquery =
//                        StoreDao.Properties.CatId.columnName +
//                                " = " +
//                                catId +
//                                " and ( ";
//                for (int i = 0; i < filterAccess.size(); i++) {
//                    stringRawquery = stringRawquery +
//                            StoreDao.Properties.AccessibilityList.columnName +
//                            " like " +
//                            "'%" +
//                            filterAccess.get(i) +
//                            "%'";
//                    if (i < filterAccess.size() - 1) {
//                        stringRawquery = stringRawquery + " or ";
//                    }
//                }
//                stringRawquery = stringRawquery + " )";
//                Query query = storeDao.queryBuilder().where(new WhereCondition.StringCondition(stringRawquery)).build();
//                stores = query.list();
//            }
//
//            daoSession.clear();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return stores;
//    }

    public synchronized List<Store> getListSearch(String enteredText, List<Long> filterAccess) {
        List<Store> stores = new ArrayList<>();
        try {
            openReadableDb();
            StoreDao storeDao = daoSession.getStoreDao();

            String stringRawquery =
                    "( " +
                            StoreDao.Properties.Name.columnName +
                            " like " +
                            " '%" +
                            enteredText +
                            "%' " +
                            " or " +
                            StoreDao.Properties.Address.columnName +
                            " like " +
                            " '%" +
                            enteredText +
                            "%'" +
                            " ) and ( ";
            for (int i = 0; i < filterAccess.size(); i++) {
                stringRawquery = stringRawquery +
                        StoreDao.Properties.AccessibilityList.columnName +
                        " like " +
                        "'%" +
                        filterAccess.get(i) +
                        "%'";
                if (i < filterAccess.size() - 1) {
                    stringRawquery = stringRawquery + " or ";
                }
            }
            stringRawquery = stringRawquery + " )";
            Query query = storeDao.queryBuilder().where(new WhereCondition.StringCondition(stringRawquery)).build();
            stores = query.list();

            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stores;
    }
}
