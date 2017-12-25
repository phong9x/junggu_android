package com.trams.joonggu_nubigo.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.common.AppGlobal;
import com.trams.joonggu_nubigo.utils.Utils;
import com.trams.joonggu_nubigo.view.customview.CustomTextViewBold;
import com.trams.joonggu_nubigo.view.customview.CustomTextViewNomal;

/**
 * Created by huydv0109 on 31/08/2015.
 */
public abstract class BaseFragment extends Fragment{

    private static final String TAG = BaseFragment.class.getSimpleName();

    public View headerSearchBtn, headerBackBtn, headerShowMapBtn;
    public CustomTextViewNomal headerCountTv, headerSearchEditText;
    public CustomTextViewBold headerTitleTv;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        initView(view, savedInstanceState);
        initHeader(view);
        return view;

    }


    @Override
    public void onStart() {
        super.onStart();
        initVaribles();
        registerListener();
    }

    protected abstract int getLayout();

    protected abstract void initView(View view, Bundle savedInstanceState);

    protected abstract void registerListener();

    protected abstract void initVaribles();


    public void showDialog(DialogFragment fragment, String tag) {
        FragmentManager fm = getChildFragmentManager();
        fragment.show(fm, tag);
    }

    public void showDialog(DialogFragment fragment, Bundle bundle, String tag) {
        FragmentManager fm = getChildFragmentManager();
        fragment.setArguments(bundle);
        fragment.show(fm, tag);
    }

    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(getActivity(), cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void startActivity(Class<?> cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }

    public void addFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.tabContent, fragment);
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }

    public void openFragment(int resId, Class<? extends Fragment> fragmentClazz, boolean addBackStack) {
        openFragment(resId, fragmentClazz, null, addBackStack);
    }

    /*public void openFragment(int resId, Class<? extends Fragment> fragmentClazz, Bundle bundle, boolean addBackStack) {
        try {
            FragmentManager manager = getActivity().getSupportFragmentManager();
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
        FragmentManager manager = getActivity().getSupportFragmentManager();
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
                } catch (java.lang.InstantiationException e) {
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

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.tabContent, fragment);
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }

    public void setupHeader(boolean showPos1, boolean showNormalPos2, boolean showExtensionPos2, boolean showPos3, boolean showPos4) {
        ImageView pos1 = (ImageView) getActivity().findViewById(R.id.header_pos1_img);
        View extensionPos2 = getActivity().findViewById(R.id.header_extension_pos2);
        View normalPos2 = getActivity().findViewById(R.id.header_normal_pos2);
        ImageView pos3 = (ImageView) getActivity().findViewById(R.id.header_show_map_btn_pos3);
        ImageView pos4 = (ImageView) getActivity().findViewById(R.id.header_search_img_pos4);

        if (showPos1 == true) {
            pos1.setVisibility(View.VISIBLE);
        } else {
            pos1.setVisibility(View.GONE);
        }

        if (showNormalPos2 == true) {
            normalPos2.setVisibility(View.VISIBLE);
        } else {
            normalPos2.setVisibility(View.GONE);
        }

        if (showExtensionPos2 == true) {
            extensionPos2.setVisibility(View.VISIBLE);
        } else {
            extensionPos2.setVisibility(View.GONE);
        }

        if (showPos3 == true) {
            pos3.setVisibility(View.VISIBLE);
        } else {
            pos3.setVisibility(View.GONE);
        }

        if (showPos4 == true) {
            pos4.setVisibility(View.VISIBLE);
        } else {
            pos4.setVisibility(View.GONE);
        }

    }

    public void setupHeader(boolean showPos1, int pos1Res, boolean showNormalPos2, String textTitleNormalPos2, String textCountNormalPos2, boolean showExtensionPos2, boolean showPos3, boolean showPos4, boolean isSearch, String enteredText) {
        ImageView pos1 = (ImageView) getActivity().findViewById(R.id.header_pos1_img);
        View extensionPos2 = getActivity().findViewById(R.id.header_extension_pos2);
        View normalPos2 = getActivity().findViewById(R.id.header_normal_pos2);
        View searchPos2 = getActivity().findViewById(R.id.header_search_pos2);
        CustomTextViewNomal titleNormalPos2 = (CustomTextViewNomal) getActivity().findViewById(R.id.header_area_name_pos2);
        CustomTextViewNomal countNormalPos2 = (CustomTextViewNomal) getActivity().findViewById(R.id.header_area_count_pos2);
        CustomTextViewNomal enterText = (CustomTextViewNomal) getActivity().findViewById(R.id.header_search_et_pos2);
        ImageView pos3 = (ImageView) getActivity().findViewById(R.id.header_show_map_btn_pos3);
        ImageView pos4 = (ImageView) getActivity().findViewById(R.id.header_search_img_pos4);

        if (showPos1 == true) {
            pos1.setVisibility(View.VISIBLE);
            pos1.setImageResource(pos1Res);
        } else {
            pos1.setVisibility(View.GONE);
        }

        if (showNormalPos2 == true) {
            normalPos2.setVisibility(View.VISIBLE);
            titleNormalPos2.setVisibility(View.VISIBLE);
            countNormalPos2.setVisibility(View.VISIBLE);
        } else {
            normalPos2.setVisibility(View.GONE);
        }

        if (isSearch == true) {
            normalPos2.setVisibility(View.GONE);
            searchPos2.setVisibility(View.VISIBLE);
        } else {
            normalPos2.setVisibility(View.VISIBLE);
            searchPos2.setVisibility(View.GONE);
        }

        if (showExtensionPos2 == true) {
            extensionPos2.setVisibility(View.VISIBLE);
            searchPos2.setVisibility(View.GONE);
        } else {
            extensionPos2.setVisibility(View.GONE);
        }

        if (showPos3 == true) {
            pos3.setVisibility(View.VISIBLE);
        } else {
            pos3.setVisibility(View.GONE);
        }

        if (showPos4 == true) {
            pos4.setVisibility(View.VISIBLE);
        } else {
            pos4.setVisibility(View.GONE);
        }

        if (textTitleNormalPos2 != null && !textTitleNormalPos2.equals("")) {
            titleNormalPos2.setText(textTitleNormalPos2);
        } else {
            titleNormalPos2.setVisibility(View.GONE);
        }

        if (textCountNormalPos2 != null && !textCountNormalPos2.equals("")) {
            countNormalPos2.setText("(" + textCountNormalPos2 + ")");
        } else {
            countNormalPos2.setVisibility(View.GONE);
        }

        if (enteredText != null && !enteredText.equals("")) {
            enterText.setText(enteredText);
        }

        if (showNormalPos2 == false && textTitleNormalPos2.equals("") && textCountNormalPos2.equals("") && showExtensionPos2 == true) {
            normalPos2.setVisibility(View.GONE);
        }

    }

    public void setupHeaderMap(boolean isShowHeaderPos1, int headerPostRes, boolean headerNomalPos2layout, boolean isHeaderAreaNamePos2, String txtHeaderAreaNamePos2,
                               boolean isHeaderAreaCountPos2, String strHeaderAreaCountPos2, boolean isHeaderSearchPos2, String strHeaderSearchPos2,
                               boolean isHeaderExtendsionPos2,

                               boolean isHeaderShowMapBtnPos3, int resHeaderShowMapBtnPos3,
                               boolean isHeaderSearchImgPos4, int resHeaderSearchImgPos4

    ) {

        ImageView pos1 = (ImageView) getActivity().findViewById(R.id.header_pos1_img);

        LinearLayout headerNomalPos2Layout = (LinearLayout) getActivity().findViewById(R.id.header_normal_pos2);
        CustomTextViewNomal tvHeaderAreaNamePos2 = (CustomTextViewNomal) getActivity().findViewById(R.id.header_area_name_pos2);
        CustomTextViewNomal tvHeaderAreaCountPos2 = (CustomTextViewNomal) getActivity().findViewById(R.id.header_area_count_pos2);

        LinearLayout headerSearchPos2Layout = (LinearLayout) getActivity().findViewById(R.id.header_search_pos2);
        CustomTextViewNomal tvHeaderSearchEtPos2 = (CustomTextViewNomal) getActivity().findViewById(R.id.header_search_et_pos2);

        LinearLayout headerExtendsionPos2Layout = (LinearLayout) getActivity().findViewById(R.id.header_extension_pos2);
        ImageView imgHeaderShowMapBtnPos3 = (ImageView) getActivity().findViewById(R.id.header_show_map_btn_pos3);
        ImageView imgHeaderSearchImgPos4 = (ImageView) getActivity().findViewById(R.id.header_search_img_pos4);


        if (isShowHeaderPos1) {
            pos1.setVisibility(View.VISIBLE);
            pos1.setImageResource(headerPostRes);
        } else {
            pos1.setVisibility(View.GONE);
        }

        if (headerNomalPos2layout) {
            headerNomalPos2Layout.setVisibility(View.VISIBLE);
        } else {
            headerNomalPos2Layout.setVisibility(View.GONE);
        }

        if (isHeaderAreaNamePos2) {
            tvHeaderAreaNamePos2.setVisibility(View.VISIBLE);
            tvHeaderAreaNamePos2.setText(txtHeaderAreaNamePos2);

        } else {
            tvHeaderAreaNamePos2.setVisibility(View.GONE);
        }

        if (isHeaderAreaCountPos2) {
            tvHeaderAreaCountPos2.setVisibility(View.VISIBLE);
            tvHeaderAreaCountPos2.setText(strHeaderAreaCountPos2);
        } else {
            tvHeaderAreaCountPos2.setVisibility(View.GONE);
        }

        if (isHeaderSearchPos2) {
            headerSearchPos2Layout.setVisibility(View.VISIBLE);
            tvHeaderSearchEtPos2.setText(strHeaderSearchPos2);
        } else {
            headerSearchPos2Layout.setVisibility(View.GONE);
        }

        if (isHeaderExtendsionPos2) {
            headerExtendsionPos2Layout.setVisibility(View.VISIBLE);
        } else {
            headerExtendsionPos2Layout.setVisibility(View.GONE);
        }

        if (isHeaderShowMapBtnPos3) {
            imgHeaderShowMapBtnPos3.setVisibility(View.VISIBLE);
            imgHeaderShowMapBtnPos3.setImageResource(resHeaderShowMapBtnPos3);
        } else {
            imgHeaderShowMapBtnPos3.setVisibility(View.GONE);
        }

        if (isHeaderSearchImgPos4) {
            imgHeaderSearchImgPos4.setVisibility(View.VISIBLE);
            imgHeaderSearchImgPos4.setImageResource(resHeaderSearchImgPos4);
        } else {
            imgHeaderSearchImgPos4.setVisibility(View.GONE);
        }


    }

    public void showBottomTabBar(boolean isShow) {
        if (isShow == true) {
            getActivity().findViewById(R.id.tab_bottom_container).setVisibility(View.VISIBLE);
        } else {
            getActivity().findViewById(R.id.tab_bottom_container).setVisibility(View.GONE);
        }
    }

    public void showHeader(boolean isShow) {
        if (isShow == true) {
            getActivity().findViewById(R.id.header_container).setVisibility(View.VISIBLE);
        } else {
            getActivity().findViewById(R.id.header_container).setVisibility(View.GONE);
        }
    }

    public void setHideKeyBoardOnTouch() {
        getView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Utils.hideKeyBoard(getActivity());
                return true;
            }
        });
    }

    void showPopup() {
        EditText searchPopupText = (EditText) getActivity().findViewById(R.id.search_box_edit_text);
        View popup = getActivity().findViewById(R.id.popup_container);
        searchPopupText.setText("");
        popup.setVisibility(View.VISIBLE);
    }

    void initHeader(View view) {
        headerBackBtn = view.findViewById(R.id.header_back_btn);
        headerSearchBtn = view.findViewById(R.id.header_search_btn);
        headerShowMapBtn = view.findViewById(R.id.header_show_map_btn);
        headerTitleTv = (CustomTextViewBold) view.findViewById(R.id.header_title_text_view);
        headerCountTv = (CustomTextViewNomal) view.findViewById(R.id.header_count_text_view);
        headerSearchEditText = (CustomTextViewNomal) view.findViewById(R.id.header_search_edit_text);
    }

    public void setAutoFocusPopupSearch() {
        EditText editText = (EditText) getActivity().findViewById(R.id.search_box_edit_text);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

}
