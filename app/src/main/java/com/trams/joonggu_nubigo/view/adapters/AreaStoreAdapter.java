package com.trams.joonggu_nubigo.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.common.AppGlobal;
import com.trams.joonggu_nubigo.dao.Store;
import com.trams.joonggu_nubigo.parsers.StoreParser;
import com.trams.joonggu_nubigo.utils.NaverMapUtils;
import com.trams.joonggu_nubigo.view.activities.AreaStoreDetailActivity;
import com.trams.joonggu_nubigo.view.customview.CustomTextViewNomal;
import com.trams.joonggu_nubigo.view.customview.FieldView;

import java.util.List;

/**
 * Created by Administrator on 30/10/2015.
 */
public class AreaStoreAdapter extends ArrayAdapter<Store> {

    private DisplayImageOptions mDisplayImageOptions;
    Context context;
    int filterPos = 0;

    public AreaStoreAdapter(Context context, List<Store> stores, int filterPos) {
        super(context, R.layout.item_area_store, stores);
        this.context = context;
        this.filterPos = filterPos;
        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
    }

    public static class ViewHolder {
        ImageView imgCompany;
        //ImageView imgCompanyTypeIcon;
        CustomTextViewNomal companyName;
        RatingBar star;
        //CustomTextViewNomal companyType;
        View viewRoadBtn;

        CheckBox access0;
        CheckBox access1;
        CheckBox access2;
        CheckBox access3;
        CheckBox access4;
        CheckBox access5;

        public FieldView fieldView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Store item = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.item_area_store, parent, false);
            holder = new ViewHolder();
            holder.imgCompany = (ImageView) convertView.findViewById(R.id.img_company);
            //holder.imgCompanyTypeIcon = (ImageView) convertView.findViewById(R.id.img_company_type_icon);
            holder.companyName = (CustomTextViewNomal) convertView.findViewById(R.id.tv_company_name);
            holder.star = (RatingBar) convertView.findViewById(R.id.star);
            //holder.companyType = (CustomTextViewNomal) convertView.findViewById(R.id.tv_company_type);
            holder.viewRoadBtn = convertView.findViewById(R.id.area_store_view_road_btn);

            holder.access0 = (CheckBox) convertView.findViewById(R.id.access0);
            holder.access1 = (CheckBox) convertView.findViewById(R.id.access1);
            holder.access2 = (CheckBox) convertView.findViewById(R.id.access2);
            holder.access3 = (CheckBox) convertView.findViewById(R.id.access3);
            holder.access4 = (CheckBox) convertView.findViewById(R.id.access4);
            holder.access5 = (CheckBox) convertView.findViewById(R.id.access5);

            holder.fieldView = (FieldView) convertView.findViewById(R.id.field_view_item_scrap);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        ImageLoader.getInstance().displayImage(StoreParser.getPresentImageUrl(item.getImageBaseAttach()), holder.imgCompany, mDisplayImageOptions);
        holder.companyName.setText(item.getName());
        holder.star.setRating(StoreParser.getGra(item.getGrade()));
        //holder.companyType.setText(StoreParser.getFieldList(item.getFieldList()));

        //StoreParser.setFieldIcon(item, holder.imgCompanyTypeIcon);
        if (filterPos == 0) {
            holder.fieldView.setupView(item, true, 0);
        } else {
            holder.fieldView.setupView(item, false, filterPos);
        }

        CheckBox[] checkBoxes = new CheckBox[]{holder.access0,
                holder.access1,
                holder.access2,
                holder.access3,
                holder.access4,
                holder.access5};
        StoreParser.setAccessibilityList(item.getAccessibilityList(), checkBoxes);

        holder.viewRoadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double lat = Double.parseDouble(item.getLatitude());
                double lon = Double.parseDouble(item.getLongitude());
                NaverMapUtils.openNaverMap(getContext(), lat, lon);
            }
        });
        holder.imgCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppGlobal.sStore = item;
                Intent intent = new Intent(context, AreaStoreDetailActivity.class);
                context.startActivity(intent);

            }
        });

        return convertView;
    }
}
