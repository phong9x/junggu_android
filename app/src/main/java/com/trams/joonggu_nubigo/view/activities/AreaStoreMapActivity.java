package com.trams.joonggu_nubigo.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapCompassManager;
import com.nhn.android.maps.NMapContext;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.mapviewer.overlay.NMapMyLocationOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.common.AppGlobal;
import com.trams.joonggu_nubigo.common.Constant;
import com.trams.joonggu_nubigo.dao.Accessibility;
import com.trams.joonggu_nubigo.dao.Store;
import com.trams.joonggu_nubigo.dbmanager.AccessibilityManager;
import com.trams.joonggu_nubigo.dbmanager.StoreManager;
import com.trams.joonggu_nubigo.maps.NMapPOIflagType;
import com.trams.joonggu_nubigo.maps.NMapViewerResourceProvider;
import com.trams.joonggu_nubigo.utils.LogUtils;
import com.trams.joonggu_nubigo.view.customview.CustomTextViewNomal;
import com.trams.joonggu_nubigo.view.customview.StoreView;
import com.trams.joonggu_nubigo.view.fragments.AreaStoreFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dang on 18/11/2015.
 */
public class AreaStoreMapActivity extends NMapActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getName();
    //    ImageView imgCompany;
//    ImageView imgCompanyTypeIcon;
//    CustomTextViewNomal companyName;
//    RatingBar star;
//    CustomTextViewNomal companyType, headerTitleTv, headerCountTv;
    CustomTextViewNomal headerTitleTv, headerCountTv;
//    private DisplayImageOptions mDisplayImageOptions;

    private NMapView mMapView;

    private NMapContext mMapContext;
    private NMapController mMapController;
    private NMapViewerResourceProvider mMapViewerResourceProvider;
    private NMapOverlayManager mOverlayManager;
    private NMapLocationManager mMapLocationManager;
    private NMapCompassManager mMapCompassManager;
    private NMapMyLocationOverlay mMyLocationOverlay;
    //    CheckBox access0, access1, access2, access3, access4, access5;
    List<Store> listStore = null;
    String type = null;

    Store currStore = new Store();

    View headerNormalContainer, headerSearchContainer, headerSearchBackBtn, headerBackBtn;

    private ImageView btnMyLocation, btnZoomIn, btnZoomOut, btnZoomBg;

    List<Long> filterAccess = new ArrayList<>();

    private StoreView storeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_store_map);
        setupViews();
        initVaribles();

    }

    void setupViews() {
        mMapView = (NMapView) findViewById(R.id.area_store_map_view);
        mMapView.setApiKey(Constant.NAVER_MAP_API_KEY);
        mMapView.setClickable(true);
        mMapController = mMapView.getMapController();
        mMapView.setBuiltInZoomControls(false, null);
        mMapViewerResourceProvider = new NMapViewerResourceProvider(this);
        mOverlayManager = new NMapOverlayManager(this, mMapView, mMapViewerResourceProvider);

        storeView = (StoreView) findViewById(R.id.store_view);

//        imgCompany = (ImageView) findViewById(R.id.area_store_map_img_company);
//        imgCompanyTypeIcon = (ImageView) findViewById(R.id.img_company_type_icon);
//        companyName = (CustomTextViewNomal) findViewById(R.id.area_store_map_tv_company_name);
//        star = (RatingBar) findViewById(R.id.area_store_map_star);
//        companyType = (CustomTextViewNomal) findViewById(R.id.area_store_map_tv_company_type);
//        access0 = (CheckBox) findViewById(R.id.access0);
//        access1 = (CheckBox) findViewById(R.id.access1);
//        access2 = (CheckBox) findViewById(R.id.access2);
//        access3 = (CheckBox) findViewById(R.id.access3);
//        access4 = (CheckBox) findViewById(R.id.access4);
//        access5 = (CheckBox) findViewById(R.id.access5);
        headerNormalContainer = findViewById(R.id.area_store_map_header_container);
        headerSearchContainer = findViewById(R.id.area_store_map_search_header_container);
        headerSearchBackBtn = findViewById(R.id.area_store_map_search_header_back_btn);

        btnMyLocation = (ImageView) findViewById(R.id.btn_my_location_map_fragment);
        btnMyLocation.setOnClickListener(this);

        btnZoomIn = (ImageView) findViewById(R.id.btn_zoom_in_map_fragment);
        btnZoomIn.setOnClickListener(this);

        btnZoomOut = (ImageView) findViewById(R.id.btn_zoom_out_map_fragment);
        btnZoomOut.setOnClickListener(this);

        btnZoomBg = (ImageView) findViewById(R.id.img_bg_zoom);
        btnZoomBg.setOnClickListener(this);

        headerSearchBackBtn.setOnClickListener(this);
//        imgCompany.setOnClickListener(this);
        headerBackBtn = findViewById(R.id.header_back_btn);
        headerBackBtn.setOnClickListener(this);
        headerTitleTv = (CustomTextViewNomal) findViewById(R.id.header_title_text_view);
        headerCountTv = (CustomTextViewNomal) findViewById(R.id.header_count_text_view);
    }

    void initVaribles() {
        initData();
//        mDisplayImageOptions = new DisplayImageOptions.Builder()
//                .cacheInMemory(true)
//                .cacheOnDisk(true)
//                .considerExifParams(true)
//                .build();
    }

//    void selected(String imageUrls, String companyName, String star, String companyType, String accessibilityJson) {
//        ImageLoader.getInstance().displayImage(StoreParser.getPresentImageUrl(imageUrls), this.imgCompany, mDisplayImageOptions);
//        this.companyName.setText(companyName);
//        this.star.setRating(StoreParser.getGrade(star));
//        this.companyType.setText(StoreParser.getFieldList(companyType));
//
//        String mainType = (StoreParser.getFieldList(companyType).split(StoreParser.FIELD_PATTERN, -1))[0];
//
//        if (mainType.equals("먹거리")) {
//            imgCompanyTypeIcon.setImageResource(R.drawable.field_food);
//        } else if (mainType.equals("관광지")) {
//            imgCompanyTypeIcon.setImageResource(R.drawable.field_sight);
//        } else if (mainType.equals("숙박")) {
//            imgCompanyTypeIcon.setImageResource(R.drawable.field_accommodation);
//        } else if (mainType.equals("쇼핑")) {
//            imgCompanyTypeIcon.setImageResource(R.drawable.field_shopping);
//        } else if (mainType.equals("생활")) {
//            imgCompanyTypeIcon.setImageResource(R.drawable.field_living);
//        }
//
//        CheckBox[] checkBoxes = new CheckBox[]{access0, access1, access2, access3, access4, access5};
//        StoreParser.setAccessibilityList(accessibilityJson, checkBoxes);
//    }

    @Override
    protected void onStop() {
        super.onStop();
        StoreManager.getInstance(this).closeDbConnections();
    }

    void initData() {
        Intent bundle = getIntent();
        Long regionId = bundle.getLongExtra(AreaStoreFragment.REGION_ID, 0);
        String regionName = bundle.getStringExtra(AreaStoreFragment.REGION_NAME);
        String enteredText = bundle.getStringExtra(AreaStoreFragment.AREA_STORE_SEARCH_TEXT);
        type = bundle.getStringExtra(AreaStoreFragment.AREA_STORE_TYPE);
        // accessibility filter
        List<Accessibility> accessibilities = AccessibilityManager.getInstance(this).getAccessibilities();
        filterAccess.clear();
        for (Accessibility accessibility :
                accessibilities) {
            if (accessibility.getSelected() == 1) {
                filterAccess.add(accessibility.getId());
            }
        }

        if (type.equals(AreaStoreFragment.AREA_STORE_NORMAL)) {
            headerNormalContainer.setVisibility(View.VISIBLE);
            headerSearchContainer.setVisibility(View.GONE);
            headerTitleTv.setText(regionName);
            listStore = StoreManager.getInstance(this).getStores(filterAccess, regionId);
        } else if (type.equals(AreaStoreFragment.AREA_STORE_SEARCH)) {
            headerNormalContainer.setVisibility(View.GONE);
            headerSearchContainer.setVisibility(View.VISIBLE);
            listStore = StoreManager.getInstance(this).getListSearch(enteredText, filterAccess);
            headerCountTv.setText("(" + listStore.size() + ")");
        }
        // gen pins
        // test
        /*listStore.clear();
        Store storeTest = new Store();
        storeTest.setName("TEST");
        storeTest.setLatitude("37.365608");
        storeTest.setLongitude("127.107232");

        Store storeTest1 = new Store();
        storeTest1.setName("TEST1");
        storeTest1.setLatitude("37.365708");
        storeTest1.setLongitude("127.107232");

        Store storeTest2 = new Store();
        storeTest2.setName("TEST2");
        storeTest2.setLatitude("37.365808");
        storeTest2.setLongitude("127.107232");
        listStore.add(storeTest);
        listStore.add(storeTest1);
        listStore.add(storeTest2);*/
        pins(listStore);

        Store top = listStore.get(0);
//        selected(top.getImageBaseAttach(),
//                top.getName(),
//                top.getGrade(),
//                top.getFieldList(),
//                top.getAccessibilityList());

        storeView.updateView(top);
        currStore = top;
    }


    void pins(List<Store> stores) {
        // set POI data
        try {
            NMapPOIdata poiData = new NMapPOIdata(stores.size(), mMapViewerResourceProvider);
            poiData.beginPOIdata(stores.size());
            for (int i = 0; i < stores.size(); i++) {
                Store store = stores.get(i);
                poiData.addPOIitem(Double.parseDouble(store.getLongitude()), Double.parseDouble(store.getLatitude()), store.getName(), NMapPOIflagType.DEFAULT, 0);
//                poiData.addPOIitem(Double.parseDouble(store.getLongitude()), Double.parseDouble(store.getLatitude()), store.getName(), NaverMapUtils.getMarkerId(store), 0);
            }

//            if(poiData != null && stores.size() > 0) poiData.getPOIitem(0).setMarkerId(NMapPOIflagType.FOCUS);

            poiData.endPOIdata();
            NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);
            poiDataOverlay.showAllPOIdata(0);
            // create POI data overlay

            // set event listener to the overlay
            poiDataOverlay.setOnStateChangeListener(onPOIdataStateChangeListener);

            if (stores != null && stores.size() > 0)
                poiDataOverlay.setLastFocusedIndex(0);

        } catch (NumberFormatException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /* POI data State Change Listener*/
    private final NMapPOIdataOverlay.OnStateChangeListener onPOIdataStateChangeListener = new NMapPOIdataOverlay.OnStateChangeListener() {

        @Override
        public void onFocusChanged(NMapPOIdataOverlay nMapPOIdataOverlay, NMapPOIitem nMapPOIitem) {
            if (nMapPOIitem != null) {
                String lat = nMapPOIitem.getPoint().getLatitude() + "";
                String lon = nMapPOIitem.getPoint().getLongitude() + "";
                for (int i = 0; i < listStore.size(); i++) {
                    Store store = listStore.get(i);
                    if (lat.equals(store.getLatitude()) && lon.equals(store.getLongitude())) {
//                        selected(store.getImageBaseAttach(),
//                                store.getName(),
//                                store.getGrade(),
//                                store.getFieldList(),
//                                store.getAccessibilityList());
                        storeView.updateView(store);
                        currStore = store;
                    }
                }
            }
        }

        @Override
        public void onCalloutClick(NMapPOIdataOverlay poiDataOverlay, NMapPOIitem item) {
            LogUtils.d(TAG, "onCalloutClick(): NGeoPoint Id =  " + item.getId());
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_back_btn:
            case R.id.area_store_map_search_header_back_btn:
                finish();
                break;
            case R.id.area_store_map_img_company:
                AppGlobal.sStore = currStore;
                Intent intent = new Intent(this, AreaStoreDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_my_location_map_fragment:
                startMyLocation();
                break;
            case R.id.btn_zoom_in_map_fragment:
                doZoomIn();
                break;
            case R.id.btn_zoom_out_map_fragment:
                doZoomOut();
                break;
            case R.id.img_bg_zoom:
                //done nothing
                //set to not click through map
                break;
        }
    }

    private void startMyLocation() {

        LogUtils.d(TAG, "startMyLocation start");

        if (mMyLocationOverlay != null) {
            LogUtils.d(TAG, "startMyLocation mMyLocationOverlay != null");

            if (!mOverlayManager.hasOverlay(mMyLocationOverlay)) {
                LogUtils.d(TAG, "startMyLocation !mOverlayManager.hasOverlay(mMyLocationOverlay)");
                mOverlayManager.addOverlay(mMyLocationOverlay);
            }

            if (mMapLocationManager.isMyLocationEnabled()) {
                LogUtils.d(TAG, "startMyLocation mMapLocationManager.isMyLocationEnabled()");

                if (!mMapView.isAutoRotateEnabled()) {
                    LogUtils.d(TAG, "startMyLocation !mMapView.isAutoRotateEnabled()");

                    mMyLocationOverlay.setCompassHeadingVisible(true);

                    mMapCompassManager.enableCompass();

                    LogUtils.d(TAG, "startMyLocation enable compass");

//                    mMapView.setAutoRotateEnabled();

//                    mMapContainerView.requestLayout();
                } else {
                    LogUtils.d(TAG, "startMyLocation mMapView.isAutoRotateEnabled()");
                    stopMyLocation();
                }

                mMapView.postInvalidate();
            } else {
                LogUtils.d(TAG, "startMyLocation !!! mMapLocationManager.isMyLocationEnabled()");

                boolean isMyLocationEnabled = mMapLocationManager.enableMyLocation(true);
                if (!isMyLocationEnabled) {
                    LogUtils.d(TAG, "startMyLocation !isMyLocationEnabled");
                    Toast.makeText(this, "Please enable a My Location source in system settings",
                            Toast.LENGTH_LONG).show();

                    Intent goToSettings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(goToSettings);

                    return;
                } else {
                    LogUtils.d(TAG, "startMyLocation isMyLocationEnabled ...");
                }
            }
        }
    }

    private void stopMyLocation() {
        LogUtils.d(TAG, "stopMyLocation start");

        if (mMyLocationOverlay != null) {
            mMapLocationManager.disableMyLocation();
        }
    }

    private void doZoomIn() {
        mMapController.zoomIn();
    }

    private void doZoomOut() {
        mMapController.zoomOut();
    }
}