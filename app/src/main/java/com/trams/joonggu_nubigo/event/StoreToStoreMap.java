package com.trams.joonggu_nubigo.event;

import com.trams.joonggu_nubigo.dao.Store;

import java.util.List;

/**
 * Created by HuyDV on 08/11/2015.
 */
public class StoreToStoreMap {

    public final String region;
    public final List<Store> stores;

    public StoreToStoreMap(String region, List<Store> stores) {
        this.region = region;
        this.stores = stores;
    }
}
