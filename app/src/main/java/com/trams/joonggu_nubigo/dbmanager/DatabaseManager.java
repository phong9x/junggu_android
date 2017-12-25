package com.trams.joonggu_nubigo.dbmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.trams.joonggu_nubigo.common.Constant;
import com.trams.joonggu_nubigo.dao.DaoMaster;
import com.trams.joonggu_nubigo.dao.DaoSession;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.greenrobot.dao.async.AsyncOperation;
import de.greenrobot.dao.async.AsyncOperationListener;
import de.greenrobot.dao.async.AsyncSession;

/**
 * Created by HuyDV on 07/11/2015.
 */
public abstract class DatabaseManager implements IDatabaseManager, AsyncOperationListener {

    protected Context context;
    protected DaoMaster.DevOpenHelper mHelper;
    protected SQLiteDatabase database;
    protected DaoMaster daoMaster;
    protected DaoSession daoSession;
    protected AsyncSession asyncSession;
    protected List<AsyncOperation> completedOperations;

    @Override
    public void init(Context context) {
        this.context = context;
        mHelper = new DaoMaster.DevOpenHelper(this.context, Constant.DB_NAME, null);
        completedOperations = new CopyOnWriteArrayList<>();
    }

    @Override
    public void onAsyncOperationCompleted(AsyncOperation operation) {
        completedOperations.add(operation);
    }

    public void assertWaitForCompletion1Sec() {
        asyncSession.waitForCompletion(1000);
        asyncSession.isCompleted();
    }

    public void openReadableDb() throws SQLiteException {
        database = mHelper.getReadableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
        asyncSession = daoSession.startAsyncSession();
        asyncSession.setListener(this);
    }

    public void openWritableDb() throws SQLiteException {
        database = mHelper.getWritableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
        asyncSession = daoSession.startAsyncSession();
        asyncSession.setListener(this);
    }

    @Override
    public void closeDbConnections() {
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }
        if (database != null && database.isOpen()) {
            database.close();
        }
        if (mHelper != null) {
            mHelper.close();
            mHelper = null;
        }
    }

}

