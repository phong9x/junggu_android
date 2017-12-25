package com.trams.joonggu_nubigo.view.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.trams.joonggu_nubigo.R;

/**
 * Created by zin9x on 11/16/2015.
 */
public abstract class FragmentBaseDialog extends DialogFragment {
    protected Context mContext;

    protected abstract int getLayoutResource();

    protected abstract void initViews(Bundle bundle, View view);

    protected abstract void initVariables(Bundle bundle, View view);

    protected abstract void initWindow(Bundle bundle, View view);

    protected abstract void initData(Bundle bundle);

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutResource(), container, false);
        setCancelable(false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyDialog);
        initWindow(savedInstanceState, rootView);
        initViews(savedInstanceState, rootView);
        initVariables(savedInstanceState, rootView);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }
}
