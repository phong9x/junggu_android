package com.trams.joonggu_nubigo.dbmanager;

import android.content.Context;

import com.trams.joonggu_nubigo.dao.User;
import com.trams.joonggu_nubigo.dao.UserDao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 19/11/2015.
 */
public class DbUserUpdate extends DbHelperUpdate {

    private Context context;
    private static final String TAG = DbUserUpdate.class.getName();

    public DbUserUpdate(Context context, String url) {
        super(context, url);
        this.context = context;
    }

    public DbUserUpdate(Context context) {
        super(context, "");
        this.context = context;
    }

    @Override
    public long getLastUpdate() {
        return 0;
    }

    @Override
    public List parseData(JSONObject jsonResponse) {
        return null;
    }

    @Override
    public void updateDb(List listT) {

    }

    @Override
    public void getAllDb() {

    }

    public User getUserLogin() {
        UserDao userDao = DBHelper.getIstance(context).getDaoSession().getUserDao();
        User user = userDao.queryBuilder().limit(1).unique();
        DBHelper.getIstance(context).clearSession();
        return user;
    }

    public void insertUserLogin(User user) {
        UserDao userDao = DBHelper.getIstance(context).getDaoSession().getUserDao();
        userDao.insertOrReplace(user);
        DBHelper.getIstance(context).clearSession();
    }

    public void deleteAll() {
        UserDao userDao = DBHelper.getIstance(context).getDaoSession().getUserDao();
        userDao.deleteAll();
        DBHelper.getIstance(context).clearSession();
    }

    /*
    * @return 0(not yet), 1(scraped)
    * */

    public int getScrapedOrNot(long storeId, long userId) {
        UserDao userDao = DBHelper.getIstance(context).getDaoSession().getUserDao();
        User user = userDao.loadByRowId(userId);

        try {
            JSONArray jsonArray = new JSONArray(user.getScrap());
            for (int i = 0; i < jsonArray.length(); i++) {
                long value = jsonArray.getLong(i);
                if (value == storeId) {
                    return 1;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DBHelper.getIstance(context).clearSession();

        return 0;
    }





}
