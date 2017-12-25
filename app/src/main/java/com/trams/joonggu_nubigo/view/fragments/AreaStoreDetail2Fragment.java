package com.trams.joonggu_nubigo.view.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.common.AppGlobal;
import com.trams.joonggu_nubigo.objects.AreaStoreDetailPage2Obj;
import com.trams.joonggu_nubigo.parsers.StoreParser;
import com.trams.joonggu_nubigo.view.adapters.StoreDetailPage2Adapter;

import java.util.ArrayList;

/**
 * Created by zin9x on 11/10/2015.
 */
public class AreaStoreDetail2Fragment extends BaseFragment {
    ListView listView;
    StoreDetailPage2Adapter adapter;
    ArrayList<AreaStoreDetailPage2Obj> areaStoreDetailPage2Objs = null;

    private static AreaStoreDetail2Fragment instance;

    public static AreaStoreDetail2Fragment getInstance() {
        if (instance == null) {
            instance = new AreaStoreDetail2Fragment();
        }
        return instance;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_area_store_detail_2;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        listView = (ListView) view.findViewById(R.id.area_store_detail_page2_lv);
    }

    @Override
    protected void registerListener() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initVaribles() {
        areaStoreDetailPage2Objs = StoreParser.getDataExtendDetail2(AppGlobal.sStore.getImageExtendAttach());
        adapter = new StoreDetailPage2Adapter(getActivity(), areaStoreDetailPage2Objs);
        listView.setAdapter(adapter);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
