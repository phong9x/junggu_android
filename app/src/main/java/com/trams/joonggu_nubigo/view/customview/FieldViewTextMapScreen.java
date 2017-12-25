package com.trams.joonggu_nubigo.view.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.dao.Store;
import com.trams.joonggu_nubigo.parsers.StoreParser;

import java.util.ArrayList;

/**
 * Created by Administrator on 27/11/2015.
 */
public class FieldViewTextMapScreen extends LinearLayout {

    private LinearLayout layoutName1, layoutName2, layoutName3, layoutName4, layoutName5;

    public FieldViewTextMapScreen(Context context) {
        super(context);
        init();
    }

    public FieldViewTextMapScreen(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FieldViewTextMapScreen(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public FieldViewTextMapScreen(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init();
//    }

    private void init() {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.field_view_text_map_screen_layout, this, true);
        layoutName1 = (LinearLayout) findViewById(R.id.layout_field_name_1);
        layoutName2 = (LinearLayout) findViewById(R.id.layout_field_name_2);
        layoutName3 = (LinearLayout) findViewById(R.id.layout_field_name_3);
        layoutName4 = (LinearLayout) findViewById(R.id.layout_field_name_4);
        layoutName5 = (LinearLayout) findViewById(R.id.layout_field_name_5);
    }

    public void setupView(Store store) {

        ArrayList<Integer> listId = StoreParser.getListFieldId(store);

        layoutName1.setVisibility(View.GONE);
        layoutName2.setVisibility(View.GONE);
        layoutName3.setVisibility(View.GONE);
        layoutName4.setVisibility(View.GONE);
        layoutName5.setVisibility(View.GONE);

        for (int i = 0; i < listId.size(); i++) {
            int id = listId.get(i);
            switch (id) {
                case 1:
                    layoutName1.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    layoutName2.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    layoutName3.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    layoutName4.setVisibility(View.VISIBLE);
                    break;
                case 5:
                    layoutName5.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }


}
