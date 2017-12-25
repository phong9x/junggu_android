package com.trams.joonggu_nubigo.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RatingBar;

import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.common.AppGlobal;
import com.trams.joonggu_nubigo.dao.Store;
import com.trams.joonggu_nubigo.network.NetworkUtils;
import com.trams.joonggu_nubigo.network.WebServiceConfig;
import com.trams.joonggu_nubigo.objects.CommentObj;
import com.trams.joonggu_nubigo.utils.LogUtils;
import com.trams.joonggu_nubigo.utils.PreferUtils;
import com.trams.joonggu_nubigo.utils.ProgressDialogUtils;
import com.trams.joonggu_nubigo.view.activities.CommentActivity;
import com.trams.joonggu_nubigo.view.adapters.CommentAdapter;
import com.trams.joonggu_nubigo.view.customview.CustomTextViewNomal;
import com.trams.joonggu_nubigo.view.dialog.DialogUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by zin9x on 11/10/2015.
 */
public class AreaStoreDetail3Fragment extends BaseFragment implements View.OnClickListener {

    View createBtn;
    private ListView lsvComment;
    private CommentAdapter commentAdapter;
    private ArrayList<CommentObj> commentObjs;
    private long id;
    private static final String TAG = AreaStoreDetail3Fragment.class.getName();
    private int page = 1;
    boolean loadingMore = false;
    private boolean isStopLoadingMore = true;
    private CustomTextViewNomal tvScore, tvTotalRate;
    private RatingBar rbStore;

    private static AreaStoreDetail3Fragment instance;

    public static AreaStoreDetail3Fragment getInstance() {
        if (instance == null) {
            instance = new AreaStoreDetail3Fragment();
        }
        return instance;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_area_store_detail_3;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        createBtn = view.findViewById(R.id.area_store_detail3_create_button);
        lsvComment = (ListView) view.findViewById(R.id.lsvComment);
        commentObjs = new ArrayList<CommentObj>();
        commentAdapter = new CommentAdapter(getActivity(), commentObjs);
        lsvComment.setAdapter(commentAdapter);

        tvScore = (CustomTextViewNomal) view.findViewById(R.id.tv_score_store);
        tvTotalRate = (CustomTextViewNomal) view.findViewById(R.id.tv_total_rate);

        rbStore = (RatingBar) view.findViewById(R.id.rb_store_comment);
    }

    @Override
    public void onResume() {
        super.onResume();
        getListCommnet(page);
    }

    @Override
    protected void registerListener() {

        createBtn.setOnClickListener(this);

        lsvComment.setOnScrollListener(new AbsListView.OnScrollListener() {
                                           @Override
                                           public void onScrollStateChanged(AbsListView view, int scrollState) {

                                           }

                                           @Override
                                           public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                                               LogUtils.d(TAG, "onScroll , firstVisibleItem : " + firstVisibleItem + " , visibleItemCount : " + visibleItemCount + " , totalItemCount : " + totalItemCount);
                                               int lastInScreen = firstVisibleItem + visibleItemCount;
                                               if ((lastInScreen == totalItemCount && !(loadingMore)) && totalItemCount != 0 && isStopLoadingMore) {
                                                   LogUtils.d(TAG, "onScroll , lastInScreen == totalItemCount = " + lastInScreen);
                                                   page++;
                                                   getListCommnet(page);
                                               }
                                           }
                                       }
        );

    }

    @Override
    protected void initVaribles() {
        EventBus.getDefault().registerSticky(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(Store event) {

    }

    private void getListCommnet(final int page) {
        id = AppGlobal.sStore.getId();
        LogUtils.d(TAG, "getListComment start");

        final JSONObject jsonRequest = new JSONObject();
        loadingMore = true;

        //fake data
//        NetworkUtils.getRequestVolley(getActivity(), WebServiceConfig.URL_GET_LIST_COMMENT + "/" + 1 + "/" + page, jsonRequest, new NetworkUtils.RequestResponse() {

        NetworkUtils.getRequestVolley(getActivity(), WebServiceConfig.URL_GET_LIST_COMMENT + "/" + id + "/" + page, jsonRequest, new NetworkUtils.RequestResponse() {
            @Override
            public void onResponse(JSONObject jsonResponse) {
                try {
                    int code = jsonResponse.getInt("code");

                    if (code == 200) {
                        //signup success -->
//                        String msg = jsonResponse.getString("message");
//                        DialogUtils.showConfirmAlertDialog(getActivity(), msg, new DialogUtils.ConfirmDialogListener() {
//                            @Override
//                            public void onConfirmClick() {
//
//                            }
//                        });

                        Log.d(TAG, "AreaStoreDetail3Fragment , data json response : " + jsonResponse.toString());

                        JSONObject jsonData = jsonResponse.getJSONObject("value");

                        double totalUserRate = jsonData.getDouble("total");
                        double totalGrade = jsonData.getDouble("grade");

                        tvTotalRate.setText((int) totalGrade + "명");

                        if (totalUserRate != 0) {
                            double fScore = totalGrade / totalUserRate;
                            Log.d(TAG, "fScore : " + fScore);
                            tvScore.setText(String.format("%.1f", fScore));
                            rbStore.setRating((float) fScore);
                        }

//                        int intScore = Math.round(score);

//                        float fScore = new Float(Math.round(score));
//                        int intScore = totalGrade / totalUserRate;

                        JSONArray cmtJsonArray = jsonData.getJSONArray("list_comment");

                        for (int i = 0; i < cmtJsonArray.length(); i++) {
                            JSONObject jsonComment = cmtJsonArray.getJSONObject(i);
                            CommentObj cmtObj = new CommentObj();
                            cmtObj.setId(jsonComment.getInt("id"));
                            cmtObj.setDetails(jsonComment.getString("details"));
                            cmtObj.setUserId(jsonComment.getInt("userId"));
                            cmtObj.setGrade(jsonComment.getInt("grade"));
                            cmtObj.setIsDelete(jsonComment.getInt("isDelete"));
                            cmtObj.setCreateDate(jsonComment.getLong("createDate"));
                            cmtObj.setUpdateDate(jsonComment.getLong("updateDate"));
                            cmtObj.setStore(jsonComment.getString("store"));
                            cmtObj.setName(jsonComment.getString("userUsername"));
                            commentAdapter.add(cmtObj);

                        }

//                        commentAdapter = new CommentAdapter(getActivity(), commentObjs);
                        commentAdapter.notifyDataSetChanged();
                        loadingMore = false;
                        lsvComment.setSelection(10 * (page - 1) - 1);

                        if (cmtJsonArray == null || cmtJsonArray.length() == 0 || cmtJsonArray.length() < 10) {
                            isStopLoadingMore = false;
                            return;
                        }

//                        lsvComment.setAdapter(commentAdapter);


                    } else {
                        String msg = jsonResponse.getString("message");
                        DialogUtils.showConfirmAlertDialog(getActivity(), msg, new DialogUtils.ConfirmDialogListener() {
                            @Override
                            public void onConfirmClick() {

                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ProgressDialogUtils.dismissProgressDialog();
                    DialogUtils.showConfirmAlertDialog(getActivity(), getActivity().getString(R.string.request_error_msg), new DialogUtils.ConfirmDialogListener() {
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
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.area_store_detail3_create_button:
                if (PreferUtils.isLogin(getActivity())) {
                    startActivity(CommentActivity.class);
                } else {
                    DialogUtils.showConfirmAlertDialog(getContext(), getString(R.string.create_comment_error), new DialogUtils.ConfirmDialogListener() {
                        @Override
                        public void onConfirmClick() {

                        }
                    });
                }
                break;
        }
    }
}
