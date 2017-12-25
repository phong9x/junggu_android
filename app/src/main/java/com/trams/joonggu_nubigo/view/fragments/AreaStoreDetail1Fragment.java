package com.trams.joonggu_nubigo.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.common.AppGlobal;
import com.trams.joonggu_nubigo.dao.User;
import com.trams.joonggu_nubigo.dbmanager.DbFacilityUpdate;
import com.trams.joonggu_nubigo.dbmanager.DbUserUpdate;
import com.trams.joonggu_nubigo.network.NetworkUtils;
import com.trams.joonggu_nubigo.network.WebServiceConfig;
import com.trams.joonggu_nubigo.objects.ImageObj;
import com.trams.joonggu_nubigo.parsers.StoreParser;
import com.trams.joonggu_nubigo.utils.LogUtils;
import com.trams.joonggu_nubigo.utils.NaverMapUtils;
import com.trams.joonggu_nubigo.utils.PreferUtils;
import com.trams.joonggu_nubigo.utils.ProgressDialogUtils;
import com.trams.joonggu_nubigo.utils.Utils;
import com.trams.joonggu_nubigo.view.activities.AreaStoreDetail1MapActivity;
import com.trams.joonggu_nubigo.view.adapters.TagsAdapter;
import com.trams.joonggu_nubigo.view.adapters.ViewPagerAdapter;
import com.trams.joonggu_nubigo.view.custome.SimpleViewPagerIndicator;
import com.trams.joonggu_nubigo.view.customview.CustomTextViewBold;
import com.trams.joonggu_nubigo.view.customview.CustomTextViewNomal;
import com.trams.joonggu_nubigo.view.dialog.DialogUtils;
import com.trams.joonggu_nubigo.view.dialog.ReportDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by zin9x on 11/10/2015.
 */
public class AreaStoreDetail1Fragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = AreaStoreDetail1Fragment.class.getSimpleName();
    private SimpleViewPagerIndicator mIndicator;
    private ViewPager pager;
    private GridView grvTags;
    private TagsAdapter tagsAdapter;
    private LinearLayout lnlReport;
    String phoneNumber = "";
    View makeACallBtn, showMapBtn, checkNaverBtn;
    ArrayList<ImageObj> imageObjs = null;
    ArrayList<String> tags = new ArrayList<>();

    CheckBox access0, access1, access2, access3, access4, access5;

    RatingBar star;
    CustomTextViewNomal totalRateText;

    CustomTextViewNomal tvAddress, tvNumber, tvServiceHours, tvHoliday, tvDateLatestUpdate, tvOtherInfo, tvLotNumber;

    double lat = 0, lon = 0;
    ImageView clipBtn;

    DbUserUpdate dbUserUpdate;

    DbFacilityUpdate dbFacilityUpdate;

    private ImageView imgNext, imgBack;

    private static AreaStoreDetail1Fragment instance;

    public static AreaStoreDetail1Fragment getInstance() {
        if (instance == null) {
            instance = new AreaStoreDetail1Fragment();
        }
        return instance;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_area_store_detail_1;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mIndicator = (SimpleViewPagerIndicator) view.findViewById(R.id.indicator);
        grvTags = (GridView) view.findViewById(R.id.grvTags);
        makeACallBtn = view.findViewById(R.id.area_store_detail_1_make_a_phone_btn);
        lnlReport = (LinearLayout) view.findViewById(R.id.lnlReport);

        access0 = (CheckBox) view.findViewById(R.id.access0);
        access1 = (CheckBox) view.findViewById(R.id.access1);
        access2 = (CheckBox) view.findViewById(R.id.access2);
        access3 = (CheckBox) view.findViewById(R.id.access3);
        access4 = (CheckBox) view.findViewById(R.id.access4);
        access5 = (CheckBox) view.findViewById(R.id.access5);

        pager = (ViewPager) view.findViewById(R.id.pager);
        star = (RatingBar) view.findViewById(R.id.star);
        totalRateText = (CustomTextViewNomal) view.findViewById(R.id.total_rate);
        showMapBtn = view.findViewById(R.id.area_store_detail_1_show_map_btn);

        tvAddress = (CustomTextViewNomal) view.findViewById(R.id.store_detail_1_address);
        tvNumber = (CustomTextViewNomal) view.findViewById(R.id.store_detail_1_phone);
        tvServiceHours = (CustomTextViewNomal) view.findViewById(R.id.store_detail_1_service_hours);
        tvHoliday = (CustomTextViewNomal) view.findViewById(R.id.store_detail_1_holiday);
        tvDateLatestUpdate = (CustomTextViewNomal) view.findViewById(R.id.store_detail_1_latest_update);
        tvOtherInfo = (CustomTextViewNomal) view.findViewById(R.id.store_detail_1_other_info);
        tvLotNumber = (CustomTextViewNomal) view.findViewById(R.id.tvLotNumber);

        clipBtn = (ImageView) view.findViewById(R.id.area_store_detail_1_clip_btn);

        imgNext = (ImageView) view.findViewById(R.id.img_next_detail1);
        imgBack = (ImageView) view.findViewById(R.id.img_back_detail1);
    }

    @Override
    protected void registerListener() {
        makeACallBtn.setOnClickListener(this);
        lnlReport.setOnClickListener(this);
        showMapBtn.setOnClickListener(this);
        clipBtn.setOnClickListener(this);


    }

    @Override
    protected void initVaribles() {
        dbUserUpdate = new DbUserUpdate(getActivity());
        update();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkNaverBtn = getActivity().findViewById(R.id.imgRight);
        checkNaverBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.area_store_detail_1_make_a_phone_btn:
                checkSimCard();
                break;
            case R.id.lnlReport:
                ReportDialog fragment = new ReportDialog();
                showDialog(fragment, "report dialog");
                break;
            case R.id.area_store_detail_1_show_map_btn:
                Intent intent = new Intent(getActivity(), AreaStoreDetail1MapActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.imgRight:
                NaverMapUtils.openNaverMap(getActivity(), lon, lat);
                break;
            case R.id.area_store_detail_1_clip_btn:
                if (PreferUtils.isLogin(getActivity())) {
                    scrapToggle();
                } else {
                    DialogUtils.showConfirmAlertDialog(getActivity(), "Log on to My Page", new DialogUtils.ConfirmDialogListener() {
                        @Override
                        public void onConfirmClick() {
                        }
                    });
                }
                break;
            default:
                break;
        }
    }

    void checkSimCard() {
        TelephonyManager telMgr = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        int simState = telMgr.getSimState();
        switch (simState) {
            case TelephonyManager.SIM_STATE_ABSENT:
                DialogUtils.showConfirmAlertDialog(getActivity(), "Check your SIM card please, make sure you're using the correct one!", new DialogUtils.ConfirmDialogListener() {
                    @Override
                    public void onConfirmClick() {
                    }
                });
                break;
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                DialogUtils.showConfirmAlertDialog(getActivity(), "Check your SIM card please, make sure you're using the correct one!", new DialogUtils.ConfirmDialogListener() {
                    @Override
                    public void onConfirmClick() {
                    }
                });
                break;
            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                DialogUtils.showConfirmAlertDialog(getActivity(), "Check your SIM card please, make sure you're using the correct one!", new DialogUtils.ConfirmDialogListener() {
                    @Override
                    public void onConfirmClick() {
                    }
                });
                break;
            case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                DialogUtils.showConfirmAlertDialog(getActivity(), "Check your SIM card please, make sure you're using the correct one!", new DialogUtils.ConfirmDialogListener() {
                    @Override
                    public void onConfirmClick() {
                    }
                });
                break;
            case TelephonyManager.SIM_STATE_READY:
                // do something
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(callIntent);
                break;
            case TelephonyManager.SIM_STATE_UNKNOWN:
                DialogUtils.showConfirmAlertDialog(getActivity(), "Check your SIM card please, make sure you're using the correct one!", new DialogUtils.ConfirmDialogListener() {
                    @Override
                    public void onConfirmClick() {
                    }
                });
                break;
        }
    }

    void updateScrapUI(String scrap) {
        scrap = scrap.trim();
        if (scrap == null || scrap.equals(""))
            clipBtn.setImageResource(R.drawable.ic_scrap_off);
        else {
            try {
                boolean scraped = false;
                JSONArray jsonArray = new JSONArray(scrap);
                LogUtils.d("updateScrapUI()", "updateScrapUI(): store scraped json = " + scrap);
                for (int i = 0; i < jsonArray.length(); i++) {
                    long storeIdScraped = jsonArray.getLong(i);
                    if (storeIdScraped == AppGlobal.sStore.getId()) {
                        scraped = true;
                        break;
                    }
                }
                if (scraped == true) {
                    clipBtn.setImageResource(R.drawable.ic_scrap_on);
                } else {
                    clipBtn.setImageResource(R.drawable.ic_scrap_off);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    void update() {

        // sometime server return null lat lon
        try {
            lat = Double.parseDouble(AppGlobal.sStore.getLatitude());
            lon = Double.parseDouble(AppGlobal.sStore.getLongitude());
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "ERROR : " + e.getMessage());
        }

        // phonenumber
        phoneNumber = AppGlobal.sStore.getPhone();
        // tags
        tags = StoreParser.getTags(AppGlobal.sStore.getTag());
        // list image obj for list pics show up on top page
        imageObjs = StoreParser.getMainImageUrls(AppGlobal.sStore.getImageBaseAttach());

        //scrap
        updateDbUser();

        setTitle(AppGlobal.sStore.getName());

        if (Utils.checkNull(AppGlobal.sStore.getAddress()))
            tvAddress.setText(getString(R.string.detail1_des) + " " + StoreParser.getAddress1(AppGlobal.sStore.getAddress()));

        if (Utils.checkNull(StoreParser.getAddress2(AppGlobal.sStore.getAddress(), AppGlobal.sCurAreaName)))
            tvLotNumber.setText(getString(R.string.detail1_des) + " " + StoreParser.getAddress2(AppGlobal.sStore.getAddress(), AppGlobal.sCurAreaName));

        if (Utils.checkNull(AppGlobal.sStore.getPhone()))
            tvNumber.setText(AppGlobal.sStore.getPhone());

        if (Utils.checkNull(AppGlobal.sStore.getServiceHours()))
            tvServiceHours.setText(AppGlobal.sStore.getServiceHours());

        if (Utils.checkNull(AppGlobal.sStore.getHoliday()))
            tvHoliday.setText(AppGlobal.sStore.getHoliday());

        if (AppGlobal.sStore.getUpdateDate() != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
            String date = format.format(AppGlobal.sStore.getUpdateDate());
            tvDateLatestUpdate.setText(date);
        }

        if (Utils.checkNull(AppGlobal.sStore.getOtherInfo()))
            tvOtherInfo.setText(AppGlobal.sStore.getOtherInfo());

        float starCount = StoreParser.getGra(AppGlobal.sStore.getGrade());
        star.setRating(starCount);
        CheckBox[] checkBoxes = new CheckBox[]{access0, access1, access2, access3, access4, access5};
        StoreParser.setAccessibilityList(AppGlobal.sStore.getAccessibilityList(), checkBoxes);
        int total = StoreParser.getTotalRate(AppGlobal.sStore.getGrade());
        totalRateText.setText(("" + total + "명"));
        tagsAdapter = new TagsAdapter(getActivity(), tags);

        if (imageObjs != null && imageObjs.size() > 1) {
            mIndicator.setVisibility(View.VISIBLE);
            imgNext.setVisibility(View.VISIBLE);
            imgBack.setVisibility(View.VISIBLE);
        } else {
            mIndicator.setVisibility(View.INVISIBLE);
            imgNext.setVisibility(View.INVISIBLE);
            imgBack.setVisibility(View.INVISIBLE);
        }

        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity(), imageObjs);
        pager.setAdapter(adapter);
        mIndicator.setViewPager(pager);
        grvTags.setAdapter(tagsAdapter);


    }

    void setTitle(String title) {
        CustomTextViewBold lblHeader = (CustomTextViewBold) getActivity().findViewById(R.id.lblHeader);
        lblHeader.setText(title);
    }


    private void scrapRequest() {

        Long storeId = AppGlobal.sStore.getId();

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("store_id", storeId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetworkUtils.postRequestVolley(getActivity(), WebServiceConfig.URL_UPDATE_SCRAP_STATUS, jsonObject, new NetworkUtils.RequestResponse() {
            @Override
            public void onResponse(JSONObject jsonResponse) {
                try {
                    LogUtils.d(TAG, "AreaStoreDetail1Fragment update scrap status success");

                    int code = jsonResponse.getInt("code");

                    if (code == 200) {
                        DbUserUpdate dbUserUpdate = new DbUserUpdate(getActivity());
                        final User userLogIn = dbUserUpdate.getUserLogin();

                        //login success -->
                        JSONObject jsonUser = jsonResponse.getJSONObject("value");
                        // update scrap status
                        String scrap = jsonUser.getString("scrap");
                        updateScrapUI(scrap);

                        User userLogined = new User();
                        userLogined.setId(jsonUser.getInt("id"));
                        userLogined.setUsername(jsonUser.getString("username"));
                        userLogined.setPassword(userLogIn.getPassword());
                        userLogined.setNickname(jsonUser.getString("nickname"));
                        userLogined.setFullname(jsonUser.getString("fullname"));
                        userLogined.setRole(jsonUser.getInt("role"));
                        userLogined.setSex(jsonUser.getString("sex"));
                        userLogined.setPhone(jsonUser.getString("phone"));
                        userLogined.setPhone(jsonUser.getString("email"));
                        userLogined.setScrap(jsonUser.getString("scrap"));
                        userLogined.setCreateDate(new Date(jsonUser.getLong("createDate")));
                        userLogined.setUpdateDate(new Date(jsonUser.getLong("updateDate")));

                        PreferUtils.setUserId(getActivity(), (int) userLogined.getId());
                        PreferUtils.setLogin(getActivity(), true);

                        //delete all data in user table , add only one row of user
                        dbUserUpdate.deleteAll();
                        dbUserUpdate.insertUserLogin(userLogined);

                    } else {
                        String msg = jsonResponse.getString("message");
                        DialogUtils.showConfirmAlertDialog(getActivity(), msg, new DialogUtils.ConfirmDialogListener() {
                            @Override
                            public void onConfirmClick() {

                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ProgressDialogUtils.dismissProgressDialog();
                    DialogUtils.showConfirmAlertDialog(getActivity(), getString(R.string.request_error_msg), new DialogUtils.ConfirmDialogListener() {
                        @Override
                        public void onConfirmClick() {

                        }
                    });
                }
            }
        }, new NetworkUtils.RequestError() {
            @Override
            public void onError() {
                ProgressDialogUtils.dismissProgressDialog();
            }
        }, true);
    }

    private void updateDbUser() {
        // reset scrap status
        clipBtn.setImageResource(R.drawable.ic_scrap_off);

        DbUserUpdate dbUserUpdate = new DbUserUpdate(getActivity());
        final User userLogIn = dbUserUpdate.getUserLogin();
        final JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put(WebServiceConfig.LOGIN_USERNAME, userLogIn.getUsername().toString());
            jsonRequest.put(WebServiceConfig.LOGIN_PASSWORD, userLogIn.getPassword());
            jsonRequest.put(WebServiceConfig.LOGIN_ROLE, "0");
        } catch (Exception e) {
            e.printStackTrace();
        }

        NetworkUtils.postRequestVolley(getActivity(), WebServiceConfig.URL_LOGIN, jsonRequest, new NetworkUtils.RequestResponse() {
            @Override
            public void onResponse(final JSONObject jsonResponse) {
                try {

                    int code = jsonResponse.getInt("code");

                    if (code == 200) {
                        //login success -->
                        String msg = jsonResponse.getString("message");
                        try {
                            JSONObject jsonUser = jsonResponse.getJSONObject("value");

                            User userLogined = new User();
                            userLogined.setId(jsonUser.getInt("id"));
                            userLogined.setUsername(jsonUser.getString("username"));
                            userLogined.setPassword(userLogIn.getPassword());
                            userLogined.setNickname(jsonUser.getString("nickname"));
                            userLogined.setFullname(jsonUser.getString("fullname"));
                            userLogined.setRole(jsonUser.getInt("role"));
                            userLogined.setSex(jsonUser.getString("sex"));
                            userLogined.setPhone(jsonUser.getString("phone"));
                            userLogined.setPhone(jsonUser.getString("email"));
                            userLogined.setScrap(jsonUser.getString("scrap"));

                            // update scrap status
                            updateScrapUI(jsonUser.getString("scrap"));

                            userLogined.setCreateDate(new Date(jsonUser.getLong("createDate")));
                            userLogined.setUpdateDate(new Date(jsonUser.getLong("updateDate")));


                            PreferUtils.setUserId(getActivity(), (int) userLogined.getId());
                            PreferUtils.setLogin(getActivity(), true);

                            //delete all data in user table , add only one row of user

                            DbUserUpdate dbUserUpdate = new DbUserUpdate(getActivity());
                            dbUserUpdate.deleteAll();
                            dbUserUpdate.insertUserLogin(userLogined);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ProgressDialogUtils.dismissProgressDialog();
                }
            }
        }, new NetworkUtils.RequestError() {
            @Override
            public void onError() {

            }
        }, true);
    }


    private void scrapToggle() {

        User userLogIn = dbUserUpdate.getUserLogin();
        LogUtils.d(TAG, "AreaStoreDetail1Fragment : userLogin " + userLogIn.toString());

        final JSONObject jsonRequest = new JSONObject();

        try {
            jsonRequest.put(WebServiceConfig.LOGIN_USERNAME, userLogIn.getUsername());
            jsonRequest.put(WebServiceConfig.LOGIN_PASSWORD, userLogIn.getPassword());
            jsonRequest.put(WebServiceConfig.LOGIN_ROLE, "0");
        } catch (Exception e) {
            e.printStackTrace();
        }

        NetworkUtils.postRequestVolley(getActivity(), WebServiceConfig.URL_LOGIN, jsonRequest, new NetworkUtils.RequestResponse() {
            @Override
            public void onResponse(final JSONObject jsonResponse) {
                try {
                    LogUtils.d(TAG, "AreaStoreDetail1Fragment login success");
                    int code = jsonResponse.getInt("code");

                    if (code == 200) {
                        //login success -->
                        String msg = jsonResponse.getString("message");
                        try {
                            String session = jsonResponse.getJSONObject("value").getString("sessionId");
                            PreferUtils.saveSession(getActivity(), session);
                            LogUtils.d(TAG, "Session = " + session);

                            //after get session , start comment
                            scrapRequest();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        // login fail
                        String msg = jsonResponse.getString("message");
                        DialogUtils.showConfirmAlertDialog(getActivity(), getString(R.string.request_error_msg), new DialogUtils.ConfirmDialogListener() {
                            @Override
                            public void onConfirmClick() {

                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ProgressDialogUtils.dismissProgressDialog();
                    DialogUtils.showConfirmAlertDialog(getActivity(), getString(R.string.request_error_msg), new DialogUtils.ConfirmDialogListener() {
                        @Override
                        public void onConfirmClick() {

                        }
                    });
                }
            }
        }, new NetworkUtils.RequestError() {
            @Override
            public void onError() {
                ProgressDialogUtils.dismissProgressDialog();
            }
        }, false);
    }


}
