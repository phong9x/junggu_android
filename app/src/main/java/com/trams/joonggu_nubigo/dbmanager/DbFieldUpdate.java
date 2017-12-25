package com.trams.joonggu_nubigo.dbmanager;

import android.app.Activity;
import android.content.Context;

import com.trams.joonggu_nubigo.dao.Field;
import com.trams.joonggu_nubigo.dao.FieldDao;
import com.trams.joonggu_nubigo.utils.LogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 13/11/2015.
 */
public class DbFieldUpdate extends DbHelperUpdate<Field, FieldDao> {

    private static final String TAG = DbCategoryUpdate.class.getName();
    private Context context;

    public DbFieldUpdate(Context context, String url) {
        super(context, url);
        this.context = context;
    }

    private UpdateDbItf.OnResponseFail onResponseFail;
    private UpdateDbItf.OnResponseSuccess onResponseSuccess;

    public UpdateDbItf.OnResponseFail getOnResponseFail() {
        return onResponseFail;
    }

    public void setOnResponseFail(UpdateDbItf.OnResponseFail onResponseFail) {
        this.onResponseFail = onResponseFail;
    }

    public UpdateDbItf.OnResponseSuccess getOnResponseSuccess() {
        return onResponseSuccess;
    }

    public void setOnResponseSuccess(UpdateDbItf.OnResponseSuccess onResponseSuccess) {
        this.onResponseSuccess = onResponseSuccess;
    }

    public synchronized List<Field> getAllFields(Activity activity) {
        LogUtils.d(TAG, "getAllFields start");
        FieldDao fieldDao = DBHelper.getIstance(context).getDaoSession().getFieldDao();
        List<Field> fields = fieldDao.loadAll();
        DBHelper.getIstance(context).clearSession();

        LogUtils.d(TAG, "getAllFields , size : " + fields.size());
        return fields;
    }

    @Override
    public long getLastUpdate() {
        long result = 0;
        FieldDao fieldDao = DBHelper.getIstance(context).getDaoSession().getFieldDao();

        Field field = fieldDao.queryBuilder().orderDesc(FieldDao.Properties.UpdateDate).limit(1).unique();
        if (field != null) {
            LogUtils.d(TAG, "getLastUpdate, field result : " + field.toString());
            result = field.getUpdateDate().getTime();
        }

        DBHelper.getIstance(context).clearSession();

        return result;
    }

//    public synchronized List<Field> getAllField(Activity activity) {
//        LogUtils.d(TAG, "getAllField start");
//        FieldDao fieldDao = DBHelper.getIstance(context).getDaoSession().getFieldDao();
//        List<Field> fields = fieldDao.loadAll();
//        DBHelper.getIstance(context).clearSession();
//        LogUtils.d(TAG, "getAllField , size : " + fields.size());
//        return fields;
//    }

    @Override
    public List<Field> parseData(JSONObject jsonResponse) {

        LogUtils.d(TAG, "parseData , jsonResponse field data : " + jsonResponse.toString());
        ArrayList<Field> fields = new ArrayList<Field>();

        try {

            if (jsonResponse.isNull("value")) return fields;

            JSONArray jsonArr = jsonResponse.getJSONArray("value");

            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObject = jsonArr.getJSONObject(i);

                Field field = new Field();

                field.setId(jsonObject.getInt("id"));
                field.setName(jsonObject.getString("name"));
                field.setDescription(jsonObject.getString("description"));
                field.setCreateDate(new Date(jsonObject.getLong("createDate")));
                field.setUpdateDate(new Date(jsonObject.getLong("updateDate")));

                fields.add(field);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return fields;
    }

    @Override
    public void updateDb(List<Field> listT) {
        LogUtils.d(TAG, "updateDb start , listT size : " + listT.size());
        if (listT.size() > 0) {
            FieldDao fieldDao = DBHelper.getIstance(context).getDaoSession().getFieldDao();

            for (int i = 0; i < listT.size(); i++) {
                LogUtils.d(TAG, "updateDb Field update start ----- " + i + " : " + listT.get(i).toString());
                fieldDao.insertOrReplace(listT.get(i));
                LogUtils.d(TAG, "updateDb Field update finish ----- " + i + " : " + listT.get(i).toString());
            }

            DBHelper.getIstance(context).clearSession();

        }

        if (onResponseFail != null) onResponseFail.onResponseFail();
    }

    @Override
    public void getAllDb() {
        if (onResponseSuccess != null) onResponseSuccess.onResponseSuccess();
    }
}
