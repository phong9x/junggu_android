package com.trams.joonggu_nubigo.view.dialog;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by HuyDV on 14/11/2015.
 */
public class DialogController {

    public enum Type {
        DOWNLOAD,
        DELAY
    }

    private MaterialDialog dialog;

    private static DialogController instance = new DialogController();

    public static DialogController getInstance() {
        return instance;
    }

    private DialogController() {
    }

    public MaterialDialog getDialog() {
        return dialog;
    }

    public synchronized void init(Context context, Type type) {
        switch (type) {
            case DOWNLOAD:
                dialog = new MaterialDialog.Builder(context)
                        .title("Download Data")
                        .content("Downloading ...")
                        .progress(true, 0)
                        .progressIndeterminateStyle(false)
                        .cancelable(false)
                        .build();
                break;
            case DELAY:
                dialog = new MaterialDialog.Builder(context)
                        .title("Loading")
                        .content("Loading ...")
                        .progress(true, 0)
                        .progressIndeterminateStyle(false)
                        .cancelable(false)
                        .build();
                break;
        }
    }

}
