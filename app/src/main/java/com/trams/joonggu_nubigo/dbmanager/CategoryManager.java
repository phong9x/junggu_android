package com.trams.joonggu_nubigo.dbmanager;

import android.content.Context;

import com.trams.joonggu_nubigo.dao.Category;
import com.trams.joonggu_nubigo.dao.CategoryDao;

import java.util.List;


/**
 * Created by HuyDV on 07/11/2015.
 */
public class CategoryManager extends DatabaseManager {

    private static final String TAG = CategoryManager.class.getSimpleName();

    private static CategoryManager instance;

    private CategoryManager(final Context context) {
        init(context);
    }

    public static CategoryManager getInstance(Context context) {

        if (instance == null) {
            instance = new CategoryManager(context);
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

    public synchronized List<Category> getCategories() {
        List<Category> categories = null;
        try {
            openReadableDb();
            CategoryDao categoryDao = daoSession.getCategoryDao();
            categories = categoryDao.loadAll();

            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }

   /* public synchronized List<Store> getStores(Long selectedId) {
        List<Store> stores = null;
        try {
            openReadableDb();
            CategoryDao categoryDao = daoSession.getCategoryDao();

            Category category = categoryDao.loadByRowId(selectedId);
            stores = category.getStores();

            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stores;
    }*/

}
