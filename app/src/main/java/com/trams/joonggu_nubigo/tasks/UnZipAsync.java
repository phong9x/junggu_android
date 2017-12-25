package com.trams.joonggu_nubigo.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.trams.joonggu_nubigo.common.Constant;
import com.trams.joonggu_nubigo.utils.Decompress;

/**
 * Created by HuyDV on 14/11/2015.
 */
public class UnZipAsync extends AsyncTask<String, String, String> {
    private Context context;

    private OnUnZipTrackingListener listener;

    public interface OnUnZipTrackingListener {
        void onCompleted();
    }

    public void setOnUnZipTrackingListener(OnUnZipTrackingListener listener) {
        this.listener = listener;
    }

    public UnZipAsync(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        String unzipLocation = context.getDatabasePath(Constant.DB_NAME).getParent() + "/"; // Application database real path
        Decompress d = new Decompress(Constant.DOWNLOADED_ZIP_DB_PATH, unzipLocation);
        d.unzip();
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (listener != null) {
            listener.onCompleted();
        }
    }
}
