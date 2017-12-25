package com.trams.joonggu_nubigo.view.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.dao.Accessibility;
import com.trams.joonggu_nubigo.dbmanager.DbSettingUpdate;
import com.trams.joonggu_nubigo.dbmanager.UpdateDbItf;
import com.trams.joonggu_nubigo.network.WebServiceConfig;
import com.trams.joonggu_nubigo.utils.LogUtils;
import com.trams.joonggu_nubigo.view.adapters.SettingAdapter;
import com.trams.joonggu_nubigo.view.customview.CustomTextViewNomal;

import java.util.List;

/**
 * Created by Administrator on 18/11/2015.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener{
    private ListView lvSetting;
    private Button btnSave;
    private List<Accessibility> listSettingObj;
    private static final String TAG = SettingActivity.class.getName();
    private CustomTextViewNomal tvHeader;
    private SettingAdapter settingAdapter;
    private DbSettingUpdate dbSettingUpdate;
    private ImageView imgBack;

    @Override
    protected int getLayout() {
        return R.layout.layout_setting;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        lvSetting = (ListView) findViewById(R.id.lvMypageSetting);
        btnSave = (Button) findViewById(R.id.btn_setting_save);
        tvHeader = (CustomTextViewNomal) findViewById(R.id.tvSettingHeader);
        imgBack = (ImageView) findViewById(R.id.img_setting_back);
    }

    @Override
    protected void registerListener() {
        btnSave.setOnClickListener(this);
        imgBack.setOnClickListener(this);

    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {

        dbSettingUpdate = new DbSettingUpdate(SettingActivity.this, WebServiceConfig.URL_UPDATE_SETTING);

//        listSettingObj = dbSettingUpdate.getAllAccessbility(getActivity());

        dbSettingUpdate.setOnResponseSuccess(new UpdateDbItf.OnResponseSuccess() {
            @Override
            public void onResponseSuccess() {
                listSettingObj = dbSettingUpdate.getAllAccessbility(SettingActivity.this);
                settingAdapter = new SettingAdapter(SettingActivity.this,listSettingObj );
                lvSetting.setAdapter(settingAdapter);
            }
        });

        dbSettingUpdate.setOnResponseFail(new UpdateDbItf.OnResponseFail() {
            @Override
            public void onResponseFail() {
                listSettingObj = dbSettingUpdate.getAllAccessbility(SettingActivity.this);
                settingAdapter = new SettingAdapter(SettingActivity.this, listSettingObj);
                lvSetting.setAdapter(settingAdapter);
            }
        });

        dbSettingUpdate.execute(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_setting_save:
                updateDb();
                break;
            case R.id.img_setting_back:
                finish();
                break;

        }
    }

    private void updateDb() {

        for (int i = 0; i < listSettingObj.size(); i++) {
            LogUtils.d(TAG, "Setting Obj " + i + " : " + listSettingObj.get(i).toString());
        }

        dbSettingUpdate.updateDb(listSettingObj);

        //after update go to mypage
        finish();
    }
}
