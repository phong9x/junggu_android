package com.trams.joonggu_nubigo.dbmanager;

import android.content.Context;
import android.database.sqlite.SQLiteException;

/**
 * Created by HuyDV on 07/11/2015.
 * Interface that provides methods for managing the database inside the Application
 */
public interface IDatabaseManager {


    void init(Context context);

    /**
     * Query for readable DB
     */
    void openReadableDb() throws SQLiteException;

    /**
     * Query for writable DB
     */
    void openWritableDb() throws SQLiteException;

    /**
     * Closing available connections
     */
    void closeDbConnections();

}
