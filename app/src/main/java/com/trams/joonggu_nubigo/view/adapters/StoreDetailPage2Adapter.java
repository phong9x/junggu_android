package com.trams.joonggu_nubigo.view.adapters;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.objects.AreaStoreDetailPage2Obj;
import com.trams.joonggu_nubigo.objects.ImageObj;
import com.trams.joonggu_nubigo.view.custome.SimpleViewPagerIndicator;
import com.trams.joonggu_nubigo.view.customview.CustomTextViewNomal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dang on 11/11/2015.
 */
public class StoreDetailPage2Adapter extends ArrayAdapter<AreaStoreDetailPage2Obj> {

    public StoreDetailPage2Adapter(Context context, List<AreaStoreDetailPage2Obj> objs) {
        super(context, R.layout.item_store_detail_page2, objs);
    }

    public static class ViewHolder {
        CustomTextViewNomal name;
        SimpleViewPagerIndicator mIndicator;
        ViewPager pager;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AreaStoreDetailPage2Obj item = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.item_store_detail_page2, parent, false);
            holder = new ViewHolder();

            holder.name = (CustomTextViewNomal) convertView.findViewById(R.id.area_store_detail2_title);
            holder.mIndicator = (SimpleViewPagerIndicator) convertView.findViewById(R.id.indicator);
            holder.pager = (ViewPager) convertView.findViewById(R.id.pager);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(item.getName());
        ArrayList<ImageObj> imageObjs = new ArrayList<>();
        imageObjs.addAll(item.getImageObjs());

        ViewPagerAdapter adapter = new ViewPagerAdapter(getContext(), imageObjs);
        holder.pager.setAdapter(adapter);
        holder.mIndicator.setViewPager(holder.pager);

        return convertView;
    }
}
