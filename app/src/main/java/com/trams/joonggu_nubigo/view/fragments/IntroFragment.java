package com.trams.joonggu_nubigo.view.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.view.customview.CustomTextViewNomal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 12/11/2015.
 */
public class IntroFragment extends BaseFragment implements View.OnClickListener {

    private CustomTextViewNomal tvHeader;
    private TextView tvContent;
    private ImageView imgSearch;

    @Override
    protected int getLayout() {
        return R.layout.fragment_intro;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        tvHeader = (CustomTextViewNomal) view.findViewById(R.id.tvIntroHeader);
        tvContent = (TextView) view.findViewById(R.id.tvIntroContent);
        imgSearch = (ImageView) view.findViewById(R.id.header_search_btn);

    }

    @Override
    protected void registerListener() {
        imgSearch.setOnClickListener(this);
    }

    @Override
    protected void initVaribles() {
        //Matcher all header string in text view
        String matcher = "중구길벗센터";
        SpannableString spannable = new SpannableString(tvHeader.getText().toString());
        Pattern patternId = Pattern.compile(matcher);
        Matcher matcherId = patternId.matcher(tvHeader.getText().toString());

        while (matcherId.find()) {
//            // Set click event
//            spannable.setSpan(new ClickableSpan() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            }, matcherId.start(), matcherId.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            // Set title string to bold
            spannable.setSpan(Typeface.BOLD, matcherId.start(), matcherId.end(), 0);
            // Set title string color
            spannable.setSpan(new ForegroundColorSpan(Color.argb(255, 0, 0, 0)), matcherId.start(), matcherId.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        tvHeader.setText(spannable);

        //content
        //Matcher all header string in text view
        String matcherContent = "안";
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

            // Set title string to bold
            spannableContent.setSpan(Typeface.BOLD, matcherIdContent.start(), matcherIdContent.end(), 0);
            // Set title string color
            spannableContent.setSpan(new ForegroundColorSpan(Color.argb(255, 17, 134, 117)), matcherIdContent.start(), matcherIdContent.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        tvContent.setText(spannableContent);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_search_btn:
                doSearch();
                break;
        }
    }

    private void doSearch() {
        showPopup();
    }
}
