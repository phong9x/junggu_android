package com.trams.joonggu_nubigo.dbmanager;

import android.content.Context;

import com.trams.joonggu_nubigo.network.NetworkUtils;
import com.trams.joonggu_nubigo.utils.LogUtils;
import com.trams.joonggu_nubigo.utils.PreferUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 10/11/2015.
 */
public abstract class DbHelperUpdate<T, TDao> {
    private T t;
    private TDao tDao;
    private Context context;
    private String url;
    private static final String TAG = DbHelperUpdate.class.getName();

    public DbHelperUpdate(Context _context, String _url) {
        this.context = _context;
        this.url = _url;
    }

    public abstract long getLastUpdate();

    public void execute(final boolean isDismissProgressDialog) {

        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("updateDate", String.valueOf(getLastUpdate()));
            jsonRequest.put("userId", PreferUtils.getUserId(context));

            LogUtils.d(TAG,"postRequestVolley , jsonRequest : " + jsonRequest.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetworkUtils.postUpdateDataVolley(context, url, jsonRequest, new NetworkUtils.RequestResponse() {
            @Override
            public void onResponse(JSONObject jsonResponse) {
                LogUtils.d(TAG, "postRequestVolley , onResponse : " + jsonResponse.toString());
                if(context != null)
                updateDb(parseData(jsonResponse));
            }
        }, new NetworkUtils.RequestError() {
            @Override
            public void onError() {
                LogUtils.d(TAG, "postRequestVolley , onError : ERROR");
                getAllDb();
            }
        },isDismissProgressDialog);
    }
    ;

    public abstract List<T> parseData(JSONObject jsonResponse);

    public abstract void updateDb(List<T> listT);
    public abstract void getAllDb();


}
