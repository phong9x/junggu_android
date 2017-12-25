package com.trams.joonggu_nubigo.view.customview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.common.AppGlobal;
import com.trams.joonggu_nubigo.dao.Store;
import com.trams.joonggu_nubigo.parsers.StoreParser;
import com.trams.joonggu_nubigo.utils.LogUtils;
import com.trams.joonggu_nubigo.utils.NaverMapUtils;
import com.trams.joonggu_nubigo.view.activities.AreaStoreDetailActivity;

/**
 * Created by Administrator on 16/11/2015.
 */
public class StoreViewMap extends RelativeLayout implements View.OnClickListener {
    private static final String TAG = StoreViewMap.class.getName();
    private ImageView imgThubnail;
    private CustomTextViewNomal tvName;
    private RatingBar ratingBar;
    private Button btnViewRoad;
    private AccessbilityView accessbilityView;
    private DisplayImageOptions mDisplayImageOptions;
    private FieldViewTextMapScreen fieldView;

    private Store store;

    public StoreViewMap(Context context) {
        super(context);
        init();
    }

    public StoreViewMap(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StoreViewMap(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public StoreViewMap(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init();
//    }

    private void init() {

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.store_view_map, this, true);
        imgThubnail = (ImageView) findViewById(R.id.img_thubnail_store_view);
        tvName = (CustomTextViewNomal) findViewById(R.id.tv_name_store_view);
        ratingBar = (RatingBar) findViewById(R.id.rb_store_view);
        btnViewRoad = (Button) findViewById(R.id.btn_view_road_store_view);
        accessbilityView = (AccessbilityView) findViewById(R.id.acv_store_view);
        fieldView = (FieldViewTextMapScreen) findViewById(R.id.field_view_text);

        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        btnViewRoad.setOnClickListener(this);
        imgThubnail.setOnClickListener(this);

    }

    public void updateView(Store store) {

        LogUtils.d(TAG, "updateView data : " + store.toString());

        this.store = store;
        try {
            String url = StoreParser.getPresentImageUrl(store.getImageBaseAttach());
            ImageLoader.getInstance().displayImage(url, imgThubnail, mDisplayImageOptions);
            LogUtils.d(TAG, "StoreView ,url : " + url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        tvName.setText(store.getName());

        fieldView.setupView(store);

        ratingBar.setRating(StoreParser.getGra(store.getGrade()));

        accessbilityView.setView(StoreParser.getAccessibilityList(store.getAccessibilityList()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_thubnail_store_view:
                goToDetailStore();
                break;
            case R.id.btn_view_road_store_view:
                try {
                    LogUtils.d(TAG, "StoreView onClick : " + store.toString());
                    NaverMapUtils.openNaverMap(getContext(), Float.valueOf(store.getLongitude()), Float.valueOf(store.getLatitude()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void goToDetailStore() {
        AppGlobal.sStore = store;
        Intent intent = new Intent(getContext(), AreaStoreDetailActivity.class);
        getContext().startActivity(intent);
    }
}
