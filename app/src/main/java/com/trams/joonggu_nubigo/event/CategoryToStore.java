package com.trams.joonggu_nubigo.event;

import com.trams.joonggu_nubigo.dao.Store;

import java.util.List;

/**
 * Created by HuyDV on 08/11/2015.
 */
public class CategoryToStore {

    public final String categoryName;
    public final List<Store> stores;
    public final long categoryId;

    public CategoryToStore(String categoryName, List<Store> stores, long categoryId) {
        this.categoryName = categoryName;
        this.stores = stores;
        this.categoryId = categoryId;
    }
}
