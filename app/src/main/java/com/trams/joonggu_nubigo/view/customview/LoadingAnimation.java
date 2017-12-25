package com.trams.joonggu_nubigo.view.customview;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.utils.LogUtils;

public class LoadingAnimation extends DialogFragment {

	public static final String FRAGMENT_TAG = "LoadingAnimation";
	private static final String TAG = LoadingAnimation.class.getSimpleName();
	private static boolean isShowing;

	public static void show(FragmentManager fm) {
		LogUtils.d(TAG, "Show loading, isShowing: " + isShowing);
		if (fm.findFragmentByTag(FRAGMENT_TAG) == null && !isShowing) {
			LogUtils.d(TAG, "loadingAnimation.show(fm, FRAGMENT_TAG)");
			LoadingAnimation loadingAnimation = new LoadingAnimation();
			loadingAnimation.show(fm, FRAGMENT_TAG);
			isShowing = true;
		}
	}

	public static void hide(FragmentManager fm) {
		LogUtils.d(TAG, "Hide loading");
		Fragment fragment = fm.findFragmentByTag(FRAGMENT_TAG);
		if (fragment != null && fragment instanceof DialogFragment) {
			LogUtils.d(TAG, "((DialogFragment) fragment).dismiss();");
			((DialogFragment) fragment).dismiss();
		}
	}

	public LoadingAnimation() {
		setStyle(R.style.Dialog_No_Border, R.style.Dialog_No_Border);
		setCancelable(false);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.loading_animation_layout, null);
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);
		isShowing = false;
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
