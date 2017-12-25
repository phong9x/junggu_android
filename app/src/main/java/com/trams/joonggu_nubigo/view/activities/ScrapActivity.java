package com.trams.joonggu_nubigo.view.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.dao.Field;
import com.trams.joonggu_nubigo.dao.Store;
import com.trams.joonggu_nubigo.dao.User;
import com.trams.joonggu_nubigo.dbmanager.DbFieldUpdate;
import com.trams.joonggu_nubigo.dbmanager.DbStoreUpdate;
import com.trams.joonggu_nubigo.dbmanager.DbUserUpdate;
import com.trams.joonggu_nubigo.dbmanager.UpdateDbItf;
import com.trams.joonggu_nubigo.network.WebServiceConfig;
import com.trams.joonggu_nubigo.utils.LogUtils;
import com.trams.joonggu_nubigo.view.adapters.ScrapAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 18/11/2015.
 */
public class ScrapActivity extends BaseActivity implements View.OnClickListener {
    private ListView lvScrap;
    private ScrapAdapter scrapAdapter;
    private static final String TAG = ScrapActivity.class.getName();
    private List<Field> facilities;
    private List<Store> stores;
    private ImageView imgBack;

    private int page = 1;
    boolean loadingMore = false;
    private boolean isStopLoadingMore = true;

    @Override
    protected int getLayout() {
        return R.layout.layout_scrap;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        lvScrap = (ListView) findViewById(R.id.lvScrap);
        imgBack = (ImageView) findViewById(R.id.img_scrap_back);
    }

    @Override
    protected void registerListener() {
        lvScrap.setAdapter(scrapAdapter);
        imgBack.setOnClickListener(this);
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        getAllField();
    }

    private void getAllField() {

        final DbFieldUpdate dbFieldUpdate = new DbFieldUpdate(ScrapActivity.this, WebServiceConfig.URL_UPDATE_FIELD);

        dbFieldUpdate.setOnResponseSuccess(new UpdateDbItf.OnResponseSuccess() {
            @Override
            public void onResponseSuccess() {

                facilities = dbFieldUpdate.getAllFields(ScrapActivity.this);
                updateUI();
            }
        });

        dbFieldUpdate.setOnResponseFail(new UpdateDbItf.OnResponseFail() {
            @Override
            public void onResponseFail() {

                facilities = dbFieldUpdate.getAllFields(ScrapActivity.this);
                updateUI();
            }
        });

        dbFieldUpdate.execute(true);
    }

    private void updateUI() {

        DbUserUpdate dbUserUpdate = new DbUserUpdate(this);
        User user = dbUserUpdate.getUserLogin();

        if (user != null) {
            String scrap = user.getScrap();

            ArrayList<Integer> listIds = new ArrayList<>();

            try {
                JSONArray jsonSrapArr = new JSONArray(scrap);

                for (int i = 0; i < jsonSrapArr.length(); i++) {
                    listIds.add((Integer) jsonSrapArr.get(i));
                }

                LogUtils.d(TAG, "Scrap Screen updateUI , listIds : " + listIds.toString());

                final DbStoreUpdate dbStoreUpdate = new DbStoreUpdate(ScrapActivity.this, WebServiceConfig.URL_UPDATE_STORE);
                stores = dbStoreUpdate.getStoreByListId(listIds);

                for (int i = 0; i < stores.size(); i++) {
                    LogUtils.d(TAG, "ScrapActivity , updateUI : " + stores.get(i).toString());
                }

                scrapAdapter = new ScrapAdapter(ScrapActivity.this, stores);
                lvScrap.setAdapter(scrapAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

//    private void getAllStoreScrapCallApi() {
//
//        Log.d(TAG,"getAllStoreScrapCallApi start");
//
//        final JSONObject jsonRequest = new JSONObject();
//
//        try {
////            fake data
////            jsonRequest.put("user_id", 70);
//            jsonRequest.put("user_id", PreferUtils.getUserId(ScrapActivity.this));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        NetworkUtils.postRequestVolley(ScrapActivity.this, WebServiceConfig.URL_UPDATE_SCRAP, jsonRequest, new NetworkUtils.RequestResponse() {
//            @Override
//            public void onResponse(final JSONObject jsonResponse) {
//                try {
//                    int code = jsonResponse.getInt("code");
//                    if (code == 200) {
//                        String msg = jsonResponse.getString("message");
//                        try {
//
//                            JSONArray jsonArr = jsonResponse.getJSONArray("value");
//
//                            ArrayList<Integer> listIds = new ArrayList<Integer>();
//
//                            for (int i = 0; i < jsonArr.length(); i++) {
//                                listIds.add(jsonArr.getInt(i));
//                                LogUtils.d(TAG, "getAllStoreScrapCallApi listIds , " + i + " : " + (Integer) jsonArr.get(i));
//                            }
//
//                            final DbStoreUpdate dbStoreUpdate = new DbStoreUpdate(ScrapActivity.this, WebServiceConfig.URL_UPDATE_STORE);
//                            stores = dbStoreUpdate.getStoreByListId(listIds);
//
//                            scrapAdapter = new ScrapAdapter(ScrapActivity.this, stores, hmField);
//                            lvScrap.setAdapter(scrapAdapter);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    } else {
//                        // login fail
////                        String msg = jsonResponse.getString("message");
////                        DialogUtils.showConfirmAlertDialog(ScrapActivity.this, msg, new DialogUtils.ConfirmDialogListener() {
////                            @Override
////                            public void onConfirmClick() {
////
////                            }
////                        });
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    ProgressDialogUtils.dismissProgressDialog();
//                    DialogUtils.showConfirmAlertDialog(ScrapActivity.this, ScrapActivity.this.getString(R.string.request_error_msg), new DialogUtils.ConfirmDialogListener() {
//                        @Override
//                        public void onConfirmClick() {
//
//                        }
//                    });
//                }
//            }
//        }, new NetworkUtils.RequestError() {
//            @Override
//            public void onError() {
//
//            }
//        }, true);
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_scrap_back:
                finish();
                break;
        }
    }
}
