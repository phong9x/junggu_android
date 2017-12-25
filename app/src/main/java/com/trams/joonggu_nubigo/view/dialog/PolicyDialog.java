package com.trams.joonggu_nubigo.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.view.customview.CustomTextViewNomal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ADMIN on 11/5/2015.
 */
public class PolicyDialog extends Dialog implements View.OnClickListener {

    private ImageView imgBack;

    private static PolicyDialog instance;
    private CustomTextViewNomal tvContent;

    public static PolicyDialog getInstance(Context context) {
        if (instance == null) {
            instance = new PolicyDialog(context);
        }
        return instance;
    }

    public PolicyDialog(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(false);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.policy_dialog);

        imgBack = (ImageView) findViewById(R.id.img_policy_back);
        imgBack.setOnClickListener(this);

        tvContent = (CustomTextViewNomal) findViewById(R.id.tv_policy_content);

        //content
        //Matcher all header string in text view
        String matcherContent = "‘중구 길벗’";
        SpannableString spannableContent = new SpannableString(tvContent.getText().toString());
        Pattern patternIdContent = Pattern.compile(matcherContent);
        Matcher matcherIdContent = patternIdContent.matcher(tvContent.getText().toString());

        while (matcherIdContent.find()) {
            // Set click event
//            spannable.setSpan(new ClickableSpan() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            }, matcherId.start(), matcherId.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

//            // Set title string to bold
//            spannableContent.setSpan(Typeface.BOLD, matcherIdContent.start(), matcherIdContent.end(), 0);
            // Set title string color
            spannableContent.setSpan(new ForegroundColorSpan(Color.argb(255, 17, 134, 117)), matcherIdContent.start(), matcherIdContent.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
//        tvContent.setText(spannableContent);

        String matcherContentTitle = "1. 총칙";
//        SpannableString spannableContentTitle = new SpannableString(tvContent.getText().toString());
        Pattern patternIdContentTitle = Pattern.compile(matcherContentTitle);
        Matcher matcherIdContentTitle = patternIdContentTitle.matcher(tvContent.getText().toString());
        while (matcherIdContentTitle.find()) {
            // Set title string to bold
            spannableContent.setSpan(Typeface.BOLD, matcherIdContentTitle.start(), matcherIdContentTitle.end(), 0);
            // Set title string color
            spannableContent.setSpan(new ForegroundColorSpan(Color.argb(255, 0, 0, 0)), matcherIdContentTitle.start(), matcherIdContentTitle.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        tvContent.setText(spannableContent);

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
            case R.id.img_policy_back:
                hideView();
                break;
        }
    }
}
