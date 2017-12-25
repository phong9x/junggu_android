package com.trams.joonggu_nubigo.view.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.common.Prefs;
import com.trams.joonggu_nubigo.dao.Category;
import com.trams.joonggu_nubigo.dbmanager.CategoryManager;
import com.trams.joonggu_nubigo.dbmanager.DbCategoryUpdate;
import com.trams.joonggu_nubigo.dbmanager.UpdateDbItf;
import com.trams.joonggu_nubigo.network.WebServiceConfig;
import com.trams.joonggu_nubigo.view.adapters.AreaAdapter;

import java.util.List;

/**
 * Created by Administrator on 28/10/2015.
 */
public class AreaFragment extends BaseFragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private static final String TAG = AreaFragment.class.getSimpleName();

    public static final String CATEGORY_ID = "CATEGORY_ID";

    public static final String CATEGORY_NAME = "CATEGORY_NAME";


    GridView gridView;
    AreaAdapter adapter;
    View closeTipBtn, tipLayout;
    List<Category> data;

    Prefs prefs;
    private DbCategoryUpdate dbCategoryUpdate;

    @Override
    protected int getLayout() {
        return R.layout.fragment_area;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        setupHeader(false, 0, false, "", "", true, false, true, false, "");
        gridView = (GridView) view.findViewById(R.id.gv_area);
        closeTipBtn = view.findViewById(R.id.btn_area_cross);
        tipLayout = view.findViewById(R.id.area_tip_container);
    }

    @Override
    protected void registerListener() {
        closeTipBtn.setOnClickListener(this);
        gridView.setOnItemClickListener(this);
        headerSearchBtn.setOnClickListener(this);
    }

    protected void initVaribles() {
        showBottomTabBar(true);
        updateDb();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void refreshAdapter() {
        if (data != null && getActivity() != null) {
            if (adapter == null) {
                adapter = new AreaAdapter(getActivity(), data);
                gridView.setAdapter(adapter);
            } else {
                gridView.setAdapter(null);
                adapter.clear();
                adapter.addAll(data);
                adapter.notifyDataSetChanged();
                gridView.setAdapter(adapter);
            }
        }
    }

    @Override
    public void onStop() {
        CategoryManager.getInstance(getActivity()).closeDbConnections();

        super.onStop();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AreaAdapter adapter = (AreaAdapter) parent.getAdapter();
        Category target = adapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putLong(CATEGORY_ID, target.getId());
        bundle.putString(CATEGORY_NAME, target.getCatName());
        bundle.putString(AreaStoreFragment.AREA_STORE_TYPE, AreaStoreFragment.AREA_STORE_NORMAL);

        adapter.setSelectedIdx(position);
        adapter.notifyDataSetChanged();
        openFragment(R.id.tabContent, AreaStoreFragment.class, bundle, true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_search_btn:
                showPopup();
                setAutoFocusPopupSearch();
                break;
            case R.id.btn_area_cross:
                tipLayout.setVisibility(View.GONE);
                break;
        }
    }

    void updateDb() {
        dbCategoryUpdate = new DbCategoryUpdate(getActivity(), WebServiceConfig.URL_UPDATE_CATEGORY);

        dbCategoryUpdate.setOnResponseSuccess(new UpdateDbItf.OnResponseSuccess() {
            @Override
            public void onResponseSuccess() {
                data = dbCategoryUpdate.getAllParent(getActivity());
                refreshAdapter();
            }
        });

        dbCategoryUpdate.setOnResponseFail(new UpdateDbItf.OnResponseFail() {
            @Override
            public void onResponseFail() {
                data = dbCategoryUpdate.getAllParent(getActivity());
                refreshAdapter();
            }
        });

        dbCategoryUpdate.execute(true);

    }
}
