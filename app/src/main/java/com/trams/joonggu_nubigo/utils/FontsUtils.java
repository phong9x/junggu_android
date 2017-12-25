package com.trams.joonggu_nubigo.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Administrator on 09/11/2015.
 */
public class FontsUtils {

    private static final String FONT_NOMAL = "NanumBarunGothic.ttf";
    private static Typeface nanumBarumTf;

    private static final String FONT_BOLD = "NanumBarunGothicBold.ttf";
    private static Typeface nanumBarunBold;

    //set tv font nomal
    public static void setFontTvNomal(Context context, TextView... tvs) {
        if (nanumBarumTf == null) {
            nanumBarumTf = Typeface.createFromAsset(context.getAssets(),
                    "fonts/" + FONT_NOMAL);
        }

        for (TextView tv : tvs) {
            tv.setTypeface(nanumBarumTf);
        }

    }

    //set tv font bold
    public static void setFontTvBold(Context context, TextView... tvs) {
        if (nanumBarunBold == null) {
            nanumBarunBold = Typeface.createFromAsset(context.getAssets(),
                    "fonts/" + FONT_BOLD);
        }

        for (TextView tv : tvs) {
            tv.setTypeface(nanumBarunBold);
        }

    }

    //set edt font nomal
    public static void setFontEdtNomal(Context context, EditText... edts) {
        if (nanumBarumTf == null) {
            nanumBarumTf = Typeface.createFromAsset(context.getAssets(),
                    "fonts/" + FONT_NOMAL);
        }

        for (EditText edt : edts) {
            edt.setTypeface(nanumBarumTf);
        }

    }

    //set edt font bold
    public static void setFontEdtBold(Context context, EditText... edts) {
        if (nanumBarunBold == null) {
            nanumBarunBold = Typeface.createFromAsset(context.getAssets(),
                    "fonts/" + FONT_BOLD);
        }

        for (EditText edt : edts) {
            edt.setTypeface(nanumBarunBold);
        }

    }

    //set btn font nomal
    public static void setFontBtnNomal(Context context, Button... btns) {
        if (nanumBarumTf == null) {
            nanumBarumTf = Typeface.createFromAsset(context.getAssets(),
                    "fonts/" + FONT_NOMAL);
        }

        for (Button btn : btns) {
            btn.setTypeface(nanumBarumTf);
        }

    }

    //set btn font bold
    public static void setFontBtnBold(Context context, Button... btns) {
        if (nanumBarunBold == null) {
            nanumBarunBold = Typeface.createFromAsset(context.getAssets(),
                    "fonts/" + FONT_BOLD);
        }

        for (Button btn : btns) {
            btn.setTypeface(nanumBarunBold);
        }
    }

    public static Typeface getFontNomal(Context context) {
        if (nanumBarumTf == null) {
            nanumBarumTf = Typeface.createFromAsset(context.getAssets(),
                    "fonts/" + FONT_NOMAL);
        }

        return nanumBarumTf;
    }

    public static Typeface getFontBold(Context context) {
        if (nanumBarunBold == null) {
            nanumBarunBold = Typeface.createFromAsset(context.getAssets(),
                    "fonts/" + FONT_BOLD);
        }

        return nanumBarunBold;
    }

}
