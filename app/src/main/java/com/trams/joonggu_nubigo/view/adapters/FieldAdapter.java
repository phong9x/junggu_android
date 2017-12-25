package com.trams.joonggu_nubigo.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;

import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.dao.Field;
import com.trams.joonggu_nubigo.utils.FontsUtils;
import com.trams.joonggu_nubigo.utils.PxUtils;
import com.trams.joonggu_nubigo.view.customview.CustomTextViewNomal;

import java.util.List;

/**
 * Created by Administrator on 13/11/2015.
 */
public class FieldAdapter extends ArrayAdapter<Field> {

    private static final String TAG = FieldAdapter.class.getName();
    private List<Field> listFields;

    private int selectedIdx = 0;

    public FieldAdapter(Context context, List<Field> listFields) {
        super(context, R.layout.item_field, listFields);
        this.listFields = listFields;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Field item = getItem(position);

        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.item_field, parent, false);
            holder = new ViewHolder();
            holder.tvName = (CustomTextViewNomal) convertView.findViewById(R.id.field_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position == selectedIdx) {
            FontsUtils.setFontTvBold(getContext(), holder.tvName);
        } else {
            FontsUtils.setFontTvNomal(getContext(), holder.tvName);
        }
        holder.tvName.setText(item.getName());

        int w = PxUtils.getWidhtScreen(getContext()) / listFields.size();
//        int h = (int) PxUtils.dpToPx(getContext(), (int) getContext().getResources().getDimension(R.dimen.fragment_map_field_height));

//        convertView.setLayoutParams(new RelativeLayout.LayoutParams(PxUtils.getWidhtScreen(context) / 6, (int) PxUtils.convertDpToPixel(context.getResources().getDimension(R.dimen.fragment_map_field_height), context)));
        convertView.setLayoutParams(new RelativeLayout.LayoutParams(w, RelativeLayout.LayoutParams.MATCH_PARENT));

        return convertView;
    }

    public static class ViewHolder {
        CustomTextViewNomal tvName;
    }

    public void setSelectedIdx(int idx) {
        selectedIdx = idx;
    }

}
