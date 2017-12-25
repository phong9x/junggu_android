package com.trams.joonggu_nubigo.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.view.customview.CustomTextViewNomal;

/**
 * Created by ADMIN on 11/5/2015.
 */
public class TermDialog extends Dialog implements View.OnClickListener {

    private ImageView imgBack;
    private CustomTextViewNomal tvContent;

    private static TermDialog instance;

    public static TermDialog getInstance(Context context) {
        if (instance == null) {
            instance = new TermDialog(context);
        }
        return instance;
    }

    public TermDialog(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(false);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.term_dialog);

        imgBack = (ImageView) findViewById(R.id.img_term_back);
        imgBack.setOnClickListener(this);

        tvContent = (CustomTextViewNomal) findViewById(R.id.tv_term_content);
    }

    /**
     * Show view
     */
    public void showView() {
        if (!this.isShowing()) {
            this.show();
        }
    }

    /**
     * hide view
     */
    public void hideView() {
        if (this.isShowing()) {
            this.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_term_back:
                hideView();
                break;
        }
    }
}
