package com.trams.joonggu_nubigo.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;

import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.objects.CommentObj;
import com.trams.joonggu_nubigo.utils.DateTimeUtils;
import com.trams.joonggu_nubigo.view.customview.CustomTextViewNomal;

import java.util.List;

/**
 * Created by zin9x on 11/13/2015.
 */
public class CommentAdapter extends ArrayAdapter<CommentObj> {

    public CommentAdapter(Context context, List<CommentObj> categories) {
        super(context, R.layout.item_comment, categories);
    }

    public static class ViewHolder {
        CustomTextViewNomal tvDate, tvName, tvComment;
        RatingBar rbUserComment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommentObj item = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.item_comment, parent, false);
            holder = new ViewHolder();
            holder.tvDate = (CustomTextViewNomal) convertView.findViewById(R.id.tvDate);
            holder.tvName = (CustomTextViewNomal) convertView.findViewById(R.id.tvName);
            holder.tvComment = (CustomTextViewNomal) convertView.findViewById(R.id.tvComment);
            holder.rbUserComment = (RatingBar) convertView.findViewById(R.id.rb_user_comment);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvDate.setText(DateTimeUtils.getDateFromLong(item.getUpdateDate()));
        holder.tvName.setText(item.getName());
        holder.tvComment.setText(item.getDetails());
        holder.rbUserComment.setRating(item.getGrade());
        return convertView;
    }
}
