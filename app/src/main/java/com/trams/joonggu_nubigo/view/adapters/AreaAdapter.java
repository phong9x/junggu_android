package com.trams.joonggu_nubigo.view.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.dao.Category;
import com.trams.joonggu_nubigo.utils.FontsUtils;
import com.trams.joonggu_nubigo.view.customview.CustomTextViewNomal;

import java.util.List;

/**
 * Created by Administrator on 29/10/2015.
 */
public class AreaAdapter extends ArrayAdapter<Category> {

    private int resetIdx = 999999999;

    private int selectedIdx = resetIdx;

    public AreaAdapter(Context context, List<Category> categories) {
        super(context, R.layout.item_area, categories);
    }

    public static class ViewHolder {
        ImageView img;
        CustomTextViewNomal tv;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Category item = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.item_area, parent, false);
            holder = new ViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.img_area_item);
            holder.tv = (CustomTextViewNomal) convertView.findViewById(R.id.tv_area_item);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position == selectedIdx) {
            holder.img.setImageResource(R.drawable.item_area_selected_bg);
            FontsUtils.setFontTvBold(getContext(), holder.tv);
            holder.tv.setTextColor(Color.parseColor("#ffffff"));
        } else {
            holder.img.setImageResource(R.drawable.item_area_bg);
            FontsUtils.setFontTvNomal(getContext(), holder.tv);
            holder.tv.setTextColor(Color.parseColor("#444444"));
        }
        holder.tv.setText(item.getCatName());

        return convertView;
    }

    public void setSelectedIdx(int idx) {
        selectedIdx = idx;
    }

    public void resetSelectedIdx() {
        selectedIdx = resetIdx;
    }
}
