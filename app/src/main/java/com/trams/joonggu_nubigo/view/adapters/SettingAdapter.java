package com.trams.joonggu_nubigo.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.dao.Accessibility;
import com.trams.joonggu_nubigo.utils.LogUtils;
import com.trams.joonggu_nubigo.view.customview.CustomTextViewNomal;

import java.util.List;


/**
 * Created by ADMIN on 11/6/2015.
 */
public class SettingAdapter extends ArrayAdapter<Accessibility> {
    private static final String TAG = "NoticeAdapter";
    private Context context;

    public SettingAdapter(Context _context, List<Accessibility> listSetting) {
        super(_context, R.layout.item_setting_layout, listSetting);
        this.context = _context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Accessibility item = getItem(position);

        LogUtils.d(TAG, "getView , item : " + item.toString());

        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.item_setting_layout, parent, false);
            holder = new ViewHolder();
            holder.imgIcon = (ImageView) convertView.findViewById(R.id.imgItemSetting);
            holder.tvContent = (CustomTextViewNomal) convertView.findViewById(R.id.tvItemSettingContent);
            holder.cbAccess = (CheckBox) convertView.findViewById(R.id.cbItemSetting);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        switch ((int) item.getId()) {
            case 1:
                holder.imgIcon.setImageResource(R.drawable.mypage_setting_icon1);
                break;
            case 2:
                holder.imgIcon.setImageResource(R.drawable.mypage_setting_icon2);
                break;
            case 3:
                holder.imgIcon.setImageResource(R.drawable.mypage_setting_icon3);
                break;
            case 4:
                holder.imgIcon.setImageResource(R.drawable.mypage_setting_icon4);
                break;
            case 5:
                holder.imgIcon.setImageResource(R.drawable.mypage_setting_icon5);
                break;
            case 6:
                holder.imgIcon.setImageResource(R.drawable.mypage_setting_icon6);
                break;
        }
        holder.tvContent.setText(item.getName());

        holder.cbAccess.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LogUtils.d(TAG, "onCheckedChanged : " + position + " , isChecked : " + isChecked);
//                item.setIsChecked(isChecked);

                if(isChecked == true){
                    item.setSelected(1);
                }else{
                    item.setSelected(0);
                }

            }
        });

        if (item.getSelected() == 1) {
            holder.cbAccess.setChecked(true);
        } else {
            holder.cbAccess.setChecked(false);
        }

        return convertView;
    }

    public static class ViewHolder {
        ImageView imgIcon;
        CustomTextViewNomal tvContent;
        CheckBox cbAccess;
    }

}
