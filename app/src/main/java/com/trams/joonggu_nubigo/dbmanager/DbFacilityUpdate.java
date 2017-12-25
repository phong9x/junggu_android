package com.trams.joonggu_nubigo.dbmanager;

import android.app.Activity;
import android.content.Context;

import com.trams.joonggu_nubigo.dao.Facility;
import com.trams.joonggu_nubigo.dao.FacilityDao;
import com.trams.joonggu_nubigo.utils.LogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 17/11/2015.
 */
public class DbFacilityUpdate extends DbHelperUpdate<Facility, FacilityDao> {

    private Context context;
    private static final String TAG = DbFacilityUpdate.class.getName();

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

    public DbFacilityUpdate(Context context, String url) {
        super(context, url);
        this.context = context;
    }

    public DbFacilityUpdate(Context context) {
        super(context, null);
        this.context = context;
    }

    @Override
    public long getLastUpdate() {
        long result = 0;
        FacilityDao facilityDao = DBHelper.getIstance(context).getDaoSession().getFacilityDao();

        Facility facility = facilityDao.queryBuilder().orderDesc(FacilityDao.Properties.UpdateDate).limit(1).unique();
        if (facility != null) {
            LogUtils.d(TAG, "getLastUpdate, facility result : " + facility.toString());
            result = facility.getUpdateDate().getTime();
        }

        DBHelper.getIstance(context).clearSession();

        return result;
    }

    @Override
    public List<Facility> parseData(JSONObject jsonResponse) {
        LogUtils.d(TAG, "parseData , jsonResponse facility : " + jsonResponse.toString());
        ArrayList<Facility> facilities = new ArrayList<Facility>();

        try {

            if (jsonResponse.isNull("value")) return facilities;

            JSONArray jsonArr = jsonResponse.getJSONArray("value");

            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObject = jsonArr.getJSONObject(i);

                Facility facility = new Facility();
                facility.setId(jsonObject.getInt("id"));
                facility.setName(jsonObject.getString("name"));
                facility.setDescription(jsonObject.getString("description"));
                facility.setCreateDate(new Date(jsonObject.getLong("createDate")));
                facility.setUpdateDate(new Date(jsonObject.getLong("updateDate")));
                facilities.add(facility);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return facilities;
    }

    @Override
    public void updateDb(List<Facility> listT) {
        LogUtils.d(TAG, "updateDb start facility tbl, listT size : " + listT.size());
        if (listT.size() > 0) {
            FacilityDao facilityDao = DBHelper.getIstance(context).getDaoSession().getFacilityDao();

            for (int i = 0; i < listT.size(); i++) {
                LogUtils.d(TAG, "updateDb facility update start ------------------- " + i + " : " + listT.get(i).toString());
                facilityDao.insertOrReplace(listT.get(i));
                LogUtils.d(TAG, "updateDb facility update finish ------------------- ");
            }

            DBHelper.getIstance(context).clearSession();

        }

        if (onResponseSuccess != null) onResponseSuccess.onResponseSuccess();
    }


    public synchronized List<Facility> getAllFacility(Activity activity) {
        LogUtils.d(TAG, "getAllFacility start");
        FacilityDao facilityDao = DBHelper.getIstance(context).getDaoSession().getFacilityDao();
        List<Facility> facilities = facilityDao.loadAll();
        DBHelper.getIstance(context).clearSession();

        LogUtils.d(TAG, "getAllFacility , size : " + facilities.size());
        return facilities;
    }

    public synchronized Facility getFacility(Activity activity, long id) {
        LogUtils.d(TAG, "getAllFacility start");
        FacilityDao facilityDao = DBHelper.getIstance(context).getDaoSession().getFacilityDao();
        Facility facility = facilityDao.loadByRowId(id);
        DBHelper.getIstance(context).clearSession();

        return facility;
    }

    @Override
    public void getAllDb() {
        if (onResponseFail != null) onResponseFail.onResponseFail();
    }

}
