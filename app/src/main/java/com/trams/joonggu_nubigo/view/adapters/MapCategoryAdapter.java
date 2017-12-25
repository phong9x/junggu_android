package com.trams.joonggu_nubigo.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;

import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.dao.Category;
import com.trams.joonggu_nubigo.utils.LogUtils;
import com.trams.joonggu_nubigo.view.customview.CustomTextViewNomal;

import java.util.List;

/**
 * Created by Administrator on 13/11/2015.
 */
public class MapCategoryAdapter extends ArrayAdapter<Category> {

    private static final String TAG = MapCategoryAdapter.class.getName();
    private int positionSelected = 0;

    public MapCategoryAdapter(Context context, List<Category> categories) {
        super(context, R.layout.item_map_category, categories);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Category item = getItem(position);

        LogUtils.d(TAG, "getView , item : " + item.toString());

        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.item_map_category, parent, false);
            holder = new ViewHolder();
            holder.tvName = (CustomTextViewNomal) convertView.findViewById(R.id.tv_name_item_map_category);
            holder.layoutBg = (RelativeLayout) convertView.findViewById(R.id.layout_item_map_category);
            holder.vRight = (View) convertView.findViewById(R.id.v_item_line_view_right);
            holder.vBottom = (View) convertView.findViewById(R.id.v_item_line_view_bottom);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position == positionSelected) {
            if (getContext() != null)
                holder.layoutBg.setBackgroundColor(getContext().getResources().getColor(R.color.item_accessbility_selected));
        } else {
            holder.layoutBg.setBackgroundResource(R.drawable.gridview_selector);
        }

        if (position % 3 == 2) {
            holder.vRight.setVisibility(View.GONE);
        } else {
            holder.vRight.setVisibility(View.VISIBLE);
        }

        holder.tvName.setText(item.getCatName());
        return convertView;
    }

    public static class ViewHolder {
        CustomTextViewNomal tvName;
        RelativeLayout layoutBg;
        View vRight;
        View vBottom;
    }

    public int getPositionSelected() {
        return positionSelected;
    }

    public void setPositionSelected(int positionSelected) {
        this.positionSelected = positionSelected;
    }
}
