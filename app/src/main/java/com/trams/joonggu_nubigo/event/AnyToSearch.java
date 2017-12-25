package com.trams.joonggu_nubigo.event;

import com.trams.joonggu_nubigo.dao.Store;

import java.util.List;

/**
 * Created by Dang on 09/11/2015.
 */
public class AnyToSearch {

    public final String enteredText;
    public final List<Store> stores;

    public AnyToSearch(String enteredText, List<Store> stores) {
        this.enteredText = enteredText;
        this.stores = stores;
    }
}
