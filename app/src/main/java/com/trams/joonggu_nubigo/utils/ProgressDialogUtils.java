package com.trams.joonggu_nubigo.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.trams.joonggu_nubigo.R;

/**
 * Created by ADMIN on 11/6/2015.
 */
public class ProgressDialogUtils {
    public static ProgressDialog progressDialog;
    public static final String TAG = ProgressDialogUtils.class.getName();

    public static void showProgressDialog(Context context, int titleID,
                                          int messageID) {
        LogUtils.d(TAG, "------- ProgressDialog showProgressDialog start ------");

        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }

        if(context == null) return;

        try {
            if (progressDialog != null) {
                if (!progressDialog.isShowing()) {
                    progressDialog.show();
                }
            } else {
                progressDialog = new ProgressDialog(context);
                progressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            progressDialog = null;
            showProgressDialog(context, titleID, messageID);
            return;
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setCancelable(false);
    }

    public static void dismissProgressDialog() {

        LogUtils.d(TAG, "------- ProgressDialog dismissProgressDialog start ------");

        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
