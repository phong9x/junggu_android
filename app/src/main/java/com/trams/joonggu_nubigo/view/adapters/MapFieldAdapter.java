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
import com.trams.joonggu_nubigo.utils.LogUtils;
import com.trams.joonggu_nubigo.utils.PxUtils;
import com.trams.joonggu_nubigo.view.customview.CustomTextViewNomal;

import java.util.List;

/**
 * Created by Administrator on 13/11/2015.
 */
public class MapFieldAdapter extends ArrayAdapter<Field> {

    private static final String TAG = MapFieldAdapter.class.getName();
    private List<Field> listFields;
    private int positionSelected = 0;

    public MapFieldAdapter(Context context, List<Field> listFields) {
        super(context, R.layout.item_map_field, listFields);
        this.listFields = listFields;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Field item = getItem(position);

        LogUtils.d(TAG, "getView , item : " + item.toString());

        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.item_map_field, parent, false);
            holder = new ViewHolder();
            holder.tvName = (CustomTextViewNomal) convertView.findViewById(R.id.tv_name_item_map_field);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvName.setText(item.getName());

        if(position == positionSelected){
            FontsUtils.setFontTvBold(getContext(), holder.tvName);
        }else{
            FontsUtils.setFontTvNomal(getContext(), holder.tvName);
        }

        int w = 150;

        w = PxUtils.getWidhtScreen(getContext()) / listFields.size();

//        if(listFields.size() <= 6){
//            w = PxUtils.getWidhtScreen(getContext()) / listFields.size();
//        }else{
//            w = 200;
//        }

        int h = (int) PxUtils.dpToPx(getContext(), (int) getContext().getResources().getDimension(R.dimen.fragment_map_field_height));

        LogUtils.d(TAG, "wwwwwwwwww : " + w + " , hhhhhhhhhhhhhhh : " + h);

//        convertView.setLayoutParams(new RelativeLayout.LayoutParams(PxUtils.getWidhtScreen(context) / 6, (int) PxUtils.convertDpToPixel(context.getResources().getDimension(R.dimen.fragment_map_field_height), context)));
        convertView.setLayoutParams(new RelativeLayout.LayoutParams(w, RelativeLayout.LayoutParams.MATCH_PARENT));

        return convertView;
    }

    public static class ViewHolder {
        CustomTextViewNomal tvName;
    }

    public int getPositionSelected() {
        return positionSelected;
    }

    public void setPositionSelected(int positionSelected) {
        this.positionSelected = positionSelected;
    }

}
