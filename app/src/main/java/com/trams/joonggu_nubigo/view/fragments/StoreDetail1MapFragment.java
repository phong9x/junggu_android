package com.trams.joonggu_nubigo.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.nhn.android.maps.NMapCompassManager;
import com.nhn.android.maps.NMapContext;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapMyLocationOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.common.AppGlobal;
import com.trams.joonggu_nubigo.common.Constant;
import com.trams.joonggu_nubigo.dao.Store;
import com.trams.joonggu_nubigo.maps.NMapPOIflagType;
import com.trams.joonggu_nubigo.maps.NMapViewerResourceProvider;
import com.trams.joonggu_nubigo.utils.LogUtils;
import com.trams.joonggu_nubigo.utils.NaverMapUtils;
import com.trams.joonggu_nubigo.view.customview.CustomTextViewBold;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dang on 17/11/2015.
 */
public class StoreDetail1MapFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = StoreDetail1MapFragment.class.getSimpleName();
    private NMapView mMapView;

    private NMapContext mMapContext;
    private NMapController mMapController;
    private NMapViewerResourceProvider mMapViewerResourceProvider;
    private NMapOverlayManager mOverlayManager;
    private NMapLocationManager mMapLocationManager;
    private NMapCompassManager mMapCompassManager;
    private NMapMyLocationOverlay mMyLocationOverlay;
    View checkNaverBtn;
    double lat = 0, lon = 0;

    private ImageView btnMyLocation, btnZoomIn, btnZoomOut, btnZoomBg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapContext = new NMapContext(super.getActivity());
        mMapContext.onCreate();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_area_store_detail_1_map;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        btnMyLocation = (ImageView) view.findViewById(R.id.btn_my_location_map_fragment);
        btnMyLocation.setOnClickListener(this);

        btnZoomIn = (ImageView) view.findViewById(R.id.btn_zoom_in_map_fragment);
        btnZoomIn.setOnClickListener(this);

        btnZoomOut = (ImageView) view.findViewById(R.id.btn_zoom_out_map_fragment);
        btnZoomOut.setOnClickListener(this);

        btnZoomBg = (ImageView) view.findViewById(R.id.img_bg_zoom);
        btnZoomBg.setOnClickListener(this);

    }

    @Override
    protected void registerListener() {

    }

    @Override
    protected void initVaribles() {
        initData();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkNaverBtn = getActivity().findViewById(R.id.imgRight);
        checkNaverBtn.setOnClickListener(this);

        mMapView = (NMapView) getView().findViewById(R.id.area_store_detail_1_mv);
        mMapContext.setupMapView(mMapView);
        setupMapView();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapContext.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapContext.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapContext.onPause();
    }

    @Override
    public void onStop() {
        mMapContext.onStop();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapContext.onDestroy();
    }

    void setupMapView() {
        // set a registered API key for Open MapViewer Library
        mMapView.setApiKey(Constant.NAVER_MAP_API_KEY);

        // initialize map view
        mMapView.setClickable(true);
        mMapView.setEnabled(true);
        mMapView.setFocusable(true);
        mMapView.setFocusableInTouchMode(true);
        mMapView.requestFocus();
        mMapView.setDrawingCacheEnabled(true);
        mMapView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);

        // use map controller to zoom in/out, pan and set map center, zoom level etc.
        mMapController = mMapView.getMapController();

        // use built in zoom controls
        NMapView.LayoutParams lp = new NMapView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, NMapView.LayoutParams.BOTTOM_RIGHT);
        mMapView.setBuiltInZoomControls(false, lp);

        mMapViewerResourceProvider = new NMapViewerResourceProvider(getActivity());
        // create overlay manager
        mOverlayManager = new NMapOverlayManager(getActivity(), mMapView, mMapViewerResourceProvider);

        // location manager
        mMapLocationManager = new NMapLocationManager(getActivity());
        mMapLocationManager.setOnLocationChangeListener(onMyLocationChangeListener);

        // compass manager
        mMapCompassManager = new NMapCompassManager(getActivity());

        // create my location overlay
        mMyLocationOverlay = mOverlayManager.createMyLocationOverlay(mMapLocationManager, mMapCompassManager);

    }

    /* MyLocation Listener */
    private final NMapLocationManager.OnLocationChangeListener onMyLocationChangeListener = new NMapLocationManager.OnLocationChangeListener() {

        @Override
        public boolean onLocationChanged(NMapLocationManager locationManager, NGeoPoint myLocation) {
            if (mMapController != null) {
                mMapController.animateTo(myLocation);
            }
            return true;
        }

        @Override
        public void onLocationUpdateTimeout(NMapLocationManager nMapLocationManager) {

        }

        @Override
        public void onLocationUnavailableArea(NMapLocationManager nMapLocationManager, NGeoPoint nGeoPoint) {

        }
    };

    void initData() {
        setTitle(AppGlobal.sStore.getName());
        lat = Double.parseDouble(AppGlobal.sStore.getLatitude());
        lon = Double.parseDouble(AppGlobal.sStore.getLongitude());
        List<Store> stores = new ArrayList<>();
        stores.add(AppGlobal.sStore);
        pins(stores);
    }

    void setTitle(String title) {
        CustomTextViewBold lblHeader = (CustomTextViewBold) getActivity().findViewById(R.id.lblHeader);
        lblHeader.setText(title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgRight:
                NaverMapUtils.openNaverMap(getActivity(), lon, lat);
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
                    Toast.makeText(getActivity(), "Please enable a My Location source in system settings",
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

//            if (mMapView.isAutoRotateEnabled()) {
//                mMyLocationOverlay.setCompassHeadingVisible(false);
//
//                mMapCompassManager.disableCompass();
//
//                mMapView.setAutoRotateEnabled(false, false);
//
////                mMapContainerView.requestLayout();
//            }
        }
    }

    private void doZoomIn() {
        mMapController.zoomIn();
    }

    private void doZoomOut() {
        mMapController.zoomOut();
    }

    void pins(List<Store> stores) {
        try {
            NMapPOIdata poiData = new NMapPOIdata(stores.size(), mMapViewerResourceProvider);
            poiData.beginPOIdata(stores.size());
            for (int i = 0; i < stores.size(); i++) {
                Store store = stores.get(i);
//                poiData.addPOIitem(Double.parseDouble(store.getLongitude()), Double.parseDouble(store.getLatitude()), store.getName(), NaverMapUtils.getMarkerId(store), 0);
                poiData.addPOIitem(Double.parseDouble(store.getLongitude()), Double.parseDouble(store.getLatitude()), store.getName(), NMapPOIflagType.DEFAULT, 0);
            }
            poiData.endPOIdata();
            NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);
            poiDataOverlay.showAllPOIdata(0);

            if (stores != null && stores.size() > 0)
                poiDataOverlay.setLastFocusedIndex(0);

        } catch (NumberFormatException e) {
            Log.e(TAG, e.getMessage());
        }
    }

}
