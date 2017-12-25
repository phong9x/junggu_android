package com.trams.joonggu_nubigo.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.trams.joonggu_nubigo.dao.Store;
import com.trams.joonggu_nubigo.maps.NMapPOIflagType;
import com.trams.joonggu_nubigo.parsers.StoreParser;

import java.util.Locale;

/**
 * Created by Administrator on 09/11/2015.
 */
public class NaverMapUtils {

    private static final String NAVER_PKG_NAME = "com.nhn.android.nmap";
    private static final String TAG = NaverMapUtils.class.getName();

    public static void openNaverMap(Context context, double longitude, double latitude) {
        try {
            LogUtils.d(TAG, "openNaverMap , longitude : " + longitude + " , latitude : " + latitude);
//            String uri = String.format(Locale.ENGLISH, "geo:%f,%f",37.591377,127.028095);
            String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
            PackageManager pm = context.getPackageManager();
            Intent intent = pm.getLaunchIntentForPackage(NAVER_PKG_NAME);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(uri));
            context.startActivity(intent);
        } catch (Exception e) {
            try {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + NAVER_PKG_NAME)));
            } catch (android.content.ActivityNotFoundException anfe) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + NAVER_PKG_NAME)));
            }
        }
    }

    public static int getMarkerId(Store store) {

        int markerId = 0;
        String mainType = StoreParser.getFieldList(store.getFieldList()).split(StoreParser.FIELD_PATTERN, -1)[0];

        if (mainType.equals("먹거리")) {
            markerId = NMapPOIflagType.FOOD;
        } else if (mainType.equals("관광지")) {
            markerId = NMapPOIflagType.SIGHT;
        } else if (mainType.equals("숙박")) {
            markerId = NMapPOIflagType.ACCOMMODATION;
        } else if (mainType.equals("쇼핑")) {
            markerId = NMapPOIflagType.SHOPPING;
        } else if (mainType.equals("생활")) {
            markerId = NMapPOIflagType.LIVING;
        } else {
            // if data of store have no field
            markerId = NMapPOIflagType.SIGHT;
        }
        return markerId;
    }

    public static int getMarkerIdFieldSelected(int fieldIdSelected) {
        int markerId = 0;
        switch (fieldIdSelected) {
            case 1:
                markerId = NMapPOIflagType.FOOD;
                break;
            case 2:
                markerId = NMapPOIflagType.SIGHT;
                break;
            case 3:
                markerId = NMapPOIflagType.ACCOMMODATION;
                break;
            case 4:
                markerId = NMapPOIflagType.SHOPPING;
                break;
            case 5:
                markerId = NMapPOIflagType.LIVING;
                break;
            default:
                markerId = NMapPOIflagType.SIGHT;
                break;
        }
        return markerId;
    }
}
