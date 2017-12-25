package com.trams.joonggu_nubigo.common;

import android.support.v4.app.Fragment;

import com.trams.joonggu_nubigo.dao.Store;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HuyDV on 17/11/2015.
 */
public class AppGlobal {

    public static List<Fragment> sFragmentList = new ArrayList();

    public static Store sStore = new Store();

    public static String sCurAreaName = "";

}
