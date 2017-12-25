package com.trams.joonggu_nubigo.view.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.trams.joonggu_nubigo.utils.FontsUtils;

/**
 * Created by Administrator on 03/12/2015.
 */
public class CustomButtonBold extends Button {
    public CustomButtonBold(Context context) {
        super(context);
        init();
    }

    public CustomButtonBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomButtonBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setTypeface(FontsUtils.getFontBold(getContext()));
    }

}
