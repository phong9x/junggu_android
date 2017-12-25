package com.trams.joonggu_nubigo.view.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.view.customview.CustomTextViewNomal;

import java.util.ArrayList;

/**
 * Created by zin9x on 11/11/2015.
 */
public class TagsAdapter extends ArrayAdapter<String> {
    private Activity mActivity;
    private ArrayList<String> tags;

    public TagsAdapter(Activity activity, ArrayList<String> tags) {
        super(activity, -1, tags);

        mActivity = activity;
        this.tags = tags;
    }

    public static class ViewHolder {
        private CustomTextViewNomal tvName;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String item = getItem(position);
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.item_tags, parent, false);
            viewHolder.tvName = (CustomTextViewNomal) convertView.findViewById(R.id.tvName);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvName.setText(item);
        return convertView;
    }
}
