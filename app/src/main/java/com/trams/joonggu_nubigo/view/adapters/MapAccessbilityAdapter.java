package com.trams.joonggu_nubigo.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.dao.Accessibility;
import com.trams.joonggu_nubigo.utils.LogUtils;
import com.trams.joonggu_nubigo.utils.PxUtils;
import com.trams.joonggu_nubigo.view.customview.CustomTextViewNomal;

import java.util.List;

/**
 * Created by Administrator on 13/11/2015.
 */
public class MapAccessbilityAdapter extends ArrayAdapter<Accessibility> {

    private static final String TAG = MapAccessbilityAdapter.class.getName();
    private List<Accessibility> accessibilityList;

    public MapAccessbilityAdapter(Context context, List<Accessibility> listSetting) {
        super(context, R.layout.item_map_accessbility, listSetting);
        this.accessibilityList = listSetting;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Accessibility item = getItem(position);

        LogUtils.d(TAG, "getView , item : " + item.toString());

        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.item_map_accessbility, parent, false);
            holder = new ViewHolder();
            holder.tvName = (CustomTextViewNomal) convertView.findViewById(R.id.tv_item_map_accessbility);
            holder.imgAcc = (ImageView) convertView.findViewById(R.id.img_item_map_accessbility);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (item.getName().equals("장애인전용 주차장")) {
            holder.tvName.setText(getContext().getResources().getString(R.string.accessbility_name_1));
        } else {
            holder.tvName.setText(item.getName());
        }

        if (getContext() != null)
            holder.tvName.setTextColor(getContext().getResources().getColor(R.color.item_accessbility_selected));

        switch ((int) item.getId()) {
            case 1:
                holder.imgAcc.setImageResource(R.drawable.accessibility_type1_selected);
                break;
            case 2:
                holder.imgAcc.setImageResource(R.drawable.accessibility_type2_selected);
                break;
            case 3:
                holder.imgAcc.setImageResource(R.drawable.accessibility_type3_selected);
                break;
            case 4:
                holder.imgAcc.setImageResource(R.drawable.accessibility_type4_selected);
                break;
            case 5:
                holder.imgAcc.setImageResource(R.drawable.accessibility_type5_selected);
                break;
            case 6:
                holder.imgAcc.setImageResource(R.drawable.accessibility_type6_selected);
                break;
        }

        if (item.getSelected() == 0) {
            if (getContext() != null)
                holder.tvName.setTextColor(getContext().getResources().getColor(R.color.item_accessbility_non_selected));
            switch ((int) item.getId()) {
                case 1:
                    holder.imgAcc.setImageResource(R.drawable.map_setting_icon1);
                    break;
                case 2:
                    holder.imgAcc.setImageResource(R.drawable.map_setting_icon2);
                    break;
                case 3:
                    holder.imgAcc.setImageResource(R.drawable.map_setting_icon3);
                    break;
                case 4:
                    holder.imgAcc.setImageResource(R.drawable.map_setting_icon4);
                    break;
                case 5:
                    holder.imgAcc.setImageResource(R.drawable.map_setting_icon5);
                    break;
                case 6:
                    holder.imgAcc.setImageResource(R.drawable.map_setting_icon6);
                    break;
            }

        } else {
            if (getContext() != null)
                holder.tvName.setTextColor(getContext().getResources().getColor(R.color.item_accessbility_selected));
            switch ((int) item.getId()) {
                case 1:
                    holder.imgAcc.setImageResource(R.drawable.accessibility_type1_selected);
                    break;
                case 2:
                    holder.imgAcc.setImageResource(R.drawable.accessibility_type2_selected);
                    break;
                case 3:
                    holder.imgAcc.setImageResource(R.drawable.accessibility_type3_selected);
                    break;
                case 4:
                    holder.imgAcc.setImageResource(R.drawable.accessibility_type4_selected);
                    break;
                case 5:
                    holder.imgAcc.setImageResource(R.drawable.accessibility_type5_selected);
                    break;
                case 6:
                    holder.imgAcc.setImageResource(R.drawable.accessibility_type6_selected);
                    break;
            }
        }

        int w = 0;

        if (getContext() != null) {
            w = PxUtils.getWidhtScreen(getContext()) / accessibilityList.size();
        }

        int h = 0;
        if (getContext() != null) {
            h = (int) PxUtils.dpToPx(getContext(), (int) getContext().getResources().getDimension(R.dimen.fragment_map_field_height));
        }

        LogUtils.d(TAG, "wwwwwwwwww : " + w + " , hhhhhhhhhhhhhhh : " + h);

//        convertView.setLayoutParams(new RelativeLayout.LayoutParams(PxUtils.getWidhtScreen(context) / 6, (int) PxUtils.convertDpToPixel(context.getResources().getDimension(R.dimen.fragment_map_field_height), context)));
        convertView.setLayoutParams(new RelativeLayout.LayoutParams(w, RelativeLayout.LayoutParams.MATCH_PARENT));

//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(item.getSelected() == 1){
//                    item.setSelected(0);
//                    holder.tvName.setTextColor(context.getResources().getColor(R.color.item_accessbility_non_selected));
//
//                    switch ((int) item.getId()) {
//                        case 1:
//                            holder.imgAcc.setImageResource(R.drawable.mypage_setting_icon1);
//                            break;
//                        case 2:
//                            holder.imgAcc.setImageResource(R.drawable.mypage_setting_icon2);
//                            break;
//                        case 3:
//                            holder.imgAcc.setImageResource(R.drawable.mypage_setting_icon3);
//                            break;
//                        case 4:
//                            holder.imgAcc.setImageResource(R.drawable.mypage_setting_icon4);
//                            break;
//                        case 5:
//                            holder.imgAcc.setImageResource(R.drawable.mypage_setting_icon5);
//                            break;
//                        case 6:
//                            holder.imgAcc.setImageResource(R.drawable.mypage_setting_icon6);
//                            break;
//                    }
//
//                }else{
//                    item.setSelected(1);
//                    holder.tvName.setTextColor(context.getResources().getColor(R.color.item_accessbility_selected));
//
//                    switch ((int) item.getId()) {
//                        case 1:
//                            holder.imgAcc.setImageResource(R.drawable.accessibility_type1_selected);
//                            break;
//                        case 2:
//                            holder.imgAcc.setImageResource(R.drawable.accessibility_type2_selected);
//                            break;
//                        case 3:
//                            holder.imgAcc.setImageResource(R.drawable.accessibility_type3_selected);
//                            break;
//                        case 4:
//                            holder.imgAcc.setImageResource(R.drawable.accessibility_type4_selected);
//                            break;
//                        case 5:
//                            holder.imgAcc.setImageResource(R.drawable.accessibility_type5_selected);
//                            break;
//                        case 6:
//                            holder.imgAcc.setImageResource(R.drawable.accessibility_type6_selected);
//                            break;
//                    }
//                }
//            }
//        });

        return convertView;
    }

    public static class ViewHolder {
        ImageView imgAcc;
        CustomTextViewNomal tvName;

    }

}
