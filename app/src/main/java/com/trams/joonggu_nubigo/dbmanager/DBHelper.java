package com.trams.joonggu_nubigo.dbmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.trams.joonggu_nubigo.common.Constant;
import com.trams.joonggu_nubigo.dao.DaoMaster;
import com.trams.joonggu_nubigo.dao.DaoSession;

/**
 * Created by Administrator on 11/11/2015.
 */
public class DBHelper {
    public static DBHelper istance;
    private static Context context;
    private static DaoSession daoSession;

    public static DBHelper getIstance(Context _context){
        if(istance == null){
            istance = new DBHelper();
            context = _context;
            init();
        }
        return istance;
    }

    public static void init(){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, Constant.DB_NAME, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession(){
        return daoSession;
    }

    public void clearSession() {
        if (daoSession != null) {
            daoSession.clear();
        }
    }
}
