package com.trams.joonggu_nubigo.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.trams.joonggu_nubigo.common.Constant;
import com.trams.joonggu_nubigo.utils.LogUtils;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by HuyDV on 14/11/2015.
 */
public class DownloadAsync extends AsyncTask<String, String, String> {

    private static final String TAG = DownloadAsync.class.getSimpleName();

    private ProgressDialog mProgressDialog;
    private Context mContext;

    OnProgressTracking onProgressTracking;

    public interface OnProgressTracking {

        void onPreStart();

        void onCompleted();
    }

    public void setOnProgressTracking(OnProgressTracking listener) {
        this.onProgressTracking = listener;
    }

    public DownloadAsync(Context context) {
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        onProgressTracking.onPreStart();
    }

    @Override
    protected String doInBackground(String... aurl) {
        int count;
        try {
            URL url = new URL(aurl[0]);
            URLConnection connection = url.openConnection();
            connection.connect();

            int lengthOfFile = connection.getContentLength();
            LogUtils.d(TAG, "Length of file: " + lengthOfFile);

            InputStream input = new BufferedInputStream(url.openStream());
            OutputStream output = new FileOutputStream(Constant.DOWNLOADED_ZIP_DB_PATH);

            byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                publishProgress("" + (int) ((total * 100) / lengthOfFile));
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
            LogUtils.d(TAG, "Download error !!! " + e.getMessage());
        }
        return null;

    }

    @Override
    protected void onPostExecute(String unused) {
        if (onProgressTracking != null) {
            onProgressTracking.onCompleted();
        }
    }
}



