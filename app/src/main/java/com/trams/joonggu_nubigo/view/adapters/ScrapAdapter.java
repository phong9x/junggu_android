package com.trams.joonggu_nubigo.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.dao.Store;
import com.trams.joonggu_nubigo.view.customview.StoreView;

import java.util.List;

/**
 * Created by Administrator on 12/11/2015.
 */
public class ScrapAdapter extends ArrayAdapter<Store> {

    private DisplayImageOptions mDisplayImageOptions;
    private static final String TAG = ScrapAdapter.class.getName();

    public ScrapAdapter(Context context, List<Store> stores) {
        super(context, R.layout.item_store, stores);
    }

    public static class ViewHolder {
        StoreView storeView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Store item = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.item_store, parent, false);
            holder = new ViewHolder();
            holder.storeView = (StoreView) convertView.findViewById(R.id.store_view);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.storeView.updateView(item);

        return convertView;
    }
}
