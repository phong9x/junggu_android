package com.trams.joonggu_nubigo.dbmanager;

import android.app.Activity;
import android.content.Context;

import com.trams.joonggu_nubigo.dao.Category;
import com.trams.joonggu_nubigo.dao.CategoryDao;
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
public class DbCategoryUpdate extends DbHelperUpdate<Category, CategoryDao> {

    private static final String TAG = DbCategoryUpdate.class.getName();
    private Context context;

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

    public DbCategoryUpdate(Context context, String url) {
        super(context, url);
        this.context = context;
    }

    public synchronized List<Category> getAllCategory(Activity activity) {
        LogUtils.d(TAG, "getAllCategory start");
        CategoryDao categoryDao = DBHelper.getIstance(context).getDaoSession().getCategoryDao();
        List<Category> categories = categoryDao.loadAll();
        DBHelper.getIstance(context).clearSession();

        LogUtils.d(TAG, "getAllCategory , size : " + categories.size());
        return categories;
    }

    public synchronized List<Category> getAllParent(Activity activity) {
        LogUtils.d(TAG, "getAllParent start");
        CategoryDao categoryDao = DBHelper.getIstance(context).getDaoSession().getCategoryDao();
        List<Category> categories = categoryDao.queryBuilder().where(CategoryDao.Properties.ParentId.eq(0)).list();
        DBHelper.getIstance(context).clearSession();

        LogUtils.d(TAG, "getAllParent , size : " + categories.size());
        return categories;
    }


    @Override
    public long getLastUpdate() {
        long result = 0;
        CategoryDao categoryDao = DBHelper.getIstance(context).getDaoSession().getCategoryDao();

        Category category = categoryDao.queryBuilder().orderDesc(CategoryDao.Properties.UpdateDate).limit(1).unique();
        if (category != null) {
            LogUtils.d(TAG, "getLastUpdate, category result : " + category.toString());
            result = category.getUpdateDate().getTime();
        }

        DBHelper.getIstance(context).clearSession();

        return result;
    }

    @Override
    public List<Category> parseData(JSONObject jsonResponse) {
        LogUtils.d(TAG, "parseData , jsonResponse category : " + jsonResponse.toString());
        ArrayList<Category> categories = new ArrayList<Category>();

        try {

            if(jsonResponse.isNull("value")) return categories;

            JSONArray jsonArr = jsonResponse.getJSONArray("value");

            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObject = jsonArr.getJSONObject(i);

                Category category = new Category();

                category.setId(jsonObject.getInt("id"));
                category.setCatName(jsonObject.getString("catName"));
                category.setDescription(jsonObject.getString("description"));
                category.setParentId(jsonObject.getInt("parentId"));
                category.setImage(jsonObject.getString("image"));
                category.setEtc(jsonObject.getString("etc"));
                category.setCreateDate(new Date(jsonObject.getLong("createDate")));
                category.setUpdateDate(new Date(jsonObject.getLong("updateDate")));
                categories.add(category);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return categories;
    }

    @Override
    public void updateDb(List<Category> listT) {
        LogUtils.d(TAG, "updateDb start category, listT size : " + listT.size());
        if (listT.size() > 0) {
            CategoryDao categoryDao = DBHelper.getIstance(context).getDaoSession().getCategoryDao();

            for (int i = 0; i < listT.size(); i++) {
                LogUtils.d(TAG, "updateDb category update start ------------------- " + i + " : " + listT.get(i).toString());
                categoryDao.insertOrReplace(listT.get(i));
                LogUtils.d(TAG, "updateDb category update finish ------------------- ");
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
