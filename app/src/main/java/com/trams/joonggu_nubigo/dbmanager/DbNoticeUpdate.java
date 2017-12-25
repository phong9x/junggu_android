package com.trams.joonggu_nubigo.dbmanager;

import android.app.Activity;
import android.content.Context;

import com.trams.joonggu_nubigo.dao.Notice;
import com.trams.joonggu_nubigo.dao.NoticeDao;
import com.trams.joonggu_nubigo.objects.NoticeObj;
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
public class DbNoticeUpdate extends DbHelperUpdate<Notice, NoticeDao> {

    private Context context;
    private static final String TAG = DbNoticeUpdate.class.getName();

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

    public DbNoticeUpdate(Context context, String url) {
        super(context, url);
        this.context = context;
    }

    @Override
    public synchronized long getLastUpdate() {
        long result = 0;
        NoticeDao noticeDao = DBHelper.getIstance(context).getDaoSession().getNoticeDao();

        Notice noticeResult = noticeDao.queryBuilder().orderDesc(NoticeDao.Properties.UpdateDate).limit(1).unique();
        if (noticeResult != null) {
            LogUtils.d(TAG, "getLastUpdate, notice result : " + noticeResult.toString());
            result = noticeResult.getUpdateDate().getTime();
        }

        DBHelper.getIstance(context).clearSession();

        return result;
    }

    @Override
    public List<Notice> parseData(JSONObject jsonResponse) {
        LogUtils.d(TAG, "parseData , jsonResponse : " + jsonResponse.toString());
        ArrayList<Notice> listNotice = new ArrayList<Notice>();

        try {
            if (jsonResponse.isNull("value")) return listNotice;

            JSONArray jsonArr = jsonResponse.getJSONArray("value");

            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObject = jsonArr.getJSONObject(i);

                Notice notice = new Notice();
                notice.setId(jsonObject.getInt("id"));
                notice.setTitle(jsonObject.getString("title"));
                notice.setContent(jsonObject.getString("content"));
                notice.setNoticeType(jsonObject.getInt("noticeType"));
                notice.setCreateDate(new Date(jsonObject.getLong("createDate")));
                notice.setUpdateDate(new Date(jsonObject.getLong("updateDate")));
                notice.setUserId(jsonObject.getInt("userId"));
                listNotice.add(notice);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return listNotice;
    }

    public synchronized List<NoticeObj> getAllNotice(Activity activity) {

        LogUtils.d(TAG, "getAllNotice start");

        NoticeDao noticeDao = DBHelper.getIstance(context).getDaoSession().getNoticeDao();

//        List<Notice> notices = noticeDao.loadAll();
        List<Notice> notices = noticeDao.queryBuilder().orderDesc(NoticeDao.Properties.UpdateDate).list();

        List<NoticeObj> noticeObjs = new ArrayList<NoticeObj>();
        LogUtils.d(TAG, "getAllNotice notices size : " + notices.size());

        for (int i = 0; i < notices.size(); i++) {
            Notice dbNotice = notices.get(i);

            NoticeObj noticeObj = new NoticeObj();
            noticeObj.setId(dbNotice.getId());
            noticeObj.setUserId(dbNotice.getUserId());
            noticeObj.setTitle(dbNotice.getTitle());
            noticeObj.setContent(dbNotice.getContent());
            noticeObj.setCreateDate(dbNotice.getCreateDate());
            noticeObj.setUpdateDate(dbNotice.getUpdateDate());
            noticeObj.setNoticeType(dbNotice.getNoticeType());
            noticeObj.setOpen(false);

            LogUtils.d(TAG, "getAllNotice , notice : " + i + " , data : " + noticeObj.toString());

            noticeObjs.add(noticeObj);
        }
        DBHelper.getIstance(context).clearSession();

        LogUtils.d(TAG, "getAllNotice , size : " + noticeObjs.size());
        return noticeObjs;
    }

    @Override
    public synchronized void updateDb(List<Notice> listT) {
        LogUtils.d(TAG, "updateDb start , listT size : " + listT.size());
        if (listT.size() > 0) {
            NoticeDao noticeDao = DBHelper.getIstance(context).getDaoSession().getNoticeDao();

            for (int i = 0; i < listT.size(); i++) {
                LogUtils.d(TAG, "updateDb notice update start ----- " + i + " : " + listT.get(i).toString());
                noticeDao.insertOrReplace(listT.get(i));
                LogUtils.d(TAG, "updateDb notice update finish ----- " + i + " : " + listT.get(i).toString());
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
