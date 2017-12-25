package com.trams.joonggu_nubigo.utils;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Administrator on 13/11/2015.
 */
public class PxUtils {
    private static final String TAG = PxUtils.class.getName();

//    public static float convertDpToPixel(float dp, Context context){
//        Resources resources = context.getResources();
//        DisplayMetrics metrics = resources.getDisplayMetrics();
//        float px = dp * (metrics.densityDpi / 160f);
//        LogUtils.d(TAG,"convertDpToPixel : " + px);
//        return px;
//    }

    public static float dpFromPx(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static int dpToPx(Context context, float dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        LogUtils.d(TAG, "dpToPx : " + px);
        return px;
    }

    public static int pxFromDpHuy(final Context context, final float dp) {
//        int padding_in_dp = 6;  // 6 dps
        final float scale = context.getResources().getDisplayMetrics().density;
        int padding_in_px = (int) (dp * scale + 0.5f);
        return padding_in_px;
    }


    public static int pxToDp(Context context, float px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        LogUtils.d(TAG, "pxToDp : " + px);
        return dp;
    }


//    public static float convertPixelsToDp(float px, Context context){
//        Resources resources = context.getResources();
//        DisplayMetrics metrics = resources.getDisplayMetrics();
//        float dp = px / (metrics.densityDpi / 160f);
//        LogUtils.d(TAG,"convertPixelsToDp : " + dp);
//        return dp;
//    }

    public static int getWidhtScreen(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        LogUtils.d(TAG, "getWidhtScreen : " + width);
        return width;
    }

    public static int getHeightScreen(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        LogUtils.d(TAG, "getHeightScreen : " + height);
        return height;
    }

}
