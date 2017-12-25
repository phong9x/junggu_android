package com.trams.joonggu_nubigo.view.adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.objects.NoticeObj;
import com.trams.joonggu_nubigo.utils.LogUtils;
import com.trams.joonggu_nubigo.view.customview.CustomTextViewNomal;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ADMIN on 11/4/2015.
 */
public class NoticeAdapter extends ArrayAdapter<NoticeObj> {

    private static final String TAG = NoticeAdapter.class.getName();
    private Context context;

    public NoticeAdapter(Context _context, List<NoticeObj> listNotice) {
        super(_context, R.layout.item_notice, listNotice);
        context = _context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final NoticeObj item = getItem(position);

        LogUtils.d(TAG, "getView , item : " + item.toString());

        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.item_notice, parent, false);
            holder = new ViewHolder();
            holder.imgOpen = (ImageView) convertView.findViewById(R.id.imgNoticeOpen);
            holder.tvTitle = (CustomTextViewNomal) convertView.findViewById(R.id.tvNoticeTitle);
            holder.tvContent = (CustomTextViewNomal) convertView.findViewById(R.id.tvNoticeContent);
            holder.layoutContent = (RelativeLayout) convertView.findViewById(R.id.layoutNoticeContent);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Format formatter = new SimpleDateFormat("yyyy.MM.dd");
        String date = formatter.format(item.getUpdateDate());

        String title = item.getTitle() + "  " + date;

        holder.tvTitle.setText(title);

        //Matcher all FAQ string in text view
        String matcher = date;
        SpannableString spannable = new SpannableString(holder.tvTitle.getText().toString());
        Pattern patternId = Pattern.compile(matcher);
        Matcher matcherId = patternId.matcher(holder.tvTitle.getText().toString());

        while (matcherId.find()) {
            // Set click event
//            spannable.setSpan(new ClickableSpan() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            }, matcherId.start(), matcherId.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            // Set FAQ string to bold
            spannable.setSpan(new RelativeSizeSpan(0.8f), matcherId.start(), matcherId.end(), 0);
            // Set FAQ string color
            spannable.setSpan(new ForegroundColorSpan(Color.argb(255, 172, 172, 172)), matcherId.start(), matcherId.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        holder.tvTitle.setText(spannable);

//        holder.tvTitle.setText(item.getTitle());
        holder.tvContent.setText(item.getContent());

        if (item.isOpen()) {
            holder.layoutContent.setVisibility(View.VISIBLE);
            holder.imgOpen.setImageResource(R.drawable.notice_open);
        } else {
            holder.layoutContent.setVisibility(View.GONE);
            holder.imgOpen.setImageResource(R.drawable.notice_close);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.layoutContent.getVisibility() == View.VISIBLE) {
                    holder.layoutContent.setVisibility(View.GONE);
                    holder.imgOpen.setImageResource(R.drawable.notice_close);
                    item.setOpen(false);
                } else {
                    holder.layoutContent.setVisibility(View.VISIBLE);
                    holder.imgOpen.setImageResource(R.drawable.notice_open);
                    item.setOpen(true);
                }
            }
        });

        return convertView;
    }

    public static class ViewHolder {
        ImageView imgOpen;
        CustomTextViewNomal tvTitle;
        CustomTextViewNomal tvContent;
        RelativeLayout layoutContent;
    }
}
