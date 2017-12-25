package com.trams.joonggu_nubigo.view.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.dao.User;
import com.trams.joonggu_nubigo.dbmanager.DbUserUpdate;
import com.trams.joonggu_nubigo.network.NetworkUtils;
import com.trams.joonggu_nubigo.network.WebServiceConfig;
import com.trams.joonggu_nubigo.utils.PreferUtils;
import com.trams.joonggu_nubigo.utils.ProgressDialogUtils;
import com.trams.joonggu_nubigo.utils.Utils;
import com.trams.joonggu_nubigo.view.customview.CustomButtonBold;
import com.trams.joonggu_nubigo.view.customview.CustomEditTextNomal;
import com.trams.joonggu_nubigo.view.dialog.DialogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Administrator on 18/11/2015.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private CustomEditTextNomal edtUserName, edtPassword;
    private CustomButtonBold btnSubmit;
    private ImageView imgBack;
    private static final String TAG = LoginActivity.class.getName();

    @Override
    protected int getLayout() {
        return R.layout.layout_login;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        edtUserName = (CustomEditTextNomal) findViewById(R.id.edtLoginUserName);
        edtPassword = (CustomEditTextNomal) findViewById(R.id.edtLoginPassword);
        btnSubmit = (CustomButtonBold) findViewById(R.id.btnLoginSubmit);
        imgBack = (ImageView) findViewById(R.id.img_login_back);
    }

    @Override
    protected void registerListener() {
        btnSubmit.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {

        Utils.hideKeyBoard(LoginActivity.this);

        switch (v.getId()) {
            case R.id.btnLoginSubmit:

                if (edtUserName.getText().toString() == null || TextUtils.isEmpty(edtUserName.getText().toString())) {
                    DialogUtils.showConfirmAlertDialog(LoginActivity.this, "Please Input UserName",
                            new DialogUtils.ConfirmDialogListener() {
                                @Override
                                public void onConfirmClick() {

                                }
                            });

                } else if (edtPassword.getText().toString() == null || TextUtils.isEmpty(edtPassword.getText().toString())) {
                    DialogUtils.showConfirmAlertDialog(LoginActivity.this, "Please Input Password",
                            new DialogUtils.ConfirmDialogListener() {
                                @Override
                                public void onConfirmClick() {

                                }
                            });

                } else {
                    // call api to login
                    login();
                }

                break;

            case R.id.img_login_back:
                finish();
                break;
        }

    }

    private void login() {
        final JSONObject jsonRequest = new JSONObject();

        try {
            jsonRequest.put(WebServiceConfig.LOGIN_USERNAME, edtUserName.getText().toString());
            jsonRequest.put(WebServiceConfig.LOGIN_PASSWORD, Utils.md5(edtPassword.getText().toString()));
            jsonRequest.put(WebServiceConfig.LOGIN_ROLE, "0");
        } catch (Exception e) {
            e.printStackTrace();
        }

        NetworkUtils.postRequestVolley(LoginActivity.this, WebServiceConfig.URL_LOGIN, jsonRequest, new NetworkUtils.RequestResponse() {
            @Override
            public void onResponse(final JSONObject jsonResponse) {
                try {

//                    LogUtils.d(TAG, "sign up response : " + response);
//                    JSONObject jsonResponse = new JSONObject(response);
                    int code = jsonResponse.getInt("code");

                    if (code == 200) {
                        //login success -->
                        String msg = jsonResponse.getString("message");
                        try {

                            JSONObject jsonUser = jsonResponse.getJSONObject("value");

                            User userLogined = new User();
                            userLogined.setId(jsonUser.getInt("id"));
                            userLogined.setUsername(jsonUser.getString("username"));
//                            userLogined.setPassword(jsonUser.getString("password"));
                            userLogined.setPassword(Utils.md5(edtPassword.getText().toString()));
                            userLogined.setNickname(jsonUser.getString("nickname"));
                            userLogined.setFullname(jsonUser.getString("fullname"));
                            userLogined.setRole(jsonUser.getInt("role"));
                            userLogined.setSex(jsonUser.getString("sex"));
                            userLogined.setPhone(jsonUser.getString("phone"));
                            userLogined.setPhone(jsonUser.getString("email"));
                            userLogined.setScrap(jsonUser.getString("scrap"));
//                            userLogined.setAge(jsonUser.getInt("age"));
                            userLogined.setCreateDate(new Date(jsonUser.getLong("createDate")));
                            userLogined.setUpdateDate(new Date(jsonUser.getLong("updateDate")));

//                            int userId = jsonResponse.getJSONObject("value").getInt("id");
//                            String session = jsonResponse.getJSONObject("value").getString("sessionId");
//                            PreferUtils.saveSession(LoginActivity.this, session);
//                            LogUtils.d(TAG, "Session =" + session);

                            PreferUtils.setUserId(LoginActivity.this, (int) userLogined.getId());
                            PreferUtils.setLogin(LoginActivity.this, true);

                            //delete all data in user table , add only one row of user

                            DbUserUpdate dbUserUpdate = new DbUserUpdate(LoginActivity.this);
                            dbUserUpdate.deleteAll();
                            dbUserUpdate.insertUserLogin(userLogined);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        DialogUtils.showConfirmAlertDialog(LoginActivity.this, msg, new DialogUtils.ConfirmDialogListener() {
                            @Override
                            public void onConfirmClick() {
                                finish();
                            }
                        });
                    } else {
                        // login fail
                        String msg = jsonResponse.getString("message");
                        DialogUtils.showConfirmAlertDialog(LoginActivity.this, msg, new DialogUtils.ConfirmDialogListener() {
                            @Override
                            public void onConfirmClick() {

                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ProgressDialogUtils.dismissProgressDialog();
                    DialogUtils.showConfirmAlertDialog(LoginActivity.this, LoginActivity.this.getString(R.string.request_error_msg), new DialogUtils.ConfirmDialogListener() {
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
        }, true);


//        NetworkUtils.postRequest(getActivity(), WebServiceConfig.URL_LOGIN, jsonData, new NetworkUtils.RequestListener() {
//            @Override
//            public void onCompleted(String response) {
//                try {
//
//                    LogUtils.d(TAG, "sign up response : " + response);
//                    JSONObject jsonResponse = new JSONObject(response);
//                    int code = jsonResponse.getInt("code");
//
//                    if (code == 200) {
//                        //login success -->
//                        String msg = jsonResponse.getString("message");
//                        DialogUtils.showConfirmAlertDialog(getActivity(), msg, new DialogUtils.ConfirmDialogListener() {
//                            @Override
//                            public void onConfirmClick() {
//                                PreferUtils.setLogin(getActivity(), true);
//                                getActivity().onBackPressed();
//                            }
//                        });
//                    } else {
//                        // login fail
//                        String msg = jsonResponse.getString("message");
//                        DialogUtils.showConfirmAlertDialog(getActivity(), msg, new DialogUtils.ConfirmDialogListener() {
//                            @Override
//                            public void onConfirmClick() {
//
//                            }
//                        });
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    ProgressDialogUtils.dismissProgressDialog();
//                    DialogUtils.showConfirmAlertDialog(getActivity(), getActivity().getString(R.string.request_error_msg), new DialogUtils.ConfirmDialogListener() {
//                        @Override
//                        public void onConfirmClick() {
//
//                        }
//                    });
//                }
//            }
//        });
    }
}
