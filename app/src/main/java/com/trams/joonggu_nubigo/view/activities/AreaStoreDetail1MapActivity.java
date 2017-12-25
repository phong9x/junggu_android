package com.trams.joonggu_nubigo.view.activities;


import android.os.Bundle;
import android.view.View;

import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.dbmanager.DbCategoryUpdate;
import com.trams.joonggu_nubigo.dbmanager.UpdateDbItf;
import com.trams.joonggu_nubigo.network.WebServiceConfig;
import com.trams.joonggu_nubigo.view.fragments.StoreDetail1MapFragment;

/**
 * Created by Dang on 17/11/2015.
 */
public class AreaStoreDetail1MapActivity extends BaseActivity implements View.OnClickListener {
    private View backBtn;

    @Override
    protected int getLayout() {
        return R.layout.activity_area_store_detail_1_map;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        backBtn = findViewById(R.id.imgLeft);
    }

    @Override
    protected void registerListener() {
        backBtn.setOnClickListener(this);
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        openFragment(R.id.tabContent, StoreDetail1MapFragment.class, true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgLeft:
                finish();
                break;
        }
    }
}