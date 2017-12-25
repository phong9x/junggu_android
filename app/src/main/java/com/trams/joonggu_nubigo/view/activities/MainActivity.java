package com.trams.joonggu_nubigo.view.activities;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.utils.ProgressDialogUtils;
import com.trams.joonggu_nubigo.view.customview.CustomEditTextNomal;
import com.trams.joonggu_nubigo.view.customview.CustomTextViewNomal;
import com.trams.joonggu_nubigo.view.fragments.AreaFragment;
import com.trams.joonggu_nubigo.view.fragments.AreaStoreFragment;
import com.trams.joonggu_nubigo.view.fragments.IntroFragment;
import com.trams.joonggu_nubigo.view.fragments.MapFragment;
import com.trams.joonggu_nubigo.view.fragments.MyPageFragment;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String SEARCH_TEXT = "SEARCH_TEXT";

    View popup, headerPos1, headerPos4;
    CustomEditTextNomal searchPopupText;
    View restOfPopup, closePopupBtn;

    // tab bottom
    private LinearLayout mRlTab1, mRlTab2, mRlTab3, mRlTab4;
    private ImageView mIvTab1, mIvTab2, mIvTab3, mIvTab4;
    private CustomTextViewNomal mTvTab1, mTvTab2, mTvTab3, mTvTab4;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        popup = findViewById(R.id.popup_container);

        headerPos1 = findViewById(R.id.header_pos1_img);
        headerPos4 = findViewById(R.id.header_search_img_pos4);

        searchPopupText = (CustomEditTextNomal) findViewById(R.id.search_box_edit_text);
        restOfPopup = findViewById(R.id.rest_of_popup_layout);
        closePopupBtn = findViewById(R.id.search_box_search_func);

    }

    @Override
    protected void registerListener() {
        headerPos1.setOnClickListener(this);
        headerPos4.setOnClickListener(this);

        restOfPopup.setOnClickListener(this);
        closePopupBtn.setOnClickListener(this);
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        setupTabs();
        setTabSelect(0);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/NanumBarunGothic.ttf");
        searchPopupText.setTypeface(typeface);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rest_of_popup_layout:
                dismissPopup();
                hideKeyboardSearchPopup();
                break;
            case R.id.search_box_search_func:
                String enteredText = searchPopupText.getText().toString();
                if (!enteredText.equals("")) {
                    hideKeyboardSearchPopup();
                    Bundle bundle = new Bundle();
                    bundle.putString(SEARCH_TEXT, enteredText);
                    bundle.putString(AreaStoreFragment.AREA_STORE_TYPE, AreaStoreFragment.AREA_STORE_SEARCH);

                    openFragment(R.id.tabContent, AreaStoreFragment.class, bundle, true);
                }
                dismissPopup();
                hideKeyboardSearchPopup();
                break;
            case R.id.header_pos1_img:
                fragmentManager.popBackStack();
                break;
            case R.id.header_search_img_pos4:
                showPopup();
                break;
        }
    }

    void showPopup() {
        searchPopupText.setText("");
        popup.setVisibility(View.VISIBLE);
    }

    void dismissPopup() {
        popup.setVisibility(View.GONE);
    }

    // setup tab bottom
    void setupTabs() {
        initTabView();
        initTabEvent();
    }

    private void initTabView() {
        mRlTab1 = (LinearLayout) findViewById(R.id.rl_tab1);
        mRlTab2 = (LinearLayout) findViewById(R.id.rl_tab2);
        mRlTab3 = (LinearLayout) findViewById(R.id.rl_tab3);
        mRlTab4 = (LinearLayout) findViewById(R.id.rl_tab4);

        mTvTab1 = (CustomTextViewNomal) findViewById(R.id.tv_tab1);
        mTvTab2 = (CustomTextViewNomal) findViewById(R.id.tv_tab2);
        mTvTab3 = (CustomTextViewNomal) findViewById(R.id.tv_tab3);
        mTvTab4 = (CustomTextViewNomal) findViewById(R.id.tv_tab4);

        mIvTab1 = (ImageView) findViewById(R.id.iv_tab1);
        mIvTab2 = (ImageView) findViewById(R.id.iv_tab2);
        mIvTab3 = (ImageView) findViewById(R.id.iv_tab3);
        mIvTab4 = (ImageView) findViewById(R.id.iv_tab4);

    }

    public void initTabEvent() {
        mRlTab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabSelect(0);
            }
        });
        mRlTab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabSelect(1);
            }
        });
        mRlTab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabSelect(2);
            }
        });
        mRlTab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabSelect(3);
            }
        });
    }

    public void setTabSelect(int i) {

        if (ProgressDialogUtils.progressDialog != null && ProgressDialogUtils.progressDialog.isShowing())
            return;

        showHeader(false);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        resetTab();
        switch (i) {
            case 0:
//                disableClick(0);
                openFragment(R.id.tabContent, AreaFragment.class, false);
                mIvTab1.setImageResource(R.drawable.tab1_selected);
                mTvTab1.setTextColor(getResources().getColor(R.color.tab_text_press));
                mRlTab1.setBackgroundColor(getResources().getColor(R.color.tab_select_back));
                disableClick(0);
                break;
            case 1:
//                disableClick(1);
                openFragment(R.id.tabContent, MapFragment.class, false);
                mIvTab2.setImageResource(R.drawable.tab2_selected);
                mTvTab2.setTextColor(getResources().getColor(R.color.tab_text_press));
                mRlTab2.setBackgroundColor(getResources().getColor(R.color.tab_select_back));
                disableClick(1);
                break;
            case 2:
//                disableClick(2);
                openFragment(R.id.tabContent, IntroFragment.class, false);
                mIvTab3.setImageResource(R.drawable.tab3_selected);
                mTvTab3.setTextColor(getResources().getColor(R.color.tab_text_press));
                mRlTab3.setBackgroundColor(getResources().getColor(R.color.tab_select_back));
                disableClick(2);
                break;
            case 3:
//                disableClick(3);
                openFragment(R.id.tabContent, MyPageFragment.class, false);
                mIvTab4.setImageResource(R.drawable.tab4_selected);
                mTvTab4.setTextColor(getResources().getColor(R.color.tab_text_press));
                mRlTab4.setBackgroundColor(getResources().getColor(R.color.tab_select_back));
                disableClick(3);
                break;
            default:

                break;
        }
        transaction.commit();

    }

    private void disableClick(int i) {

        mRlTab1.setClickable(true);
        mRlTab2.setClickable(true);
        mRlTab3.setClickable(true);
        mRlTab4.setClickable(true);

        mRlTab1.setEnabled(true);
        mRlTab2.setEnabled(true);
        mRlTab3.setEnabled(true);
        mRlTab4.setEnabled(true);

        switch (i) {
            case 0:
                mRlTab1.setClickable(false);
                mRlTab1.setEnabled(false);
                break;
            case 1:
                mRlTab2.setClickable(false);
                mRlTab2.setEnabled(false);
                break;
            case 2:
                mRlTab3.setClickable(false);
                mRlTab3.setEnabled(false);
                break;
            case 3:
                mRlTab4.setClickable(false);
                mRlTab4.setEnabled(false);
                break;
            default:

                break;
        }


    }

    private void resetTab() {
        mRlTab1.setBackgroundColor(getResources().getColor(R.color.tab_back));
        mRlTab2.setBackgroundColor(getResources().getColor(R.color.tab_back));
        mRlTab3.setBackgroundColor(getResources().getColor(R.color.tab_back));
        mRlTab4.setBackgroundColor(getResources().getColor(R.color.tab_back));

        mIvTab1.setImageResource(R.drawable.tab1_normal);
        mIvTab2.setImageResource(R.drawable.tab2_normal);
        mIvTab3.setImageResource(R.drawable.tab3_normal);
        mIvTab4.setImageResource(R.drawable.tab4_normal);

        mTvTab1.setTextColor(getResources().getColor(R.color.tab_text));
        mTvTab2.setTextColor(getResources().getColor(R.color.tab_text));
        mTvTab3.setTextColor(getResources().getColor(R.color.tab_text));
        mTvTab4.setTextColor(getResources().getColor(R.color.tab_text));
    }

    public void showHeader(boolean isShow) {
        if (isShow == true) {
            findViewById(R.id.header_container).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.header_container).setVisibility(View.GONE);
        }
    }

    void hideKeyboardSearchPopup() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}
