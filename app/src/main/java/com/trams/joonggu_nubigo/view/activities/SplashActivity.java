package com.trams.joonggu_nubigo.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.common.Constant;
import com.trams.joonggu_nubigo.common.Prefs;
import com.trams.joonggu_nubigo.network.WebServiceConfig;
import com.trams.joonggu_nubigo.tasks.DownloadAsync;
import com.trams.joonggu_nubigo.tasks.UnZipAsync;
import com.trams.joonggu_nubigo.view.dialog.DialogController;

/**
 * Created by Dang on 20/11/2015.
 */
public class SplashActivity extends BaseActivity {
    private Prefs prefs;

    private View splashView;

    @Override
    protected int getLayout() {
        return R.layout.activity_splash_layout;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        splashView = findViewById(R.id.splash_image_view);
    }

    @Override
    protected void registerListener() {

    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        prefs = new Prefs(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (prefs.isFirstRun() == true) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            downloadDb();
                        }
                    }, 0);
                } else {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, Constant.SPLASH_TIME);

//        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//        startActivity(intent);
//        finish();

    }

    void downloadDb() {
        DownloadAsync downloadAsync = new DownloadAsync(this);
        downloadAsync.setOnProgressTracking(new DownloadAsync.OnProgressTracking() {
            @Override
            public void onPreStart() {
                DialogController.getInstance().init(SplashActivity.this, DialogController.Type.DOWNLOAD);
                DialogController.getInstance()
                        .getDialog()
                        .show();
            }

            @Override
            public void onCompleted() {
                UnZipAsync unZipAsync = new UnZipAsync(SplashActivity.this);
                unZipAsync.setOnUnZipTrackingListener(new UnZipAsync.OnUnZipTrackingListener() {
                    @Override
                    public void onCompleted() {
                        DialogController.getInstance()
                                .getDialog()
                                .dismiss();
                        prefs.setFirstRun(false);
                        finish();
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                unZipAsync.execute("");
            }
        });
        downloadAsync.execute(WebServiceConfig.SERVER_DB_URL);
    }
}
