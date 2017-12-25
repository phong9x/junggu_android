package com.trams.joonggu_nubigo.view.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.dao.Store;
import com.trams.joonggu_nubigo.utils.LogUtils;
import com.trams.joonggu_nubigo.utils.PxUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 26/11/2015.
 */
public class FieldView extends LinearLayout {

    private Context context;
    private static final String TAG = FieldView.class.getName();

    private static final String FIELD_1 = "먹거리";
    private static final String FIELD_2 = "관광지";
    private static final String FIELD_3 = "숙박";
    private static final String FIELD_4 = "쇼핑";
    private static final String FIELD_5 = "생활";
    HashMap<Integer, String> hmField = new HashMap<>();


    public FieldView(Context context) {
        super(context);
        init(context);
    }

    public FieldView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FieldView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public FieldView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init(context);
//    }

    private void init(Context context) {
        this.context = context;
        setUpHashMap();
    }

    public void setupView(Store store, boolean isFilterAll, int idFilter) {

        LogUtils.d(TAG, "FieldView , store : " + store.toString());
        LogUtils.d(TAG, "FieldView , isFilterAll : " + isFilterAll);
        LogUtils.d(TAG, "FieldView , idFilter : " + idFilter);

        removeAllViews();
        removeAllViewsInLayout();

        LogUtils.d(TAG, "FieldView addView start,field setup : " + store.getFieldList());
        ArrayList<Integer> fieldListId = getListFieldId(store);
        ArrayList<String> fieldListName = getListFieldName(store);

//        String[] fieldList ={FIELD_1};

        for (int i = 0; i < fieldListId.size(); i++) {
            LogUtils.d(TAG, "FieldView fieldListId : " + i + " : " + fieldListId.get(i));
        }

        for (int i = 0; i < fieldListId.size(); i++) {
            LogUtils.d(TAG, "FieldView fieldListName : " + i + " : " + fieldListName.get(i));
        }

        LinearLayout.LayoutParams imgLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        imgLayoutParams.setMargins(10, 0, 10, 0);
        imgLayoutParams.gravity = Gravity.CENTER;

        //set icon
        if (isFilterAll) {
            LogUtils.d(TAG, "isFilterAll set img");
            for (int i = 0; i < fieldListId.size(); i++) {
                ImageView img = new ImageView(context);
                if (fieldListId.get(i) == 1) {
                    img.setImageResource(R.drawable.field_food);
                    LogUtils.d(TAG, "FieldView set field field 1");
                } else if (fieldListId.get(i) == 2) {
                    img.setImageResource(R.drawable.field_sight);
                    LogUtils.d(TAG, "FieldView set field field 2");
                } else if (fieldListId.get(i) == 3) {
                    img.setImageResource(R.drawable.field_accommodation);
                    LogUtils.d(TAG, "FieldView set field field 3");
                } else if (fieldListId.get(i) == 4) {
                    img.setImageResource(R.drawable.field_shopping);
                    LogUtils.d(TAG, "FieldView set field field 4");
                } else if (fieldListId.get(i) == 5) {
                    img.setImageResource(R.drawable.field_living);
                    LogUtils.d(TAG, "FieldView set field field 5");
                }
                addView(img, imgLayoutParams);
            }
        } else {
            LogUtils.d(TAG, "!isFilterAll set img");
            ImageView img = new ImageView(context);

//            int filedIdSelected = fieldListId.get(idFilter);

            if (idFilter == 1) {
                img.setImageResource(R.drawable.field_food);
            } else if (idFilter == 2) {
                img.setImageResource(R.drawable.field_sight);
            } else if (idFilter == 3) {
                img.setImageResource(R.drawable.field_accommodation);
            } else if (idFilter == 4) {
                img.setImageResource(R.drawable.field_shopping);
            } else if (idFilter == 5) {
                img.setImageResource(R.drawable.field_living);
            }
            addView(img, imgLayoutParams);
        }

        //set name
        if (isFilterAll) {
            if (fieldListId.size() == 1) {
                LogUtils.d(TAG, "isFilterAll set tv name fieldList.length == 1");
                CustomTextViewNomal tvName = new CustomTextViewNomal(context);

                LinearLayout.LayoutParams tvLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                tvLayoutParams.setMargins((int) PxUtils.pxFromDp(context, 5), 0, (int) PxUtils.pxFromDp(context, 5), 0);
                tvLayoutParams.gravity = Gravity.CENTER;
                addView(tvName, tvLayoutParams);

                tvName.setText(fieldListName.get(0));

                tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        getResources().getDimension(R.dimen.textsize_field_view_name));

//                tvName.setTextSize(PxUtils.pxFromDp(context, 8));
            }
        } else {
            LogUtils.d(TAG, "!isFilterAll set tv name");

            CustomTextViewNomal tvName = new CustomTextViewNomal(context);
            tvName.setText(hmField.get(idFilter));
            LinearLayout.LayoutParams tvLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            tvLayoutParams.setMargins((int) PxUtils.pxFromDp(context, 5), 0, (int) PxUtils.pxFromDp(context, 5), 0);
            tvLayoutParams.gravity = Gravity.CENTER;

//            tvName.setTextSize(PxUtils.pxFromDp(context, 8));
            tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    getResources().getDimension(R.dimen.textsize_field_view_name));

            addView(tvName, imgLayoutParams);
        }
    }

    private ArrayList<Integer> getListFieldId(Store store) {
        ArrayList<Integer> result = new ArrayList<>();

        String fieldName = store.getFieldList();

        try {
            JSONArray jsonArray = new JSONArray(fieldName);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int fieldId = jsonObject.getInt("field_list_id");
                if (!result.contains(fieldId))
                    result.add(fieldId);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    private ArrayList<String> getListFieldName(Store store) {
        ArrayList<String> result = new ArrayList<>();

        String fieldList = store.getFieldList();

        try {
            JSONArray jsonArray = new JSONArray(fieldList);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String fieldName = jsonObject.getString("field_list_name");
                if (!result.contains(fieldName))
                    result.add(fieldName);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void setUpHashMap() {
        hmField.put(1, FIELD_1);
        hmField.put(2, FIELD_2);
        hmField.put(3, FIELD_3);
        hmField.put(4, FIELD_4);
        hmField.put(5, FIELD_5);
    }
}
