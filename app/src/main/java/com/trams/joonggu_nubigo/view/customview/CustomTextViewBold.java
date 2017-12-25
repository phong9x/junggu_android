package com.trams.joonggu_nubigo.view.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.trams.joonggu_nubigo.utils.FontsUtils;

/**
 * Created by Administrator on 03/12/2015.
 */
public class CustomTextViewBold extends TextView {

    public CustomTextViewBold(Context context) {
        super(context);
        init();
    }

    public CustomTextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTextViewBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setTypeface(FontsUtils.getFontBold(getContext()));
    }

}
