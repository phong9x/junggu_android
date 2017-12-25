package com.trams.joonggu_nubigo.view.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.view.customview.CustomTextViewNomal;
import com.trams.joonggu_nubigo.view.fragments.AreaStoreDetail1Fragment;
import com.trams.joonggu_nubigo.view.fragments.AreaStoreDetail2Fragment;
import com.trams.joonggu_nubigo.view.fragments.AreaStoreDetail3Fragment;

/**
 * Created by zin9x on 11/10/2015.
 */
public class AreaStoreDetailActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout lnlBasic, lnlAccessible, lnlPolite;
    private CustomTextViewNomal lblBasic, lblAccessible, lblPolite;
    private ImageView imgRight;
    private int currentFragmentIndex;
    View backBtn;

    @Override
    protected int getLayout() {
        return R.layout.activity_areastore_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        lblBasic = (CustomTextViewNomal) findViewById(R.id.lblBasic);
        lblAccessible = (CustomTextViewNomal) findViewById(R.id.lblAccessible);
        lblPolite = (CustomTextViewNomal) findViewById(R.id.lblPolite);
        lnlBasic = (LinearLayout) findViewById(R.id.lnlBasic);
        lnlAccessible = (LinearLayout) findViewById(R.id.lnlAccessible);
        lnlPolite = (LinearLayout) findViewById(R.id.lnlPolite);
        imgRight = (ImageView) findViewById(R.id.imgRight);
        backBtn = findViewById(R.id.imgLeft);

        currentFragmentIndex = 1;
    }

    @Override
    protected void registerListener() {
        backBtn.setOnClickListener(this);
        lnlBasic.setOnClickListener(this);
        lnlAccessible.setOnClickListener(this);
        lnlPolite.setOnClickListener(this);
    }

    private void hideButtonRight(boolean isShow) {
        if (isShow) {
            imgRight.setVisibility(View.VISIBLE);
        } else {
            imgRight.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        replaceFragment(currentFragmentIndex, false);
    }

    private void setColorBg(int i) {

        if (i == currentFragmentIndex) return;

        if (i == 1) {
            lnlBasic.setBackgroundColor(Color.parseColor("#0a5460"));
            lblBasic.setTextColor(Color.parseColor("#ffffff"));
            lnlAccessible.setBackgroundColor(Color.parseColor("#ffffff"));
            lblAccessible.setTextColor(Color.parseColor("#444444"));
            lnlPolite.setBackgroundColor(Color.parseColor("#ffffff"));
            lblPolite.setTextColor(Color.parseColor("#444444"));

            currentFragmentIndex = 1;
            replaceFragment(currentFragmentIndex, false);
        }
        if (i == 2) {
            lnlBasic.setBackgroundColor(Color.parseColor("#ffffff"));
            lblBasic.setTextColor(Color.parseColor("#444444"));
            lnlAccessible.setBackgroundColor(Color.parseColor("#0a5460"));
            lblAccessible.setTextColor(Color.parseColor("#ffffff"));
            lnlPolite.setBackgroundColor(Color.parseColor("#ffffff"));
            lblPolite.setTextColor(Color.parseColor("#444444"));
            currentFragmentIndex = 2;
            replaceFragment(currentFragmentIndex, false);
        }
        if (i == 3) {
            lnlBasic.setBackgroundColor(Color.parseColor("#ffffff"));
            lblBasic.setTextColor(Color.parseColor("#444444"));
            lnlAccessible.setBackgroundColor(Color.parseColor("#ffffff"));
            lblAccessible.setTextColor(Color.parseColor("#444444"));
            lnlPolite.setBackgroundColor(Color.parseColor("#0a5460"));
            lblPolite.setTextColor(Color.parseColor("#ffffff"));
            currentFragmentIndex = 3;
            replaceFragment(currentFragmentIndex, false);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgLeft:
                goback();
                break;
            case R.id.lnlBasic:
                setColorBg(1);
                break;
            case R.id.lnlAccessible:
                setColorBg(2);
                break;
            case R.id.lnlPolite:
                setColorBg(3);
                break;
            default:
                break;
        }
    }

    void goback() {
        finish();
    }

    //    public void replaceFragment(final int position, final Class<? extends Fragment> fragmentClazz, final boolean addTobackStack) {
    public void replaceFragment(final int position, final boolean addTobackStack) {
//        if (currentFragmentIndex == 1) {
//            ProgressDialogUtils.showProgressDialog(this, 0, 0);
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    currentFragmentIndex = position;
//                    if (position == 1) {
//                        hideButtonRight(true);
//                    } else {
//                        hideButtonRight(false);
//                    }
//                    openFragment(R.id.rlContainer, fragmentClazz, addTobackStack);
//                    ProgressDialogUtils.dismissProgressDialog();
//                }
//            }, 1000);
//        } else {
            currentFragmentIndex = position;
            if (position == 1) {
                hideButtonRight(true);
            } else {
                hideButtonRight(false);
            }
//            openFragment(R.id.rlContainer, fragmentClazz, addTobackStack);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        if (position == 1) {
            transaction.replace(R.id.rlContainer, AreaStoreDetail1Fragment.getInstance(), "");

        } else if (position == 2) {
            transaction.replace(R.id.rlContainer, AreaStoreDetail2Fragment.getInstance(), "");

        } else if (position == 3) {
            transaction.replace(R.id.rlContainer, AreaStoreDetail3Fragment.getInstance(), "");

        }

        transaction.commit();

//        }
    }

}
