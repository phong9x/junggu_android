package com.trams.joonggu_nubigo.view.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.common.AppGlobal;
import com.trams.joonggu_nubigo.dao.Store;
import com.trams.joonggu_nubigo.dao.User;
import com.trams.joonggu_nubigo.dbmanager.DbUserUpdate;
import com.trams.joonggu_nubigo.network.NetworkUtils;
import com.trams.joonggu_nubigo.network.WebServiceConfig;
import com.trams.joonggu_nubigo.utils.LogUtils;
import com.trams.joonggu_nubigo.utils.PreferUtils;
import com.trams.joonggu_nubigo.utils.ProgressDialogUtils;
import com.trams.joonggu_nubigo.view.customview.CustomTextViewBold;
import com.trams.joonggu_nubigo.view.dialog.DialogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;

/**
 * Created by zin9x on 11/13/2015.
 */
public class CommentActivity extends BaseActivity implements View.OnClickListener {
    private RatingBar rtStart;
    private CustomTextViewBold tvRight;
    private EditText edtComment;
    private ImageView imgLeft;

    private String comment = "";
    private long storeId;
    private int numberGrade;
    private static final String TAG = CommentActivity.class.getName();

    @Override
    protected int getLayout() {
        return R.layout.activity_comment;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        rtStart = (RatingBar) findViewById(R.id.rtStart);
        tvRight = (CustomTextViewBold) findViewById(R.id.tvRight);
        edtComment = (EditText) findViewById(R.id.edtComment);
        imgLeft = (ImageView) findViewById(R.id.imgLeft);

    }

    @Override
    protected void registerListener() {
        tvRight.setOnClickListener(this);
        imgLeft.setOnClickListener(this);

        rtStart.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                int rate = (int) v;
                rtStart.setRating(rate);

            }
        });

    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        EventBus.getDefault().registerSticky(this);


    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(Store event) {
//        storeId = String.valueOf(event.getId());
    }


    private void login() {

        DbUserUpdate dbUserUpdate = new DbUserUpdate(this);
        User userLogIn = dbUserUpdate.getUserLogin();
        LogUtils.d(TAG, "CommentActivity : userLogin " + userLogIn.toString());

        final JSONObject jsonRequest = new JSONObject();

        try {
            jsonRequest.put(WebServiceConfig.LOGIN_USERNAME, userLogIn.getUsername());
            jsonRequest.put(WebServiceConfig.LOGIN_PASSWORD, userLogIn.getPassword());
            jsonRequest.put(WebServiceConfig.LOGIN_ROLE, "0");
        } catch (Exception e) {
            e.printStackTrace();
        }

        NetworkUtils.postRequestVolley(CommentActivity.this, WebServiceConfig.URL_LOGIN, jsonRequest, new NetworkUtils.RequestResponse() {
            @Override
            public void onResponse(final JSONObject jsonResponse) {
                try {
                    LogUtils.d(TAG, "CommentActivity login success");
                    int code = jsonResponse.getInt("code");

                    if (code == 200) {
                        //login success -->
                        String msg = jsonResponse.getString("message");
                        try {
                            String session = jsonResponse.getJSONObject("value").getString("sessionId");
                            PreferUtils.saveSession(CommentActivity.this, session);
                            LogUtils.d(TAG, "Session = " + session);

                            //after get session , start comment
                            postComment();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        // login fail
                        String msg = jsonResponse.getString("message");
                        DialogUtils.showConfirmAlertDialog(CommentActivity.this, getString(R.string.request_error_msg), new DialogUtils.ConfirmDialogListener() {
                            @Override
                            public void onConfirmClick() {

                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ProgressDialogUtils.dismissProgressDialog();
                    DialogUtils.showConfirmAlertDialog(CommentActivity.this, getString(R.string.request_error_msg), new DialogUtils.ConfirmDialogListener() {
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
        }, false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void postComment() {

        storeId = AppGlobal.sStore.getId();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(WebServiceConfig.PARAM_DETAIL, comment);
            jsonObject.put(WebServiceConfig.PARAM_STORE_ID, storeId);
            jsonObject.put(WebServiceConfig.PARAM_GRADE, numberGrade);
            jsonObject.put("id", 0);
            jsonObject.put("userId", PreferUtils.getUserId(this));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetworkUtils.postRequestVolley(this, WebServiceConfig.URL_POST_COMMENT, jsonObject, new NetworkUtils.RequestResponse() {
            @Override
            public void onResponse(JSONObject jsonResponse) {
                try {
                    LogUtils.d(TAG, "CommentActivity comment success");

                    int code = jsonResponse.getInt("code");

                    if (code == 200) {
                        //signup success -->
                        String msg = jsonResponse.getString("message");
                        DialogUtils.showConfirmAlertDialog(CommentActivity.this, msg, new DialogUtils.ConfirmDialogListener() {
                            @Override
                            public void onConfirmClick() {
                                finish();
                            }
                        });
                    } else {
                        String msg = jsonResponse.getString("message");
                        DialogUtils.showConfirmAlertDialog(CommentActivity.this, msg, new DialogUtils.ConfirmDialogListener() {
                            @Override
                            public void onConfirmClick() {

                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ProgressDialogUtils.dismissProgressDialog();
                    DialogUtils.showConfirmAlertDialog(CommentActivity.this, getString(R.string.request_error_msg), new DialogUtils.ConfirmDialogListener() {
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvRight:
                comment = edtComment.getText().toString().trim();
                numberGrade = (int) rtStart.getRating();
                if (comment.equalsIgnoreCase("")) {
                    DialogUtils.showConfirmAlertDialog(CommentActivity.this, getString(R.string.comment_error_content), new DialogUtils.ConfirmDialogListener() {
                        @Override
                        public void onConfirmClick() {

                        }
                    });
                } else if (numberGrade == 0) {
                    DialogUtils.showConfirmAlertDialog(CommentActivity.this, getString(R.string.comment_error_grade), new DialogUtils.ConfirmDialogListener() {
                        @Override
                        public void onConfirmClick() {

                        }
                    });
                } else {
                    login();
                }
                break;
            case R.id.imgLeft:
                finish();
                break;
            default:
                break;
        }
    }
}
