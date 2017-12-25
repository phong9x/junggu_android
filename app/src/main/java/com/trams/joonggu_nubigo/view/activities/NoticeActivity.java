package com.trams.joonggu_nubigo.view.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.dbmanager.DbNoticeUpdate;
import com.trams.joonggu_nubigo.dbmanager.UpdateDbItf;
import com.trams.joonggu_nubigo.network.WebServiceConfig;
import com.trams.joonggu_nubigo.view.adapters.NoticeAdapter;

/**
 * Created by Administrator on 18/11/2015.
 */
public class NoticeActivity extends BaseActivity implements View.OnClickListener {

    private ListView lvNotice;
    private NoticeAdapter noticeAdapter;
    private static final String TAG = NoticeActivity.class.getName();
    private ImageView imgBack;


    @Override
    protected int getLayout() {
        return R.layout.layout_notice;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        lvNotice = (ListView) findViewById(R.id.lvNotice);
        imgBack = (ImageView) findViewById(R.id.img_notice_back);
    }

    @Override
    protected void registerListener() {
        imgBack.setOnClickListener(this);
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        final DbNoticeUpdate dbNoticeUpdate = new DbNoticeUpdate(NoticeActivity.this, WebServiceConfig.URL_UPDATE_NOTICE);

        dbNoticeUpdate.setOnResponseFail(new UpdateDbItf.OnResponseFail() {
            @Override
            public void onResponseFail() {
                noticeAdapter = new NoticeAdapter(NoticeActivity.this, dbNoticeUpdate.getAllNotice(NoticeActivity.this));
                lvNotice.setAdapter(noticeAdapter);
            }
        });

        dbNoticeUpdate.setOnResponseSuccess(new UpdateDbItf.OnResponseSuccess() {
            @Override
            public void onResponseSuccess() {
                noticeAdapter = new NoticeAdapter(NoticeActivity.this, dbNoticeUpdate.getAllNotice(NoticeActivity.this));
                lvNotice.setAdapter(noticeAdapter);
            }
        });

        dbNoticeUpdate.execute(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_notice_back:
                finish();
                break;
        }
    }
}
