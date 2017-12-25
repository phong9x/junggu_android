package com.trams.joonggu_nubigo.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.common.Constant;
import com.trams.joonggu_nubigo.utils.LogUtils;
import com.trams.joonggu_nubigo.utils.ProgressDialogUtils;
import com.trams.joonggu_nubigo.view.dialog.DialogUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ADMIN on 11/6/2015.
 */
public class NetworkUtils {

    private static final String TAG = "NetworkUtils";
    private static Handler handler;
    public static String session = "";

    public interface RequestListener {
        public void onCompleted(String response);
    }

    public static AsyncTask postRequest(final Context context, final String url, final JSONObject jsonRequest,
                                        final RequestListener listener) {
        return new AsyncTask<String, Void, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                handler = new Handler();
                ProgressDialogUtils.showProgressDialog(context, 0, 0);
            }

            @Override
            protected String doInBackground(String... params) {

                String response = null;

                HttpClient client = new DefaultHttpClient();
                HttpConnectionParams.setConnectionTimeout(client.getParams(), WebServiceConfig.NETWORK_TIME_OUT); //Timeout Limit
                try {
                    HttpPost post = new HttpPost(url);
                    StringEntity se = new StringEntity(jsonRequest.toString());
                    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    post.setEntity(se);

                    HttpResponse httpResponse = client.execute(post);

                    response = readInputStream(httpResponse
                            .getEntity().getContent());
                    LogUtils.d(TAG, "response : " + response);
                    return response;
                } catch (Exception e) {
                    e.printStackTrace();

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ProgressDialogUtils.dismissProgressDialog();
                            DialogUtils.showConfirmAlertDialog(context, context.getString(R.string.network_error_msg), new DialogUtils.ConfirmDialogListener() {
                                @Override
                                public void onConfirmClick() {

                                }
                            });
                        }
                    });


                }
                return response;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null) {
                    listener.onCompleted(s);
                }
                ProgressDialogUtils.dismissProgressDialog();
            }
        }.execute(url);
    }

    public interface RequestResponse {
        public void onResponse(JSONObject jsonResponse);
    }

    public interface RequestError {
        public void onError();
    }

    public static void postRequestVolley(final Context context, final String url, final JSONObject jsonRequest, final RequestResponse requestResponse, final RequestError requestError, final boolean isDismissProgessDialog) {
        LogUtils.d(TAG, "postRequestVolley jsonRequest : " + jsonRequest.toString());
        LogUtils.d(TAG, "postRequestVolley url : " + url);

        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Constant.PREF_NAME, Context.MODE_MULTI_PROCESS);
        session = sharedPreferences.getString(Constant.SESSION, "");

        if (context != null)
            ProgressDialogUtils.showProgressDialog(context, 0, 0);

//        LogUtils.d(TAG, "postRequestVolley , jsonRequest : " + jsonRequest.toString());
        LogUtils.d(TAG, "Session =" + session);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonRequest, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtils.d(TAG, "postRequestVolley onResponse : " + jsonObject.toString());
                requestResponse.onResponse(jsonObject);

                if (isDismissProgessDialog && context != null)
                    ProgressDialogUtils.dismissProgressDialog();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "postRequestVolley volleyError : " + volleyError.toString());
                DialogUtils.showConfirmAlertDialog(context, context.getString(R.string.network_error_msg), new DialogUtils.ConfirmDialogListener() {
                    @Override
                    public void onConfirmClick() {

                    }
                });

                if (isDismissProgessDialog && context != null)
                    ProgressDialogUtils.dismissProgressDialog();
            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Cookie", session);
                return headers;
            }
        };

        Volley.newRequestQueue(context).add(jsonObjectRequest);

    }

    public static void getRequestVolley(final Context context, final String url, final JSONObject jsonRequest, final RequestResponse requestResponse, final RequestError requestError) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Constant.PREF_NAME, Context.MODE_MULTI_PROCESS);
        session = sharedPreferences.getString(Constant.SESSION, "");

        if (context != null)
            ProgressDialogUtils.showProgressDialog(context, 0, 0);

//        LogUtils.d(TAG, "postRequestVolley , jsonRequest : " + jsonRequest.toString());
        LogUtils.d(TAG, "Session = " + session);
        LogUtils.d(TAG, "postRequestVolley url : " + url);
        LogUtils.d(TAG, "postRequestVolley jsonRequest : " + jsonRequest.toString());


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, jsonRequest, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtils.d(TAG, "postRequestVolley onResponse : " + jsonObject.toString());
                requestResponse.onResponse(jsonObject);

                if (context != null)
                    ProgressDialogUtils.dismissProgressDialog();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "postRequestVolley volleyError : " + volleyError.toString());
                DialogUtils.showConfirmAlertDialog(context, context.getString(R.string.network_error_msg), new DialogUtils.ConfirmDialogListener() {
                    @Override
                    public void onConfirmClick() {

                    }
                });

                if (context != null)
                    ProgressDialogUtils.dismissProgressDialog();
            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
//                headers.put("Cookie", session);
                return headers;
            }
        };

        Volley.newRequestQueue(context).add(jsonObjectRequest);

    }

    public static void postUpdateDataVolley(final Context context, final String url, final JSONObject jsonRequest, final RequestResponse requestResponse, final RequestError requestError, final boolean isDismissProgressDialog) {

        if (context != null)
            ProgressDialogUtils.showProgressDialog(context, 0, 0);

        LogUtils.d(TAG, "postRequestVolley , jsonRequest : " + jsonRequest.toString());
        LogUtils.d(TAG, "postRequestVolley , url : " + url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonRequest, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtils.d(TAG, "postRequestVolley onResponse : " + jsonObject.toString());
                requestResponse.onResponse(jsonObject);

                if (isDismissProgressDialog && context != null)
                    ProgressDialogUtils.dismissProgressDialog();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "postRequestVolley volleyError : " + volleyError.toString() + " , url : " + url);
                requestError.onError();
//                DialogUtils.showConfirmAlertDialog(context, context.getString(R.string.network_error_msg), new DialogUtils.ConfirmDialogListener() {
//                    @Override
//                    public void onConfirmClick() {
//
//                    }
//                });

                if (isDismissProgressDialog && context != null)
                    ProgressDialogUtils.dismissProgressDialog();
            }
        });

        Volley.newRequestQueue(context).add(jsonObjectRequest);

    }

    private static String readInputStream(InputStream inputStream)
            throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        return builder.toString();
    }


}
