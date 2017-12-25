package com.trams.joonggu_nubigo.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nhn.android.maps.NMapCompassManager;
import com.nhn.android.maps.NMapContext;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.mapviewer.overlay.NMapMyLocationOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.common.Constant;
import com.trams.joonggu_nubigo.dao.Accessibility;
import com.trams.joonggu_nubigo.dao.Category;
import com.trams.joonggu_nubigo.dao.Field;
import com.trams.joonggu_nubigo.dao.Store;
import com.trams.joonggu_nubigo.dbmanager.DbCategoryUpdate;
import com.trams.joonggu_nubigo.dbmanager.DbFieldUpdate;
import com.trams.joonggu_nubigo.dbmanager.DbSettingUpdate;
import com.trams.joonggu_nubigo.dbmanager.DbStoreUpdate;
import com.trams.joonggu_nubigo.dbmanager.UpdateDbItf;
import com.trams.joonggu_nubigo.maps.NMapPOIflagType;
import com.trams.joonggu_nubigo.maps.NMapViewerResourceProvider;
import com.trams.joonggu_nubigo.network.WebServiceConfig;
import com.trams.joonggu_nubigo.utils.LogUtils;
import com.trams.joonggu_nubigo.utils.ProgressDialogUtils;
import com.trams.joonggu_nubigo.view.adapters.MapAccessbilityAdapter;
import com.trams.joonggu_nubigo.view.adapters.MapCategoryAdapter;
import com.trams.joonggu_nubigo.view.adapters.MapFieldAdapter;
import com.trams.joonggu_nubigo.view.customview.CustomTextViewBold;
import com.trams.joonggu_nubigo.view.customview.HorizontalListView;
import com.trams.joonggu_nubigo.view.customview.StoreViewMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Dang on 11/11/2015.
 */
public class MapFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = MapFragment.class.getName();

    private HorizontalListView lvMapField;
    private MapFieldAdapter mapFieldAdapter;

    private HorizontalListView lvMapAccessbility;
    private MapAccessbilityAdapter mapAccessbilityAdapter;

    private GridView grCategory;
    private MapCategoryAdapter mapCategoryAdapter;

    private DbCategoryUpdate dbCategoryUpdate;
    private DbFieldUpdate dbFieldUpdate;
    private DbSettingUpdate dbSettingUpdate;
    private DbStoreUpdate dbStoreUpdate;

    private List<Category> categories;
    private List<Field> fields;
    private List<Accessibility> accessibilities = new ArrayList<>();

    private int countRequestTask = 4;
    private int categorySelected = 0;
    private int fieldSelected = 0;
    private String accessbilitysSelected = "[\"1\",\"2\",\"3\",\"4\",\"5\",\"6\"]";

    private NMapView mMapView;
    private NMapContext mMapContext;
    private NMapController mMapController;
    private NMapViewerResourceProvider mMapViewerResourceProvider;
    private NMapOverlayManager mOverlayManager;
    private NMapLocationManager mMapLocationManager;
    private NMapCompassManager mMapCompassManager;
    private NMapMyLocationOverlay mMyLocationOverlay;
//    private RelativeLayout layoutMap;

    private static StoreViewMap storeView;
    private StoreViewDisplayTask storeViewDisplayTask;
    private HashMap<Integer, String> hmFiled = new HashMap<>();

    private ImageView btnMyLocation, btnZoomIn, btnZoomOut, btnZoomBg;
    private static final boolean DEBUG = true;
    private ImageView imgCategory, imgSearch;

    private CustomTextViewBold tvCategory;
    private RelativeLayout layoutCategory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapContext = new NMapContext(super.getActivity());
        mMapContext.onCreate();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMapView = (NMapView) getView().findViewById(R.id.n_map_view);
        mMapContext.setupMapView(mMapView);
        setupMapView();
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

        mMapView.setOnMapStateChangeListener(onMapViewStateChangeListener);

        // use map controller to zoom in/out, pan and set map center, zoom level etc.
        mMapController = mMapView.getMapController();

        mMapView.executeNaverMap();

        mMapViewerResourceProvider = new NMapViewerResourceProvider(getActivity());
        // create overlay manager
        mOverlayManager = new NMapOverlayManager(getActivity(), mMapView, mMapViewerResourceProvider);

        // location manager
        mMapLocationManager = new NMapLocationManager(getActivity());
//        mMapLocationManager.setOnLocationChangeListener(onMyLocationChangeListener);
        mMapLocationManager.isMyLocationEnabled();
        mMapLocationManager.enableMyLocation(true);
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
        public void onLocationUpdateTimeout(NMapLocationManager locationManager) {

            // stop location updating
            //			Runnable runnable = new Runnable() {
            //				public void run() {
            //					stopMyLocation();
            //				}
            //			};
            //			runnable.run();

//            Toast.makeText(getActivity(), "Your current location is temporarily unavailable.", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onLocationUnavailableArea(NMapLocationManager locationManager, NGeoPoint myLocation) {

            Toast.makeText(getActivity(), "Your current location is unavailable area.", Toast.LENGTH_LONG).show();

//            stopMyLocation();
        }

    };

    /* MapView State Change Listener*/
    private final NMapView.OnMapStateChangeListener onMapViewStateChangeListener = new NMapView.OnMapStateChangeListener() {

        @Override
        public void onMapInitHandler(NMapView mapView, NMapError errorInfo) {

            if (errorInfo == null) { // success
                // restore map view state such as map center position and zoom level.
//                restoreInstanceState();

            } else { // fail
                Log.e(TAG, "onFailedToInitializeWithError: " + errorInfo.toString());

//                Toast.makeText(getActivity(), errorInfo.toString(), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onAnimationStateChange(NMapView mapView, int animType, int animState) {
            if (Constant.DEBUG) {
                Log.i(TAG, "onAnimationStateChange: animType=" + animType + ", animState=" + animState);
            }
        }

        @Override
        public void onMapCenterChange(NMapView mapView, NGeoPoint center) {
            if (Constant.DEBUG) {
                Log.i(TAG, "onMapCenterChange: center=" + center.toString());
            }
        }

        @Override
        public void onZoomLevelChange(NMapView mapView, int level) {
            if (Constant.DEBUG) {
                Log.i(TAG, "onZoomLevelChange: level=" + level);
            }
        }

        @Override
        public void onMapCenterChangeFine(NMapView mapView) {

        }
    };


    @Override
    protected int getLayout() {
        return R.layout.fragment_map;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        lvMapField = (HorizontalListView) view.findViewById(R.id.lv_map_field);
        lvMapAccessbility = (HorizontalListView) view.findViewById(R.id.lv_map_accessbility);
        grCategory = (GridView) view.findViewById(R.id.gr_map_category);
        storeView = new StoreViewMap(getActivity());

        storeView = (StoreViewMap) view.findViewById(R.id.store_view);

//        layoutMap = (RelativeLayout) view.findViewById(R.id.layout_map);
//
//        addStoreView(layoutMap);
        storeView.setVisibility(View.INVISIBLE);

        btnMyLocation = (ImageView) view.findViewById(R.id.btn_my_location_map_fragment);
        btnMyLocation.setOnClickListener(this);

        btnZoomIn = (ImageView) view.findViewById(R.id.btn_zoom_in_map_fragment);
        btnZoomIn.setOnClickListener(this);

        btnZoomOut = (ImageView) view.findViewById(R.id.btn_zoom_out_map_fragment);
        btnZoomOut.setOnClickListener(this);

        btnZoomBg = (ImageView) view.findViewById(R.id.img_bg_zoom);
        btnZoomBg.setOnClickListener(this);

        imgCategory = (ImageView) view.findViewById(R.id.img_category_map);
        imgCategory.setOnClickListener(this);

        imgSearch = (ImageView) view.findViewById(R.id.img_search_map);
        imgSearch.setOnClickListener(this);

        tvCategory = (CustomTextViewBold) view.findViewById(R.id.tv_category_map_header);
        layoutCategory = (RelativeLayout) view.findViewById(R.id.layout_catetory_map);
    }

    private ArrayList<Store> getStoresOnItemClick(int categorySelected, int fieldSelected, ArrayList<Integer> accessbilitiesSclected) {

        ProgressDialogUtils.showProgressDialog(getActivity(), 0, 0);

        LogUtils.d(TAG, "getStoresOnItemClick start , categorySelected : " + categorySelected + " , fieldSelected : " + fieldSelected + " , accessbilitiesSclected : " + accessbilitiesSclected);

        ArrayList<Store> result = new ArrayList<>();


        if (dbStoreUpdate == null)
            dbStoreUpdate = new DbStoreUpdate(getActivity(), WebServiceConfig.URL_UPDATE_STORE);

        result = (ArrayList<Store>) dbStoreUpdate.getStoreFilterInMapScreen(categorySelected, fieldSelected, accessbilitiesSclected);

        ProgressDialogUtils.dismissProgressDialog();

        return result;
    }

    @Override
    protected void registerListener() {

        //Store - update
        if (dbStoreUpdate == null)
            dbStoreUpdate = new DbStoreUpdate(getActivity(), WebServiceConfig.URL_UPDATE_STORE);

        dbStoreUpdate.setOnResponseSuccess(new UpdateDbItf.OnResponseSuccess() {
            @Override
            public void onResponseSuccess() {
                countRequestTask--;
                initAddPinOnMap();
            }
        });

        dbStoreUpdate.setOnResponseFail(new UpdateDbItf.OnResponseFail() {
            @Override
            public void onResponseFail() {
                countRequestTask--;
                initAddPinOnMap();
            }
        });

        dbStoreUpdate.execute(false);

        // Category - update + display
        dbCategoryUpdate = new DbCategoryUpdate(getActivity(), WebServiceConfig.URL_UPDATE_CATEGORY);

        dbCategoryUpdate.setOnResponseSuccess(new UpdateDbItf.OnResponseSuccess() {
            @Override
            public void onResponseSuccess() {
                refreshCategoryAdapter();

                countRequestTask--;
                initAddPinOnMap();
            }
        });

        dbCategoryUpdate.setOnResponseFail(new UpdateDbItf.OnResponseFail() {
            @Override
            public void onResponseFail() {
                refreshCategoryAdapter();

                countRequestTask--;
                initAddPinOnMap();
            }
        });

        dbCategoryUpdate.execute(false);

        grCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.d(TAG, "grCategory onItemClick , position : " + position);
                mapCategoryAdapter.setPositionSelected(position);
                layoutCategory.setVisibility(View.INVISIBLE);
                mapCategoryAdapter.notifyDataSetChanged();

                if (categories != null && categories.size() > 0) {
                    categorySelected = (int) categories.get(position).getId();
                    tvCategory.setText(categories.get(position).getCatName());
                }

                ArrayList<Integer> accSelected = getAccessbilitySelected();

                addPinOnMap(getStoresOnItemClick(categorySelected, fieldSelected, accSelected));

            }
        });

        // Field - update + display
        dbFieldUpdate = new DbFieldUpdate(getActivity(), WebServiceConfig.URL_UPDATE_FIELD);
        dbFieldUpdate.setOnResponseSuccess(new UpdateDbItf.OnResponseSuccess() {
            @Override
            public void onResponseSuccess() {
                refreshFieldAdapter();

                countRequestTask--;
                initAddPinOnMap();

            }
        });

        dbFieldUpdate.setOnResponseFail(new UpdateDbItf.OnResponseFail() {
            @Override
            public void onResponseFail() {
                refreshFieldAdapter();

                countRequestTask--;
                initAddPinOnMap();

            }
        });

        lvMapField.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.d(TAG, "lvMapField onItemClick , position : " + position);
                mapFieldAdapter.setPositionSelected(position);
                mapFieldAdapter.notifyDataSetChanged();

                fieldSelected = (int) fields.get(position).getId();

                ArrayList<Integer> accSelected = getAccessbilitySelected();

                addPinOnMap(getStoresOnItemClick(categorySelected, fieldSelected, accSelected));

            }
        });


        dbFieldUpdate.execute(false);

        // Accessbility - update + display
        dbSettingUpdate = new DbSettingUpdate(getActivity(), WebServiceConfig.URL_UPDATE_SETTING);
        dbSettingUpdate.setOnResponseSuccess(new UpdateDbItf.OnResponseSuccess() {
            @Override
            public void onResponseSuccess() {
                refreshAccessbilityAdapter();

                countRequestTask--;
                initAddPinOnMap();

            }
        });

        dbSettingUpdate.setOnResponseFail(new UpdateDbItf.OnResponseFail() {
            @Override
            public void onResponseFail() {
                refreshAccessbilityAdapter();

                countRequestTask--;
                initAddPinOnMap();

            }
        });

        dbSettingUpdate.execute(false);

        lvMapAccessbility.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.d(TAG, "lvMapAccessbility.setOnItemClickListener , position : " + position);
                //fake selected
//                getStores(categorySelected, fieldSelected, accessbilitysSelected);

                if (accessibilities.get(position).getSelected() == 1) {
                    accessibilities.get(position).setSelected(0);
                } else {
                    accessibilities.get(position).setSelected(1);
                }

                mapAccessbilityAdapter.notifyDataSetChanged();

                ArrayList<Integer> accSelected = getAccessbilitySelected();
                addPinOnMap(getStoresOnItemClick(categorySelected, fieldSelected, accSelected));

            }
        });

    }

    private void refreshCategoryAdapter() {
        categories = dbCategoryUpdate.getAllParent(getActivity());

        if (categories != null && getActivity() != null) {
            if (mapCategoryAdapter == null) {
                mapCategoryAdapter = new MapCategoryAdapter(getActivity(), categories);
                grCategory.setAdapter(mapCategoryAdapter);
                tvCategory.setText(categories.get(0).getCatName());
            } else {
//                grCategory.setAdapter(null);
                mapCategoryAdapter.clear();
                mapCategoryAdapter.addAll(categories);
                mapCategoryAdapter.notifyDataSetChanged();
//                grCategory.setAdapter(mapCategoryAdapter);
                tvCategory.setText(categories.get(0).getCatName());
            }
        }
    }

    private void refreshFieldAdapter() {
        fields = dbFieldUpdate.getAllFields(getActivity());
        fields.add(0, getFieldAll());

        if (fields != null && getActivity() != null) {
            if (fields != null) {
                if (mapFieldAdapter == null) {
                    mapFieldAdapter = new MapFieldAdapter(getActivity(), fields);
                    lvMapField.setAdapter(mapFieldAdapter);
                } else {
//                    lvMapField.setAdapter(null);
                    mapFieldAdapter.clear();
                    mapFieldAdapter.addAll(fields);
                    mapFieldAdapter.notifyDataSetChanged();
//                    lvMapField.setAdapter(mapFieldAdapter);
                }
            }
        }
    }

    private void refreshAccessbilityAdapter() {
        accessibilities = dbSettingUpdate.getAllAccessbility(getActivity());
        for (int i = 0; i < accessibilities.size(); i++)
            accessibilities.get(i).setSelected(1);

        if (accessibilities != null && getActivity() != null)
            if (accessibilities != null) {
                if (mapAccessbilityAdapter == null) {
                    mapAccessbilityAdapter = new MapAccessbilityAdapter(getActivity(), accessibilities);
                    lvMapAccessbility.setAdapter(mapAccessbilityAdapter);
                } else {
//                    lvMapAccessbility.setAdapter(null);
                    mapAccessbilityAdapter.clear();
                    mapAccessbilityAdapter.addAll(accessibilities);
                    mapAccessbilityAdapter.notifyDataSetChanged();
//                    lvMapAccessbility.setAdapter(mapAccessbilityAdapter);
                }
            }
    }


    private Field getFieldAll() {
        Field field = new Field();
        field.setId(0);
        field.setName(getString(R.string.all_field_map));
        field.setDescription("");
        field.setCreateDate(new Date());
        field.setUpdateDate(new Date());
        return field;
    }

    private ArrayList<Integer> getAccessbilitySelected() {
        ArrayList<Integer> result = new ArrayList<>();

        for (int i = 0; i < accessibilities.size(); i++) {
            if (accessibilities.get(i).getSelected() == 1)
                result.add((int) accessibilities.get(i).getId());
        }
        return result;
    }

    private void initAddPinOnMap() {
        LogUtils.d(TAG, "initAddPinOnMap , countRequestTask : " + countRequestTask);
        if (countRequestTask == 0) {
            if (categories != null && categories.size() > 0)
                categorySelected = (int) categories.get(0).getId();
            addPinOnMap(getStoresOnItemClick(categorySelected, fieldSelected, getAccessbilitySelected()));
            ProgressDialogUtils.dismissProgressDialog();
        }
    }

    private void addPinOnMap(final ArrayList<Store> stores) {


        mOverlayManager.clearOverlays();
        mOverlayManager.createPOIdataOverlay(new NMapPOIdata(0, mMapViewerResourceProvider), null);

        LogUtils.d(TAG, "start addPinOnMap , stores size : " + stores.size());

        // set POI data
        NMapPOIdata poiData = new NMapPOIdata(stores.size(), mMapViewerResourceProvider);
        poiData.beginPOIdata(stores.size());

        storeViewDisplayTask = new StoreViewDisplayTask();

        for (int i = 0; i < stores.size(); i++) {
            Store store = stores.get(i);

            int markerId;

//            if (fieldSelected == 0) {
//                markerId = NaverMapUtils.getMarkerId(store);
//            } else {
//                markerId = NaverMapUtils.getMarkerIdFieldSelected(fieldSelected);
//            }

            poiData.addPOIitem(Double.parseDouble(store.getLongitude()), Double.parseDouble(store.getLatitude()), "", NMapPOIflagType.DEFAULT, 0);
        }

        poiData.endPOIdata();

        NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);
        poiDataOverlay.showAllPOIdata(0);
        // create POI data overlay

        // set event listener to the overlay
        poiDataOverlay.setOnStateChangeListener(new NMapPOIdataOverlay.OnStateChangeListener() {
                                                    @Override
                                                    public void onFocusChanged(NMapPOIdataOverlay nMapPOIdataOverlay, NMapPOIitem nMapPOIitem) {
                                                        if (nMapPOIitem != null) {
                                                            Double lat = nMapPOIitem.getPoint().getLatitude();
                                                            Double lon = nMapPOIitem.getPoint().getLongitude();

                                                            LogUtils.d(TAG, "onFocusChanged lat : " + lat + " , lon : " + lon);

                                                            Store storeInfo = findStoreByLatLong(stores, lat, lon);

                                                            LogUtils.d(TAG, "onFocusChanged storeInfo : " + storeInfo.toString());

                                                            storeView.updateView(storeInfo);
                                                            storeView.setVisibility(View.VISIBLE);
                                                            storeView.removeCallbacks(storeViewDisplayTask);
                                                            storeView.postDelayed(storeViewDisplayTask, Constant.STORE_TIME);
                                                        }
                                                    }

                                                    @Override
                                                    public void onCalloutClick(NMapPOIdataOverlay nMapPOIdataOverlay, NMapPOIitem
                                                            nMapPOIitem) {
                                                        LogUtils.d(TAG, "onCalloutClick start ");
                                                    }
                                                }

        );
    }

    static class StoreViewDisplayTask implements Runnable {
        @Override
        public void run() {
            storeView.setVisibility(View.INVISIBLE);
        }

    }

    private Store findStoreByLatLong(ArrayList<Store> stores, Double lat, Double lon) {
        Store result = new Store();
        for (int i = 0; i < stores.size(); i++) {
            if (lat == Double.parseDouble(stores.get(i).getLatitude()) && lon == Double.parseDouble(stores.get(i).getLongitude()))
                return stores.get(i);
        }
        return result;
    }


    @Override
    protected void initVaribles() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
            case R.id.img_category_map:
                showCategoryList();
                break;
            case R.id.img_search_map:
                doSearch();
                break;
        }
    }

    private void showCategoryList() {
        if (layoutCategory.getVisibility() == View.VISIBLE) {
            layoutCategory.setVisibility(View.INVISIBLE);
        } else if (layoutCategory.getVisibility() == View.INVISIBLE) {
            layoutCategory.setVisibility(View.VISIBLE);
        }

        if (mapCategoryAdapter != null)
            mapCategoryAdapter.notifyDataSetChanged();
    }

    private void doSearch() {
        showPopup();
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
}
