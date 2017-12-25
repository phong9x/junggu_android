package com.trams.joonggu_nubigo.dbmanager;

import android.app.Activity;
import android.content.Context;

import com.trams.joonggu_nubigo.common.Constant;
import com.trams.joonggu_nubigo.dao.CategoryDao;
import com.trams.joonggu_nubigo.dao.Store;
import com.trams.joonggu_nubigo.dao.StoreDao;
import com.trams.joonggu_nubigo.network.WebServiceConfig;
import com.trams.joonggu_nubigo.utils.LogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;

/**
 * Created by zin9x on 11/12/2015.
 */
public class DbStoreUpdate extends DbHelperUpdate<Store, StoreDao> {
    private Context context;
    private static final String TAG = DbStoreUpdate.class.getName();

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

    public DbStoreUpdate(Context context, String url) {
        super(context, url);
        this.context = context;
    }

    @Override
    public long getLastUpdate() {
        long result = 0;
        StoreDao storeDao = DBHelper.getIstance(context).getDaoSession().getStoreDao();

        Store store = storeDao.queryBuilder().orderDesc(StoreDao.Properties.UpdateDate).limit(1).unique();
        if (store != null) {
            LogUtils.d(TAG, "getLastUpdate, accessibility result : " + store.toString());
            result = store.getUpdateDate().getTime();
        }

        DBHelper.getIstance(context).clearSession();

        return result;
    }

    public List<Store> getStorePage(int page) {
        StoreDao storeDao = DBHelper.getIstance(context).getDaoSession().getStoreDao();
        List<Store> result = storeDao.queryBuilder().orderDesc(StoreDao.Properties.UpdateDate).limit(10 * page).list();
        DBHelper.getIstance(context).clearSession();
        return result;
    }

    @Override
    public List<Store> parseData(JSONObject jsonResponse) {
        LogUtils.d(TAG, "parseData , jsonResponse : " + jsonResponse.toString());
        ArrayList<Store> listStore = new ArrayList<Store>();

        try {

            if (jsonResponse.isNull("value")) return listStore;

            JSONArray jsonArr = jsonResponse.getJSONArray("value");

            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObject = jsonArr.getJSONObject(i);
                Store store = new Store();
                store.setId(jsonObject.getInt(WebServiceConfig.KEY_ID));
                store.setName(jsonObject.getString(WebServiceConfig.KEY_NAME));
                store.setTag(jsonObject.getString(WebServiceConfig.KEY_TAG));
                store.setServiceHours(jsonObject.getString(WebServiceConfig.KEY_SERVICE_HOURS));
                store.setHoliday(jsonObject.getString(WebServiceConfig.KEY_HOLIDAY));
                store.setBuildingForm(jsonObject.getString(WebServiceConfig.KEY_BUILDING_FORM));
                store.setFloor(jsonObject.getString(WebServiceConfig.KEY_FLOOR));
                store.setFacilityList(jsonObject.getString(WebServiceConfig.KEY_FACILITY_LIST));
                store.setRepresentative(jsonObject.getString(WebServiceConfig.KEY_REPRESENTATIVE));
                store.setPhone(jsonObject.getString(WebServiceConfig.KEY_PHONE));
                store.setAddress(jsonObject.getString(WebServiceConfig.KEY_ADDRESS));

                if (!jsonObject.isNull(WebServiceConfig.KEY_MONITORING_DATE))
                    store.setMonitoringDate(new Date(jsonObject.getInt(WebServiceConfig.KEY_MONITORING_DATE)));

                store.setMonitoringMan(jsonObject.getString(WebServiceConfig.KEY_MONITORING_MAN));
                store.setMonitoringManPhone(jsonObject.getString(WebServiceConfig.KEY_MONITORING_MAN_PHONE));
                store.setFieldList(jsonObject.getString(WebServiceConfig.KEY_FIELD_LIST));
                store.setImageBaseAttach(jsonObject.getString(WebServiceConfig.KEY_IMAGE_BASE_ATTACH));
                store.setImageExtendAttach(jsonObject.getString(WebServiceConfig.KEY_IMAGE_EXTEND_ATTACH));
                store.setGrade(jsonObject.getString(WebServiceConfig.KEY_GRADE));
                store.setAccessibilityList(jsonObject.getString(WebServiceConfig.KEY_ACCESSIBILITYLIST));
                store.setLongitude(jsonObject.getString(WebServiceConfig.KEY_LONGITUDE));
                store.setLatitude(jsonObject.getString(WebServiceConfig.KEY_LATITUDE));
                store.setCreateDate(new Date(jsonObject.getLong(WebServiceConfig.KEY_CREATE_DATE)));
                store.setUpdateDate(new Date(jsonObject.getLong(WebServiceConfig.KEY_UPDATE_DATE)));

                store.setOtherInfo(jsonObject.getString("otherInfo"));
                store.setIsDelete(jsonObject.getInt("isDelete"));

                store.setCatId(jsonObject.getInt(WebServiceConfig.KEY_CAT_ID));

////                fake data
//                store.setCatId(1);

                store.setUserId(jsonObject.getInt(WebServiceConfig.KEY_USER_ID));

                listStore.add(store);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listStore;
    }

    public synchronized List<Store> getAllStore(Activity activity) {
        LogUtils.d(TAG, "getStore start");
        StoreDao storeDao = DBHelper.getIstance(context).getDaoSession().getStoreDao();
        List<Store> stores = storeDao.loadAll();
        DBHelper.getIstance(context).clearSession();
        LogUtils.d(TAG, "getAllStore , size : " + stores.size());
        return stores;
    }


    @Override
    public void updateDb(List<Store> listT) {
        LogUtils.d(TAG, "updateDb start , listT size : " + listT.size());

        StoreDao storeDao = DBHelper.getIstance(context).getDaoSession().getStoreDao();

        if (listT.size() > 0) {
            for (int i = 0; i < listT.size(); i++) {
                LogUtils.d(TAG, "updateDb store update start ------------------- " + i + " : " + listT.get(i).toString());
                storeDao.insertOrReplace(listT.get(i));
                LogUtils.d(TAG, "updateDb store update finish ------------------- ");
            }

        }

        DBHelper.getIstance(context).clearSession();

        storeDao.queryBuilder().where(StoreDao.Properties.IsDelete.eq(1)).buildDelete();

        DBHelper.getIstance(context).clearSession();



        if (onResponseSuccess != null) onResponseSuccess.onResponseSuccess();
    }

    @Override
    public void getAllDb() {
        if (onResponseFail != null) onResponseFail.onResponseFail();
    }

    public List<Store> getStoreScrap(int userId) {
        StoreDao storeDao = DBHelper.getIstance(context).getDaoSession().getStoreDao();
        QueryBuilder qb = storeDao.queryBuilder();
        qb.where(StoreDao.Properties.UserId.eq(userId));
        List<Store> result = qb.list();
        LogUtils.d(TAG, "getStoreScrap , result size : " + result.size());
        return result;
    }

    public List<Store> getStoreFilterInMapScreen(int parentId, int fieldId, ArrayList<Integer> accList) {
        LogUtils.d(TAG, "getStoreFilterInMapScreen , start , parentId " + parentId + " , fieldId : " + fieldId + " , accList : " + accList);

        StoreDao storeDao = DBHelper.getIstance(context).getDaoSession().getStoreDao();
        List<Store> result = new ArrayList<>();
        QueryBuilder qb = storeDao.queryBuilder();

        if (fieldId == 0) {
            String stringRawquery =
                    StoreDao.Properties.CatId.columnName +
                            " in ( select " + CategoryDao.Properties.Id.columnName + " from category where " + CategoryDao.Properties.ParentId.columnName + " = " + parentId + " )";
            for (int i = 0; i < accList.size(); i++) {
                stringRawquery = stringRawquery + " and " +
                        StoreDao.Properties.AccessibilityList.columnName +
                        " like " +
                        getValueInLikeSQL(accList.get(i));
            }
            Query query = qb.where(new WhereCondition.StringCondition(stringRawquery)).build();
            result = query.list();
        } else {

//            //query 1,2,3 in 1,2,4,3
//            WhereCondition whereConditionCat = StoreDao.Properties.CatId.eq(catId);
//            WhereCondition whereConditionField = StoreDao.Properties.FieldList.like(getValueInLikeSQL(fieldId));
////            qb.and(whereConditionCat,whereConditionField);
//            qb.where(whereConditionCat, whereConditionField);
//            for (int i = 0; i < accList.size(); i++) {
//                WhereCondition whereConditionAcc = StoreDao.Properties.AccessibilityList.like(getValueInLikeSQL(accList.get(i)));
////                qb.and(whereConditionCat,whereConditionAcc);
//                qb.where(whereConditionCat, whereConditionAcc);
//            }


            String stringRawquery =
                    StoreDao.Properties.CatId.columnName +
                            " in ( select " + CategoryDao.Properties.Id.columnName + " from category where " + CategoryDao.Properties.ParentId.columnName + " = " + parentId + " )" +
                            " and " + StoreDao.Properties.FieldList.columnName + " like " + getValueInLikeSQL(fieldId);
            for (int i = 0; i < accList.size(); i++) {
                stringRawquery = stringRawquery + " and " +
                        StoreDao.Properties.AccessibilityList.columnName +
                        " like " +
                        getValueInLikeSQL(accList.get(i));
            }
            Query query = qb.where(new WhereCondition.StringCondition(stringRawquery)).build();
            result = query.list();

            LogUtils.d(TAG, "getStoreFilterInMapScreen field # 0 , parentId : " + parentId + " , FieldList : " + fieldId + " , accList : " + accList);

        }

        if (Constant.DEBUG) {
            qb.LOG_SQL = true;
            qb.LOG_VALUES = true;
        }

        LogUtils.d(TAG, "getStoreFilterInMapScreen , result : " + result.size());
        return result;

//        List<Info> infoArray = infoDao.queyBuilder()
//                .whereOr(infoDao.Properties.Display_name.like("%" + string + "%"),
//                        infoDao.Properties.Email.like("%" + string + "%"),
//                        infoDao.Properties.Phone.like("%" + string + "%"),
//                        infoDao.Properties.Code.like("%" + string + "%"))
//                .list();

//        QueryBuilder qb = userDao.queryBuilder();
//        qb.where(Properties.FirstName.eq("Joe"),
//                qb.or(Properties.YearOfBirth.gt(1970),
//                        qb.and(Properties.YearOfBirth.eq(1970), Properties.MonthOfBirth.ge(10))));
//        List youngJoes = qb.list();

    }

    public List<Store> getStoreByListId(ArrayList<Integer> ids) {
        StoreDao storeDao = DBHelper.getIstance(context).getDaoSession().getStoreDao();
        QueryBuilder qb = storeDao.queryBuilder();
        qb.where(StoreDao.Properties.Id.in(ids));
        List<Store> result = qb.list();
        LogUtils.d(TAG, "getStoreByListId , result size : " + result.size());
        return result;
    }

    public String getValueInLikeSQL(int input) {
        String result = "'%\"" + input + "\"%'";
        return result;
    }

}
