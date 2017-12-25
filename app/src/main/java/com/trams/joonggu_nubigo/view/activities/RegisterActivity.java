package com.trams.joonggu_nubigo.view.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
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
import com.trams.joonggu_nubigo.view.customview.CustomTextViewBold;
import com.trams.joonggu_nubigo.view.customview.CustomTextViewNomal;
import com.trams.joonggu_nubigo.view.dialog.DialogUtils;
import com.trams.joonggu_nubigo.view.dialog.PolicyDialog;
import com.trams.joonggu_nubigo.view.dialog.TermDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Administrator on 18/11/2015.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private CustomEditTextNomal edtUserName, edtPassword, edtConfirmPassword;
    private CheckBox cbTeamAndPolicy;
    private CustomButtonBold btnSubmit;
    private CustomTextViewNomal tvAgree, tvEnd;
    private CustomTextViewBold tvTerm, tvPolicy, tvNecessary;
    private ImageView imgBack;
    private static final String TAG = RegisterActivity.class.getName();

    @Override
    protected int getLayout() {
        return R.layout.layout_register;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        edtUserName = (CustomEditTextNomal) findViewById(R.id.edtLoginJoinUserName);
        edtPassword = (CustomEditTextNomal) findViewById(R.id.edtLoginJoinPassword);
        edtConfirmPassword = (CustomEditTextNomal) findViewById(R.id.edtJoinConfirmPassword);

        cbTeamAndPolicy = (CheckBox) findViewById(R.id.cbTeamPolicy);

        btnSubmit = (CustomButtonBold) findViewById(R.id.btnLoginJoinSubmit);
        tvTerm = (CustomTextViewBold) findViewById(R.id.tvLoginJoinTerm);
        tvPolicy = (CustomTextViewBold) findViewById(R.id.tvLoginJoinPolicy);
        imgBack = (ImageView) findViewById(R.id.img_register_back);
        tvEnd = (CustomTextViewNomal) findViewById(R.id.tvLoginAnd);
        tvAgree = (CustomTextViewNomal) findViewById(R.id.tvLoginJoinAgree);
        tvNecessary = (CustomTextViewBold) findViewById(R.id.tvLoginJoinNecessary);
    }

    @Override
    protected void registerListener() {
        btnSubmit.setOnClickListener(this);
        tvTerm.setOnClickListener(this);
        tvPolicy.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {

    }


    @Override
    public void onClick(View v) {

        Utils.hideKeyBoard(this);
        switch (v.getId()) {
            case R.id.btnLoginJoinSubmit:
                if (TextUtils.isEmpty(edtUserName.getText().toString()) || TextUtils.isEmpty(edtPassword.getText().toString()) || TextUtils.isEmpty(edtConfirmPassword.getText().toString())) {

                    DialogUtils.showConfirmAlertDialog(this, getString(R.string.fragment_login_join_user_pass_null_err_msg),
                            new DialogUtils.ConfirmDialogListener() {
                                @Override
                                public void onConfirmClick() {

                                }
                            });

                } else if (!cbTeamAndPolicy.isChecked()) {
                    DialogUtils.showConfirmAlertDialog(this, getString(R.string.fragment_login_join_term_policy_err_msg),
                            new DialogUtils.ConfirmDialogListener() {
                                @Override
                                public void onConfirmClick() {

                                }
                            });
                } else if (!edtPassword.getText().toString().equals(edtConfirmPassword.getText().toString())) {
                    DialogUtils.showConfirmAlertDialog(this, "Password and Confirm password do not match",
                            new DialogUtils.ConfirmDialogListener() {
                                @Override
                                public void onConfirmClick() {

                                }
                            });
                } else {
                    //api
                    signUp();

                }
                break;
            case R.id.tvLoginJoinTerm:
                TermDialog.getInstance(RegisterActivity.this).showView();
                break;
            case R.id.tvLoginJoinPolicy:
                PolicyDialog.getInstance(RegisterActivity.this).showView();
                break;
            case R.id.img_register_back:
                finish();
                break;

        }
    }

    private void signUp() {

        final JSONObject jsonRequest = new JSONObject();

        try {
            jsonRequest.put(WebServiceConfig.SIGNUP_ID, "");
            jsonRequest.put(WebServiceConfig.SIGNUP_USERNAME, edtUserName.getText().toString());
            jsonRequest.put(WebServiceConfig.SIGNUP_PASSWORD, Utils.md5(edtPassword.getText().toString()));
            jsonRequest.put(WebServiceConfig.SIGNUP_NICKNAME, "");
            jsonRequest.put(WebServiceConfig.SIGNUP_FULLNAME, "");
            jsonRequest.put(WebServiceConfig.SIGNUP_ROLE, "");
            jsonRequest.put(WebServiceConfig.SIGNUP_SEX, "");
            jsonRequest.put(WebServiceConfig.SIGNUP_PHONE, "");
            jsonRequest.put(WebServiceConfig.SIGNUP_EMAIL, "");
            jsonRequest.put(WebServiceConfig.SIGNUP_AGE, "");
            jsonRequest.put(WebServiceConfig.SINGNUP_UPDATE_DATE, "");
            jsonRequest.put(WebServiceConfig.SIGNUP_CREATE_DATE, "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        NetworkUtils.postRequestVolley(this, WebServiceConfig.URL_SIGNUP, jsonRequest, new NetworkUtils.RequestResponse() {
            @Override
            public void onResponse(final JSONObject jsonResponse) {
                try {
                    int code = jsonResponse.getInt("code");

                    if (code == 200) {
                        //signup success -->
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

                            //save to preference
                            PreferUtils.setLogin(RegisterActivity.this, true);
                            PreferUtils.setUserId(RegisterActivity.this, (int) userLogined.getId());

                            //delete all data in user table , add only one row of user

                            DbUserUpdate dbUserUpdate = new DbUserUpdate(RegisterActivity.this);
                            dbUserUpdate.deleteAll();
                            dbUserUpdate.insertUserLogin(userLogined);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        DialogUtils.showConfirmAlertDialog(RegisterActivity.this, msg, new DialogUtils.ConfirmDialogListener() {
                            @Override
                            public void onConfirmClick() {
//                                PreferUtils.setLogin(RegisterActivity.this, true);
                                finish();

//                                int userId = 0;
//                                try {
//                                    userId = jsonResponse.getJSONObject("value").getInt("id");
//                                    PreferUtils.setUserId(RegisterActivity.this, userId);
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }

                            }
                        });
                    } else {
                        String msg = jsonResponse.getString("message");
                        DialogUtils.showConfirmAlertDialog(RegisterActivity.this, msg, new DialogUtils.ConfirmDialogListener() {
                            @Override
                            public void onConfirmClick() {

                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ProgressDialogUtils.dismissProgressDialog();
                    DialogUtils.showConfirmAlertDialog(RegisterActivity.this, RegisterActivity.this.getString(R.string.request_error_msg), new DialogUtils.ConfirmDialogListener() {
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

//        NetworkUtils.postRequest(getActivity(), WebServiceConfig.URL_SIGNUP, jsonRequest, new NetworkUtils.RequestListener() {
//            @Override
//            public void onCompleted(String response) {
//                try {
//
//                    LogUtils.d(TAG, "sign up response : " + response);
//                    JSONObject jsonResponse = new JSONObject(response);
//
//                    int code = jsonResponse.getInt("code");
//
//                    if (code == 200) {
//                        //signup success -->
//                        String msg = jsonResponse.getString("message");
//                        DialogUtils.showConfirmAlertDialog(getActivity(), msg, new DialogUtils.ConfirmDialogListener() {
//                            @Override
//                            public void onConfirmClick() {
//                                PreferUtils.setLogin(getActivity(), true);
//                                getActivity().onBackPressed();
//                            }
//                        });
//                    } else {
//                        String msg = jsonResponse.getString("message");
//                        DialogUtils.showConfirmAlertDialog(getActivity(), msg, new DialogUtils.ConfirmDialogListener() {
//                            @Override
//                            public void onConfirmClick() {
//
//                            }
//                        });
//                    }
//
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
