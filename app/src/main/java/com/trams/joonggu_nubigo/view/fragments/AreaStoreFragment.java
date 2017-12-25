package com.trams.joonggu_nubigo.view.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.common.AppGlobal;
import com.trams.joonggu_nubigo.dao.Accessibility;
import com.trams.joonggu_nubigo.dao.Field;
import com.trams.joonggu_nubigo.dao.Store;
import com.trams.joonggu_nubigo.dbmanager.AccessibilityManager;
import com.trams.joonggu_nubigo.dbmanager.CategoryManager;
import com.trams.joonggu_nubigo.dbmanager.DbFieldUpdate;
import com.trams.joonggu_nubigo.dbmanager.DbStoreUpdate;
import com.trams.joonggu_nubigo.dbmanager.FieldManager;
import com.trams.joonggu_nubigo.dbmanager.StoreManager;
import com.trams.joonggu_nubigo.dbmanager.UpdateDbItf;
import com.trams.joonggu_nubigo.network.WebServiceConfig;
import com.trams.joonggu_nubigo.utils.LogUtils;
import com.trams.joonggu_nubigo.utils.ProgressDialogUtils;
import com.trams.joonggu_nubigo.view.activities.AreaStoreMapActivity;
import com.trams.joonggu_nubigo.view.activities.MainActivity;
import com.trams.joonggu_nubigo.view.adapters.FieldAdapter;
import com.trams.joonggu_nubigo.view.adapters.ScrapAdapter;
import com.trams.joonggu_nubigo.view.customview.HorizontalListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 29/10/2015.
 */
public class AreaStoreFragment extends BaseFragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private static final String TAG = AreaStoreFragment.class.getSimpleName();

    public static final String REGION_ID = "REGION_ID";

    public static final String REGION_NAME = "REGION_NAME";

    public static final String AREA_STORE_TYPE = "AREA_STORE_TYPE";

    public static final String AREA_STORE_NORMAL = "AREA_STORE_NORMAL";

    public static final String AREA_STORE_SEARCH = "AREA_STORE_SEARCH";

    public static final String AREA_STORE_SEARCH_TEXT = "AREA_STORE_SEARCH_TEXT";

    private ListView listView;
    private ScrapAdapter adapter;
    private ArrayList<Store> data = new ArrayList<>();
    private Long categoryId;
    private String categoryName, enteredText, type;

    private View headerNormalContainer, headerSearchContainer;

    private View backSearchBtn, showMapSearchBtn, searchBtn;

    private HorizontalListView fieldListView;
    private FieldAdapter fieldAdapter;
    private List<Field> fields;
    //    private DobList dobList;
    private ArrayList<Store> originalStoreList = new ArrayList<>();
    private ArrayList<Store> originalStoreSearchList = new ArrayList<>();
    private List<Long> filterAccess = new ArrayList<>();
    int limit = 10;
    boolean loadingMore = false;
    private boolean isStopLoadingMore = true;

    @Override
    protected int getLayout() {
        return R.layout.fragment_area_store;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        listView = (ListView) view.findViewById(R.id.area_store_lv);
        headerNormalContainer = view.findViewById(R.id.area_store_header_normal_container);
        headerSearchContainer = view.findViewById(R.id.area_store_header_search_container);
        backSearchBtn = view.findViewById(R.id.header_search_back_btn);
        showMapSearchBtn = view.findViewById(R.id.header_search_show_map_btn);
        searchBtn = view.findViewById(R.id.header_search_search_btn);
        fieldListView = (HorizontalListView) view.findViewById(R.id.area_store_field_lv);
        adapter = new ScrapAdapter(getActivity(), data);
        listView.setAdapter(adapter);
    }

    @Override
    protected void registerListener() {
        listView.setOnItemClickListener(this);
        headerBackBtn.setOnClickListener(this);
        headerShowMapBtn.setOnClickListener(this);
        headerSearchBtn.setOnClickListener(this);
        backSearchBtn.setOnClickListener(this);
        showMapSearchBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        fieldListView.setOnItemClickListener(onFieldItemClickListener);
    }

    AdapterView.OnItemClickListener onFieldItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            fieldAdapter.setSelectedIdx(position);
            fieldAdapter.notifyDataSetChanged();
            filter(position);
        }
    };

    void filter(int pos) {
        ProgressDialogUtils.showProgressDialog(getActivity(), 0, 0);

        LogUtils.d(TAG, "filter start : " + pos);

        loadingMore = false;
        isStopLoadingMore = true;
        data = new ArrayList<>();
        adapter = new ScrapAdapter(getContext(), data);
        listView.setAdapter(adapter);
        String count = "";

        if (pos == 0) {
            if (type.equals(AREA_STORE_NORMAL)) {
                count = StoreManager.getInstance(getActivity()).getStores(filterAccess, categoryId).size() + "";
                originalStoreList.clear();
                originalStoreList.addAll(StoreManager.getInstance(getActivity()).getStores(filterAccess, categoryId));
                LogUtils.d(TAG, "filter originalStoreList size : " + originalStoreList.size());
            } else if (type.equals(AREA_STORE_SEARCH)) {
                originalStoreSearchList.clear();
                originalStoreSearchList.addAll(StoreManager.getInstance(getActivity()).getListSearch(enteredText, filterAccess));
                LogUtils.d(TAG, "filter originalStoreSearchList size : " + originalStoreSearchList.size());
            }
        } else {

            ArrayList<Store> allStore = new ArrayList<>();
            if (type.equals(AREA_STORE_NORMAL)) {
                allStore = (ArrayList<Store>) StoreManager.getInstance(getActivity()).getStores(filterAccess, categoryId);
            } else if (type.equals(AREA_STORE_SEARCH)) {
                allStore = (ArrayList<Store>) StoreManager.getInstance(getActivity()).getListSearch(enteredText, filterAccess);
            }

            String filterText = fields.get(pos).getName().toLowerCase().trim();
            List<Store> stores = new ArrayList<>();
            for (int i = 0; i < allStore.size(); i++) {
                Store store = allStore.get(i);
                String fieldText = store.getFieldList().toLowerCase().trim();
                if (fieldText.contains(filterText)) {
                    stores.add(store);
                }
            }

            if (type.equals(AREA_STORE_NORMAL)) {
                originalStoreList.clear();
                originalStoreList.addAll(stores);
                count = originalStoreList.size() + "";
                LogUtils.d(TAG, "filter init originalStoreList size : " + originalStoreList.size());
            } else if (type.equals(AREA_STORE_SEARCH)) {
                originalStoreSearchList.clear();
                originalStoreSearchList.addAll(stores);
                count = originalStoreSearchList.size() + "";
                LogUtils.d(TAG, "filter init originalStoreSearchList size : " + originalStoreSearchList.size());
            }

            LogUtils.d(TAG, "filter , all store size : " + allStore.size());
        }
        setupHeaderTitle(count);
        addItems();
    }

    @Override
    protected void initVaribles() {
        updateHeader();
        showHeader(false);
        showBottomTabBar(true);

        updateDb();
    }

    void updateHeader() {
        Bundle bundle = getArguments();
        type = bundle.getString(AREA_STORE_TYPE);

        if (type.equals(AREA_STORE_NORMAL)) {
            headerNormalContainer.setVisibility(View.VISIBLE);
            headerSearchContainer.setVisibility(View.GONE);

        } else if (type.equals(AREA_STORE_SEARCH)) {
            headerNormalContainer.setVisibility(View.GONE);
            headerSearchContainer.setVisibility(View.VISIBLE);
        }
    }

    void setupHeaderTitle(String count) {
        if (type.equals(AREA_STORE_NORMAL)) {
            headerTitleTv.setText(categoryName);
            headerCountTv.setText("(" + count + ")");
        } else if (type.equals(AREA_STORE_SEARCH)) {
            headerSearchEditText.setText(enteredText);
        }
    }

    void setTitleCount(int count) {
        headerCountTv.setText("(" + count + ")");
    }

    void init() {
        Bundle bundle = getArguments();
        type = bundle.getString(AREA_STORE_TYPE);
        categoryId = bundle.getLong(AreaFragment.CATEGORY_ID);
        categoryName = bundle.getString(AreaFragment.CATEGORY_NAME);
        AppGlobal.sCurAreaName = categoryName;
        enteredText = bundle.getString(MainActivity.SEARCH_TEXT);
        String count = "";

        // accessibility filter
        List<Accessibility> accessibilities = AccessibilityManager.getInstance(getActivity()).getAccessibilities();
        filterAccess.clear();
        for (Accessibility accessibility :
                accessibilities) {
            if (accessibility.getSelected() == 1) {
                filterAccess.add(accessibility.getId());
            }
        }

        if (type.equals(AREA_STORE_NORMAL)) {
            count = StoreManager.getInstance(getActivity()).getStores(filterAccess, categoryId).size() + "";
            originalStoreList.clear();
            originalStoreList.addAll(StoreManager.getInstance(getActivity()).getStores(filterAccess, categoryId));
            LogUtils.d(TAG, "init originalStoreList size : " + originalStoreList.size());
        } else if (type.equals(AREA_STORE_SEARCH)) {
            originalStoreSearchList.clear();
            originalStoreSearchList.addAll(StoreManager.getInstance(getActivity()).getListSearch(enteredText, filterAccess));
            LogUtils.d(TAG, "init originalStoreSearchList size : " + originalStoreSearchList.size());
        }

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int lastInScreen = firstVisibleItem + visibleItemCount;
                if ((lastInScreen == totalItemCount && !(loadingMore)) && totalItemCount != 0 && isStopLoadingMore) {
                    LogUtils.d(TAG, "onScroll , lastInScreen == totalItemCount = " + lastInScreen);
                    addItems();
                }
            }
        });

        setupHeaderTitle(count);
//        initDobList(listView);

        addItems();

//        ProgressDialogUtils.dismissProgressDialog();

    }

    void showMapButton(int count) {
        // don't have any company, just disable map view button
        if (count == 0) {
            headerShowMapBtn.setClickable(false);
            showMapSearchBtn.setClickable(false);
        } else {
            headerShowMapBtn.setClickable(true);
            showMapSearchBtn.setClickable(true);
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        StoreManager.getInstance(getActivity()).closeDbConnections();
        CategoryManager.getInstance(getActivity()).closeDbConnections();
        FieldManager.getInstance(getActivity()).closeDbConnections();
        AccessibilityManager.getInstance(getActivity()).closeDbConnections();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    private void refreshAdapter() {
        LogUtils.d(TAG, "refreshAdapter start , data size : " + data.size());

//        if (data != null) {
//            if (adapter == null) {
//                adapter = new ScrapAdapter(getActivity(), data);
//                listView.setAdapter(adapter);
//            } else {
//                listView.setAdapter(null);
//        adapter.clear();
//        adapter.addAll(data);
        adapter.notifyDataSetChanged();
//                listView.setAdapter(adapter);
//            }
//            listView.setSelection(0);
//        }
    }

//    private void refreshAdapter(int newPos) {
//        if (data != null) {
////            if (adapter == null) {
////                adapter = new ScrapAdapter(getActivity(), data);
////                listView.setAdapter(adapter);
////            } else {
//                listView.setAdapter(null);
//                adapter.clear();
//                adapter.addAll(data);
//                adapter.notifyDataSetChanged();
////                listView.setAdapter(adapter);
////            }
////            listView.setSelection(newPos);
//        }
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_search_btn:
            case R.id.header_search_search_btn:
                showPopup();
                setAutoFocusPopupSearch();
                break;
            case R.id.header_back_btn:
            case R.id.header_search_back_btn:
                getFragmentManager().popBackStack();
                break;
            case R.id.header_show_map_btn:
            case R.id.header_search_show_map_btn:
                if (type.equals(AREA_STORE_NORMAL)) {
                    Bundle bundle = new Bundle();
                    bundle.putLong(REGION_ID, categoryId);
                    bundle.putString(REGION_NAME, categoryName);
                    bundle.putString(AREA_STORE_TYPE, AREA_STORE_NORMAL);
                    startActivity(AreaStoreMapActivity.class, bundle);
                } else if (type.equals(AREA_STORE_SEARCH) && data.size() != 0) {
                    Bundle bundle = new Bundle();
                    bundle.putString(AREA_STORE_TYPE, AREA_STORE_SEARCH);
                    bundle.putString(AREA_STORE_SEARCH_TEXT, enteredText);
                    startActivity(AreaStoreMapActivity.class, bundle);
                }
                break;
        }
    }

    public static AreaStoreFragment newInstance() {

        Bundle args = new Bundle();

        AreaStoreFragment fragment = new AreaStoreFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void updateDb() {

        final DbFieldUpdate dbFieldUpdate = new DbFieldUpdate(getActivity(), WebServiceConfig.URL_UPDATE_FIELD);

        dbFieldUpdate.setOnResponseSuccess(new UpdateDbItf.OnResponseSuccess() {
            @Override
            public void onResponseSuccess() {
                fields = FieldManager.getInstance(getActivity()).getFields();
                // add first item, it called all
                Field firstItem = new Field();
                firstItem.setName(getString(R.string.all_field_map));
                fields.add(0, firstItem);

                fieldAdapter = new FieldAdapter(getActivity(), fields);
                fieldListView.setAdapter(fieldAdapter);

                updateStoreDb();
            }
        });

        dbFieldUpdate.setOnResponseFail(new UpdateDbItf.OnResponseFail() {
            @Override
            public void onResponseFail() {

                fields = FieldManager.getInstance(getActivity()).getFields();
                // add first item, it called all
                Field firstItem = new Field();
                firstItem.setName(getString(R.string.all_field_map));
                fields.add(0, firstItem);

                fieldAdapter = new FieldAdapter(getActivity(), fields);
                fieldListView.setAdapter(fieldAdapter);

                updateStoreDb();
            }
        });

        dbFieldUpdate.execute(false);
    }

    void updateStoreDb() {
        final DbStoreUpdate dbStoreUpdate = new DbStoreUpdate(getActivity(), WebServiceConfig.URL_UPDATE_STORE);

        dbStoreUpdate.setOnResponseSuccess(new UpdateDbItf.OnResponseSuccess() {
            @Override
            public void onResponseSuccess() {
                init();
            }
        });

        dbStoreUpdate.setOnResponseFail(new UpdateDbItf.OnResponseFail() {
            @Override
            public void onResponseFail() {
                init();
            }
        });

        dbStoreUpdate.execute(false);
    }

//    private void initDobList(ListView listView) {
//
//        // DobList initializing
//        dobList = new DobList();
//        try {
//
//            // Register ListView
//            //
//            // NoListViewException will be thrown when
//            // there is no ListView
//            dobList.register(listView);
//
//            // Add ProgressBar to footers of ListView
//            // to be shown in loading more
//            dobList.addDefaultLoadingFooterView();
//
//            // Sets the view to show if the adapter is empty
//            // see startCentralLoading() method
//            /*View noItems = rootView.findViewById(R.id.noItems);
//            dobList.setEmptyView(noItems);*/
//
//            // Callback called when reaching last item in ListView
//            dobList.setOnLoadMoreListener(new OnLoadMoreListener() {
//
//                @Override
//                public void onLoadMore(final int totalItemCount) {
//                    Log.i(TAG, "onStart totalItemCount " + totalItemCount);
//
//                    // Just inserting some dummy data after
//                    // period of time to simulate waiting
//                    // data from server
//                    addData();
//                }
//            });
//
//        } catch (NoListviewException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            // Show ProgressBar at the center of ListView
//            // this can be used while loading data from
//            // server at the first time
//            //
//            // setEmptyView() must be called before
//            //
//            // NoEmptyViewException will be thrown when
//            // there is no EmptyView
//            dobList.startCentralLoading();
//
//        } catch (NoEmptyViewException e) {
//            e.printStackTrace();
//        }
//        // Simulate adding data at the first time
//        addData();
//    }

//    protected void addData() {
//        addItems();
////        new Handler().postDelayed(new Runnable() {
////            @Override
////            public void run() {
////                if (adapter == null) {
////                    adapter = new ScrapAdapter(getActivity(), new ArrayList<Store>());
////                }
////                addItems();
////
////                // We must call finishLoading
////                // when finishing adding data
//////                dobList.finishLoading();
////            }
////
////        }, 0);
//    }

    protected void addItems() {
        LogUtils.d(TAG, "addItems start ");
        loadingMore = true;

        int start = adapter.getCount();
        int end = start + limit;

        LogUtils.d(TAG, "addItems , type : " + type);

//        List<Store> tempList = new ArrayList<>();
        if (type.equals(AREA_STORE_NORMAL)) {
            if (end > originalStoreList.size()) {
                end = originalStoreList.size();
            }
//            tempList = originalStoreList.subList(start, end);

            for (int i = start; i < end; i++) {
                if (!data.contains(originalStoreList.get(i)))
                    data.add(originalStoreList.get(i));
            }

        } else if (type.equals(AREA_STORE_SEARCH)) {
            if (end > originalStoreSearchList.size()) {
                end = originalStoreSearchList.size();
            }
//            tempList = originalStoreSearchList.subList(start, end);
            for (int i = start; i < end; i++) {
                if (!data.contains(originalStoreSearchList.get(i)))
                    data.add(originalStoreSearchList.get(i));
            }
        }

//        data.addAll(tempList);
//        adapter.addAll(tempList);


        LogUtils.d(TAG, "addItems data size : " + data.size());
        LogUtils.d(TAG, "addItems , positon start : " + start + " , end : " + end);

//        if (data == null || data.) {
//            showMapButton(0);
//        } else {
//            showMapButton(data.size());
//        }

        showMapButton(data.size());

        refreshAdapter();

//        if (end == limit) {
//            refreshAdapter();
//        } else {
//            refreshAdapter(start);
//        }

        if (type.equals(AREA_STORE_NORMAL)) {
            if (adapter.getCount() == originalStoreList.size()) isStopLoadingMore = false;
        } else if (type.equals(AREA_STORE_SEARCH)) {
            if (adapter.getCount() == originalStoreSearchList.size()) isStopLoadingMore = false;
        }

        loadingMore = false;

        LogUtils.d(TAG, "add addItems finish");
        ProgressDialogUtils.dismissProgressDialog();
        LogUtils.d(TAG, "add addItems dismiss progress dialog");
    }


}
