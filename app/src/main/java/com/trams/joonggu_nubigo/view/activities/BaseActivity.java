package com.trams.joonggu_nubigo.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.common.AppGlobal;

/**
 * Created by Administrator on 27/10/2015.
 */
public abstract class BaseActivity extends FragmentActivity {
    FragmentManager fragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        fragmentManager = getSupportFragmentManager();
        initViews(savedInstanceState);
        registerListener();
        initVariables(savedInstanceState);
    }

    protected abstract int getLayout();

    protected abstract void initViews(Bundle savedInstanceState);

    protected abstract void registerListener();

    protected abstract void initVariables(Bundle savedInstanceState);

    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void openFragment(Fragment fragment, boolean isAddToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.tabContent, fragment);
        if (isAddToBackStack == true) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        } else {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.rlContainer, fragment);
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }

    public void openFragment(int resId, Class<? extends Fragment> fragmentClazz, boolean addBackStack) {
        openFragment(resId, fragmentClazz, null, addBackStack);
    }

    /*public void openFragment(int resId, Class<? extends Fragment> fragmentClazz, Bundle bundle, boolean addBackStack) {
        try {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            String tag = fragmentClazz.getName();
            Fragment fragment = manager.findFragmentByTag(tag);

            hideAllFragment(transaction);

            if (fragment == null) {
                fragment = fragmentClazz.newInstance();
                if (bundle != null) {
                    fragment.setArguments(bundle);
                }
                //transaction.add(resId, fragment, tag);
                if (addBackStack) {
                    transaction.addToBackStack(tag);
                }
                //AppGlobal.sFragmentList.add(fragment);
            } *//*else {
                transaction.show(fragment);
            }*//*
            transaction.replace(resId, fragment, tag);

            transaction.commit();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }*/

    public void openFragment(int resId, Class<? extends Fragment> fragmentClazz, Bundle args, boolean addBackStack) {
        FragmentManager manager = getSupportFragmentManager();
        String tag = fragmentClazz.getName();
        try {
            boolean isExisted = manager.popBackStackImmediate(tag, 0);    // IllegalStateException
            if (!isExisted) {
                Fragment fragment;
                try {
                    fragment = fragmentClazz.newInstance();
                    if (args != null) {
                        fragment.setArguments(args);
                    }
                    FragmentTransaction transaction = manager.beginTransaction();
                    //transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                    transaction.replace(resId, fragment, tag);

                    if (addBackStack) {
                        transaction.addToBackStack(tag);
                    }
                    transaction.commitAllowingStateLoss();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideAllFragment(FragmentTransaction transaction) {
        for (Fragment fragment : AppGlobal.sFragmentList) {
            transaction.hide(fragment);
        }
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            fragmentManager.popBackStack();
        }
    }

    void showPopup() {
        EditText searchPopupText = (EditText) findViewById(R.id.search_box_edit_text);
        View popup = findViewById(R.id.popup_container);
        searchPopupText.setText("");
        popup.setVisibility(View.VISIBLE);
    }

    public void setAutoFocusPopupSearch() {
        EditText editText = (EditText) findViewById(R.id.search_box_edit_text);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

}
