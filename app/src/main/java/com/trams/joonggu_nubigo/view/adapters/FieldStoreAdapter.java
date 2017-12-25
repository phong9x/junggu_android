package com.trams.joonggu_nubigo.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.view.customview.CustomTextViewNomal;

import java.util.ArrayList;

/**
 * Created by Administrator on 02/12/2015.
 */
public class FieldStoreAdapter extends ArrayAdapter<String> {
    private static final String TAG = FieldStoreAdapter.class.getName();

    public FieldStoreAdapter(Context context, ArrayList<String> listFields) {
        super(context, R.layout.item_field_view_text, listFields);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String item = getItem(position);

        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.item_field_view_text, parent, false);
            holder = new ViewHolder();
            holder.tvName = (CustomTextViewNomal) convertView.findViewById(R.id.tv_field_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvName.setText(item);
        return convertView;

    }

    public static class ViewHolder {
        CustomTextViewNomal tvName;
    }


}
