package com.trams.joonggu_nubigo.dbmanager;

import android.app.Activity;
import android.content.Context;

import com.trams.joonggu_nubigo.dao.Accessibility;
import com.trams.joonggu_nubigo.dao.AccessibilityDao;
import com.trams.joonggu_nubigo.utils.LogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 10/11/2015.
 */
public class DbSettingUpdate extends DbHelperUpdate<Accessibility, AccessibilityDao> {

    private Context context;
    private static final String TAG = DbSettingUpdate.class.getName();

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

    public DbSettingUpdate(Context context, String url) {
        super(context, url);
        this.context = context;
    }

    @Override
    public synchronized long getLastUpdate() {
        long result = 0;
        AccessibilityDao accessibilityDao = DBHelper.getIstance(context).getDaoSession().getAccessibilityDao();

        Accessibility accessibility = accessibilityDao.queryBuilder().orderDesc(AccessibilityDao.Properties.UpdateDate).limit(1).unique();
        if (accessibility != null) {
            LogUtils.d(TAG, "getLastUpdate, accessibility result : " + accessibility.toString());
            result = accessibility.getUpdateDate().getTime();
        }

        DBHelper.getIstance(context).clearSession();

        return result;
    }

    @Override
    public List<Accessibility> parseData(JSONObject jsonResponse) {
        LogUtils.d(TAG, "parseData , jsonResponse : " + jsonResponse.toString());
        ArrayList<Accessibility> listAccessbility = new ArrayList<Accessibility>();

        try {

            if (jsonResponse.isNull("value")) return listAccessbility;

            JSONArray jsonArr = jsonResponse.getJSONArray("value");

            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObject = jsonArr.getJSONObject(i);

                Accessibility accessibility = new Accessibility();
                accessibility.setId(jsonObject.getInt("id"));
                accessibility.setName(jsonObject.getString("mobileName"));
                accessibility.setDescription(jsonObject.getString("description"));
                accessibility.setCreateDate(new Date(jsonObject.getLong("createDate")));
                accessibility.setUpdateDate(new Date(jsonObject.getLong("updateDate")));
                accessibility.setSelected(jsonObject.getInt("selected"));
                listAccessbility.add(accessibility);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return listAccessbility;
    }

    public synchronized List<Accessibility> getAllAccessbility(Activity activity) {
        LogUtils.d(TAG, "getAllAccessbility start");
        AccessibilityDao accessibilityDao = DBHelper.getIstance(context).getDaoSession().getAccessibilityDao();
        List<Accessibility> accessibilities = accessibilityDao.loadAll();
        DBHelper.getIstance(context).clearSession();
        LogUtils.d(TAG, "getAllAccessbility , size : " + accessibilities.size());
        DBHelper.getIstance(context).clearSession();
        return accessibilities;
    }

    @Override
    public synchronized void updateDb(List<Accessibility> listT) {
        LogUtils.d(TAG, "updateDb start , listT size : " + listT.size());
        if (listT.size() > 0) {
            AccessibilityDao accessibilityDao = DBHelper.getIstance(context).getDaoSession().getAccessibilityDao();

            for (int i = 0; i < listT.size(); i++) {
                LogUtils.d(TAG, "updateDb accessibility update start ------------------- " + i + " : " + listT.get(i).toString());
                accessibilityDao.insertOrReplace(listT.get(i));
                LogUtils.d(TAG, "updateDb accessibility update finish ------------------- ");
            }

            DBHelper.getIstance(context).clearSession();

        }

        if (onResponseSuccess != null) onResponseSuccess.onResponseSuccess();
    }

    @Override
    public void getAllDb() {
        if (onResponseFail != null) onResponseFail.onResponseFail();
    }
}
