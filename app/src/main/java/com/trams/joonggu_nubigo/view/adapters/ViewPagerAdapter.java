package com.trams.joonggu_nubigo.view.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;
import com.trams.joonggu_nubigo.R;
import com.trams.joonggu_nubigo.common.Constant;
import com.trams.joonggu_nubigo.objects.ImageObj;

import java.util.ArrayList;

/**
 * Created by zin9x on 9/16/2015.
 */
public class ViewPagerAdapter extends PagerAdapter {
    private ArrayList<ImageObj> pagerItems;
    private LayoutInflater inflater;
    private Context context;
    private ItemClickListListener listener;
    private DisplayImageOptions mDisplayImageOptions;

    public interface ItemClickListListener {
        void onClick(int position);
    }

    public ViewPagerAdapter(Context context, ArrayList<ImageObj> pagerItems) {
        super();
        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(Constant.CACHE_ON_MEMORY)
                .cacheOnDisk(Constant.CACHE_ON_DISK)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .postProcessor(new BitmapProcessor() {
                    @Override
                    public Bitmap process(Bitmap bitmap) {
                        return scareBitmapThubnail(bitmap);
                    }
                })
                .build();

        this.pagerItems = pagerItems;
        this.context = context;

    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        inflater = LayoutInflater.from(context);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.item_viewpager, container, false);
        final ImageView imageViewCampaign = (ImageView) layout.findViewById(R.id.imgNewsFeed);
        imageViewCampaign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                HomeActivity.zoomImageFromThumb(imageViewCampaign, R.drawable.img_user_demo);
            }
        });
        //imageViewCampaign.setImageDrawable(context.getResources().getDrawable(R.drawable.img_user_demo));
//        ImageLoader.getInstance().displayImage(pagerItems.get(position).getUrlImage(), imageViewCampaign, mDisplayImageOptions);
        ImageLoader.getInstance().displayImage(pagerItems.get(position).getUrlImage(), imageViewCampaign, mDisplayImageOptions);

        container.addView(layout);
//        inflater = LayoutInflater.from(context);
//        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.item_viewpager, container, false);
//        final ImageView imageViewCampaign = (ImageView) layout.findViewById(R.id.imgNewsFeed);
//        imageViewCampaign.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                HomeActivity.zoomImageFromThumb(context, imageViewCampaign, pagerItems.get(position).getUrlImage());
//            }
//        });
//
////        Glide.with(context)
////                .load(WebServiceConfig.BASE_URL + pagerItems.get(position).getUrlImage())
////                .centerCrop()
////                .crossFade()
////                .into(imageViewCampaign);
//        mImageLoader.displayImage(WebServiceConfig.BASE_URL + pagerItems.get(position).getUrlImage(), imageViewCampaign, mDisplayImageOptions);
//        container.addView(layout);
        return layout;
    }


    private Bitmap scareBitmapThubnail(Bitmap bitmap) {
        if (bitmap.getWidth() < Constant.WIDTH_HEIGHT_IMG_DETAIL && bitmap.getHeight() < Constant.WIDTH_HEIGHT_IMG_DETAIL) {
            return bitmap;
        } else if (bitmap.getWidth() > bitmap.getHeight()) {
            float scale = bitmap.getWidth() / Constant.WIDTH_HEIGHT_IMG_DETAIL;
            return Bitmap.createScaledBitmap(bitmap, (int) Constant.WIDTH_HEIGHT_IMG_DETAIL, (int) (bitmap.getHeight() / scale), false);
        } else {
            float scale = bitmap.getHeight() / Constant.WIDTH_HEIGHT_IMG_DETAIL;
            return Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() / scale), (int) Constant.WIDTH_HEIGHT_IMG_DETAIL, false);
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public int getCount() {
        return pagerItems.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view.equals(obj);
    }

}