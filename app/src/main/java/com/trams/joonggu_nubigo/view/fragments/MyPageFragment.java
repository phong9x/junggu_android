package com.trams.joonggu_nubigo.view.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.dbmanager.DbUserUpdate;
import com.trams.joonggu_nubigo.utils.PreferUtils;
import com.trams.joonggu_nubigo.view.activities.LoginActivity;
import com.trams.joonggu_nubigo.view.activities.NoticeActivity;
import com.trams.joonggu_nubigo.view.activities.RegisterActivity;
import com.trams.joonggu_nubigo.view.activities.ScrapActivity;
import com.trams.joonggu_nubigo.view.activities.SettingActivity;
import com.trams.joonggu_nubigo.view.customview.CustomTextViewNomal;

/**
 * Created by ADMIN on 11/3/2015.
 */


public class MyPageFragment extends BaseFragment implements View.OnClickListener {

    private ImageView imgLogin, imgScrap, imgNotice, imgSetting;
    private CustomTextViewNomal tvLogin, tvScrap, tvNotice, tvSetting;
    private RelativeLayout layoutLogin, layoutScrap, layoutNotice, layoutSetting, headerLayout, headerLayoutGuest;
    private ImageView imgSearch;

    @Override
    protected int getLayout() {
        return R.layout.fragment_mypage;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        imgLogin = (ImageView) view.findViewById(R.id.imgLogin);
        imgScrap = (ImageView) view.findViewById(R.id.imgScrap);
        imgNotice = (ImageView) view.findViewById(R.id.imgNotice);
        imgSetting = (ImageView) view.findViewById(R.id.imgSetting);

        tvLogin = (CustomTextViewNomal) view.findViewById(R.id.tvLogin);
        tvScrap = (CustomTextViewNomal) view.findViewById(R.id.tvScrap);
        tvNotice = (CustomTextViewNomal) view.findViewById(R.id.tvNotice);
        tvSetting = (CustomTextViewNomal) view.findViewById(R.id.tvSetting);


        layoutLogin = (RelativeLayout) view.findViewById(R.id.layoutLogin);
        layoutScrap = (RelativeLayout) view.findViewById(R.id.layoutScrap);
        layoutNotice = (RelativeLayout) view.findViewById(R.id.layoutNotice);
        layoutSetting = (RelativeLayout) view.findViewById(R.id.layoutSetting);

        headerLayout = (RelativeLayout) view.findViewById(R.id.header_mypage_layout);
        headerLayoutGuest = (RelativeLayout) view.findViewById(R.id.header_mypage_layout_guest);

        imgSearch = (ImageView) view.findViewById(R.id.header_search_btn);
    }

    @Override
    protected void registerListener() {
        imgLogin.setOnClickListener(this);
        imgScrap.setOnClickListener(this);
        imgNotice.setOnClickListener(this);
        imgSetting.setOnClickListener(this);

        layoutLogin.setOnClickListener(this);
        layoutScrap.setOnClickListener(this);
        layoutNotice.setOnClickListener(this);
        layoutSetting.setOnClickListener(this);
        imgSearch.setOnClickListener(this);
    }

    @Override
    protected void initVaribles() {
        updateLoginStatus();
    }

    private void updateLoginStatus() {
        if (PreferUtils.isLogin(getActivity())) {
            tvLogin.setText(getString(R.string.fragment_mypage_login));
            tvScrap.setText(getString(R.string.fragment_mypage_scrap));
            headerLayout.setVisibility(View.VISIBLE);
            headerLayoutGuest.setVisibility(View.GONE);
        } else {
            tvLogin.setText(getString(R.string.fragment_mypage_not_login));
            tvScrap.setText(getString(R.string.fragment_mypage_not_scrap));
            headerLayout.setVisibility(View.GONE);
            headerLayoutGuest.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutLogin:
                if (PreferUtils.isLogin(getActivity())) {
                    //logout
                    PreferUtils.setLogin(getActivity(), false);
                    PreferUtils.setUserId(getActivity(), 0);

                    //delete user in database
                    DbUserUpdate dbUserUpdate = new DbUserUpdate(getActivity());
                    dbUserUpdate.deleteAll();

                    updateLoginStatus();
                } else {
                    //replaceFragment(new MyPageLoginFragment());
//                    openFragment(R.id.tabContent, MyPageLoginFragment.class, true);
                    startActivity(LoginActivity.class);
                }
                break;

            case R.id.layoutScrap:
                if (PreferUtils.isLogin(getActivity())) {
                    //replaceFragment(new MyPageScrapFragment());
//                    openFragment(R.id.tabContent, MyPageScrapFragment.class, true);
                    startActivity(ScrapActivity.class);
                } else {
                    //replaceFragment(new MyPageJoinFragment());
//                    openFragment(R.id.tabContent, MyPageJoinFragment.class, true);
                    startActivity(RegisterActivity.class);
                }
                break;

            case R.id.layoutNotice:
                //replaceFragment(new MyPageNoticeFragment());
//                openFragment(R.id.tabContent, MyPageNoticeFragment.class, true);
                startActivity(NoticeActivity.class);
                break;

            case R.id.layoutSetting:
                //replaceFragment(new MyPageSettingFragment());
//                openFragment(R.id.tabContent, MyPageSettingFragment.class, true);
                startActivity(SettingActivity.class);
                break;

            case R.id.imgLogin:
                break;

            case R.id.imgScrap:
                break;

            case R.id.imgNotice:
                break;

            case R.id.imgSetting:
                break;

            case R.id.header_search_btn:
                doSearch();
                break;

        }
    }

    private void doSearch() {
        showPopup();
    }
}
