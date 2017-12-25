package com.trams.joonggu_nubigo.view.customview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;
import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.common.AppGlobal;
import com.trams.joonggu_nubigo.common.Constant;
import com.trams.joonggu_nubigo.dao.Store;
import com.trams.joonggu_nubigo.parsers.StoreParser;
import com.trams.joonggu_nubigo.utils.LogUtils;
import com.trams.joonggu_nubigo.utils.NaverMapUtils;
import com.trams.joonggu_nubigo.view.activities.AreaStoreDetailActivity;
import com.trams.joonggu_nubigo.view.adapters.FieldStoreAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 16/11/2015.
 */
public class StoreView extends RelativeLayout implements View.OnClickListener {
    private static final String TAG = StoreViewMap.class.getName();
    private ImageView imgThubnail;
    private CustomTextViewBold tvName;
    private RatingBar ratingBar;
    private Button btnViewRoad;
    private AccessbilityView accessbilityView;
    private DisplayImageOptions mDisplayImageOptions;
    //    private FieldViewText fieldView;
    private GridView grField;

    private Store store;

    public StoreView(Context context) {
        super(context);
        init();
    }

    public StoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public StoreView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init();
//    }

    private void init() {

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.item_scrap, this, true);
        imgThubnail = (ImageView) findViewById(R.id.img_thubnail_item_scrap);
        tvName = (CustomTextViewBold) findViewById(R.id.tv_name_item_scrap);
        ratingBar = (RatingBar) findViewById(R.id.rb_item_scrap);
        btnViewRoad = (Button) findViewById(R.id.btn_view_road_item_scrap);
        accessbilityView = (AccessbilityView) findViewById(R.id.acv_item_scrap);
//        fieldView = (FieldViewText) findViewById(R.id.field_view_item_scrap);

        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(Constant.CACHE_ON_MEMORY)
                .cacheOnDisk(Constant.CACHE_ON_DISK)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .postProcessor(new BitmapProcessor() {
                    @Override
                    public Bitmap process(Bitmap bitmap) {
                        return scareBitmapThubnail(bitmap);
                    }
                })
                .build();

        btnViewRoad.setOnClickListener(this);
        imgThubnail.setOnClickListener(this);

        grField = (GridView) findViewById(R.id.gr_field);

    }

    private Bitmap scareBitmapThubnail(Bitmap bitmap) {
        if (bitmap.getWidth() < Constant.WIDTH_HEIGHT_IMG_PRESENT && bitmap.getHeight() < Constant.WIDTH_HEIGHT_IMG_PRESENT) {
            return bitmap;
        } else if (bitmap.getWidth() > bitmap.getHeight()) {
            float scale = bitmap.getWidth() / Constant.WIDTH_HEIGHT_IMG_PRESENT;
            return Bitmap.createScaledBitmap(bitmap, (int) Constant.WIDTH_HEIGHT_IMG_PRESENT, (int) (bitmap.getHeight() / scale), false);
        } else {
            float scale = bitmap.getHeight() / Constant.WIDTH_HEIGHT_IMG_PRESENT;
            return Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() / scale), (int) Constant.WIDTH_HEIGHT_IMG_PRESENT, false);
        }
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

        ArrayList<String> listFieldName = StoreParser.getListFieldName(store);
        FieldStoreAdapter fieldNameAdapter = new FieldStoreAdapter(getContext(), listFieldName);
        grField.setAdapter(fieldNameAdapter);

        ratingBar.setRating(StoreParser.getGra(store.getGrade()));

        accessbilityView.setView(StoreParser.getAccessibilityList(store.getAccessibilityList()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_thubnail_item_scrap:
                goToDetailStore();
                break;
            case R.id.btn_view_road_item_scrap:
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
