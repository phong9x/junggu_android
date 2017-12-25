package com.trams.joonggu_nubigo.view.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.trams.joonggu_nubigo.utils.FontsUtils;

/**
 * Created by Administrator on 03/12/2015.
 */
public class CustomEditTextNomal extends EditText {
    public CustomEditTextNomal(Context context) {
        super(context);
        init();
    }

    public CustomEditTextNomal(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomEditTextNomal(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setTypeface(FontsUtils.getFontNomal(getContext()));
    }

}
