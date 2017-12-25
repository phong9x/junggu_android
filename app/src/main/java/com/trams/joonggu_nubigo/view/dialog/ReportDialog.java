package com.trams.joonggu_nubigo.view.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.dao.Store;
import com.trams.joonggu_nubigo.network.NetworkUtils;
import com.trams.joonggu_nubigo.network.WebServiceConfig;
import com.trams.joonggu_nubigo.utils.PreferUtils;
import com.trams.joonggu_nubigo.utils.ProgressDialogUtils;
import com.trams.joonggu_nubigo.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;

/**
 * Created by zin9x on 11/16/2015.
 */
public class ReportDialog extends FragmentBaseDialog implements View.OnClickListener {
    private ImageView imgRight;
    private EditText edtNumberPhone, edtContent;
    private LinearLayout lnlReport;
    private CheckBox ckbCheck;

    private String numberPhone, content, id;

    @Override
    protected int getLayoutResource() {
        return R.layout.dialog_report;
    }

    @Override
    protected void initViews(Bundle bundle, View view) {

        final TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);

        //numberPhone = Utils.prefixPhonenumber(tm.getLine1Number());
        //numberPhone = tm.getLine1Number();
        //Toast.makeText(this, "Phonenumber = " + numberPhone, Toast.LENGTH_SHORT).show();
        //numberPhone = "01685899892";

        numberPhone = tm.getLine1Number();


        imgRight = (ImageView) view.findViewById(R.id.imgRight);
        edtNumberPhone = (EditText) view.findViewById(R.id.edtNumberPhone);
        edtContent = (EditText) view.findViewById(R.id.edtContent);
        lnlReport = (LinearLayout) view.findViewById(R.id.lnlReport);
        ckbCheck = (CheckBox) view.findViewById(R.id.ckbCheck);
        edtNumberPhone.setText(numberPhone + "");
    }

    @Override
    protected void initVariables(Bundle bundle, View view) {
        EventBus.getDefault().registerSticky(this);
        imgRight.setOnClickListener(this);
        lnlReport.setOnClickListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(Store event) {
        id = String.valueOf(event.getId());
    }

    private void postRequestReport() {
        numberPhone = edtNumberPhone.getText().toString().trim();
        content = edtContent.getText().toString().trim();
        if (numberPhone.equalsIgnoreCase("") || content.equalsIgnoreCase("")) {
            Utils.toast(getActivity(), "Enter all information");
        } else if (!ckbCheck.isChecked()) {
            Utils.toast(getActivity(), "Agree to the Policy on the Treatment of Personal Information");
        } else {
            postReport();
        }
    }

    private void postReport() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(WebServiceConfig.PARAM_DETAIL, content);
            jsonObject.put(WebServiceConfig.PARAM_NUMBER_PHONE, numberPhone);
            jsonObject.put(WebServiceConfig.PARAM_ID, 0);
            jsonObject.put(WebServiceConfig.PARAM_STORE_ID, id);
            jsonObject.put("userId", PreferUtils.getUserId(getActivity()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetworkUtils.postRequestVolley(getActivity(), WebServiceConfig.URL_POST_REPORT, jsonObject, new NetworkUtils.RequestResponse() {
            @Override
            public void onResponse(JSONObject jsonResponse) {
                try {
                    int code = jsonResponse.getInt("code");

                    if (code == 200) {
                        AlertDialog.Builder a = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
                                .setMessage("Your report has been received successfully")
                                .setPositiveButton(R.string.btn_ok,
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                dismiss();
                                            }
                                        });

                        AlertDialog alert = a.create();
                        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                        alert.show();
                    } else {
                        String msg = jsonResponse.getString("message");
                        DialogUtils.showConfirmAlertDialog(getActivity(), msg, new DialogUtils.ConfirmDialogListener() {
                            @Override
                            public void onConfirmClick() {
                                dismiss();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ProgressDialogUtils.dismissProgressDialog();
                    DialogUtils.showConfirmAlertDialog(getActivity(), getString(R.string.request_error_msg), new DialogUtils.ConfirmDialogListener() {
                        @Override
                        public void onConfirmClick() {

                        }
                    });
                }
            }
        }, new NetworkUtils.RequestError() {
            @Override
            public void onError() {

            }
        },true);
    }

    private void toast(Activity activity, String text) {

    }

    @Override
    protected void initWindow(Bundle bundle, View view) {

    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgRight:
                dismiss();
                break;
            case R.id.lnlReport:
                postRequestReport();
                break;
            default:
                break;
        }
    }
}
