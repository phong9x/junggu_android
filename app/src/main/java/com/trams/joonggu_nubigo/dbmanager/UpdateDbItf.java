package com.trams.joonggu_nubigo.dbmanager;

/**
 * Created by Administrator on 11/11/2015.
 */
public class UpdateDbItf {

    public interface OnResponseSuccess {
        public void onResponseSuccess();
    }

    public interface OnResponseFail {
        public void onResponseFail();
    }

}
