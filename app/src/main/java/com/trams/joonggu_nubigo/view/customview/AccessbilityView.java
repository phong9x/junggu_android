package com.trams.joonggu_nubigo.view.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.trams.joonggu_nubigo.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 12/11/2015.
 */
public class AccessbilityView extends LinearLayout{

    private ImageView imgAccItem1,imgAccItem2,imgAccItem3,imgAccItem4,imgAccItem5,imgAccItem6;

    private static final String TAG = AccessbilityView.class.getName();
    private Context context;

    public AccessbilityView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public AccessbilityView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public AccessbilityView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public AccessbilityView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        this.context = context;
//        init();
//    }

    private void init(){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.accessbility_view, this, true);
        imgAccItem1 = (ImageView) findViewById(R.id.imgAccessbility1);
        imgAccItem2 = (ImageView) findViewById(R.id.imgAccessbility2);
        imgAccItem3 = (ImageView) findViewById(R.id.imgAccessbility3);
        imgAccItem4 = (ImageView) findViewById(R.id.imgAccessbility4);
        imgAccItem5 = (ImageView) findViewById(R.id.imgAccessbility5);
        imgAccItem6 = (ImageView) findViewById(R.id.imgAccessbility6);

    }

    public void setView(boolean item1Select,boolean item2Select,boolean item3Select,boolean item4Select,boolean item5Select,boolean item6Select){
        if(item1Select){
            imgAccItem1.setImageResource(R.drawable.accessibility_type1_selected);
        }else{
            imgAccItem1.setImageResource(R.drawable.accessibility_type1_normal);
        }

        if(item2Select){
            imgAccItem2.setImageResource(R.drawable.accessibility_type2_selected);
        }else{
            imgAccItem2.setImageResource(R.drawable.accessibility_type2_normal);
        }

        if(item3Select){
            imgAccItem3.setImageResource(R.drawable.accessibility_type3_selected);
        }else{
            imgAccItem3.setImageResource(R.drawable.accessibility_type3_normal);
        }

        if(item4Select){
            imgAccItem4.setImageResource(R.drawable.accessibility_type4_selected);
        }else{
            imgAccItem4.setImageResource(R.drawable.accessibility_type4_normal);
        }

        if(item5Select){
            imgAccItem5.setImageResource(R.drawable.accessibility_type5_selected);
        }else{
            imgAccItem5.setImageResource(R.drawable.accessibility_type5_normal);
        }

        if(item6Select){
            imgAccItem6.setImageResource(R.drawable.accessibility_type6_selected);
        }else{
            imgAccItem6.setImageResource(R.drawable.accessibility_type6_normal);
        }
    }

    public void setView(ArrayList<Integer> listAcc){

        if(listAcc.contains(1)){
            imgAccItem1.setImageResource(R.drawable.accessibility_type1_selected);
        }else{
            imgAccItem1.setImageResource(R.drawable.accessibility_type1_normal);
        }

        if(listAcc.contains(2)){
            imgAccItem2.setImageResource(R.drawable.accessibility_type2_selected);
        }else{
            imgAccItem2.setImageResource(R.drawable.accessibility_type2_normal);
        }

        if(listAcc.contains(3)){
            imgAccItem3.setImageResource(R.drawable.accessibility_type3_selected);
        }else{
            imgAccItem3.setImageResource(R.drawable.accessibility_type3_normal);
        }

        if(listAcc.contains(4)){
            imgAccItem4.setImageResource(R.drawable.accessibility_type4_selected);
        }else{
            imgAccItem4.setImageResource(R.drawable.accessibility_type4_normal);
        }

        if(listAcc.contains(5)){
            imgAccItem5.setImageResource(R.drawable.accessibility_type5_selected);
        }else{
            imgAccItem5.setImageResource(R.drawable.accessibility_type5_normal);
        }

        if(listAcc.contains(6)){
            imgAccItem6.setImageResource(R.drawable.accessibility_type6_selected);
        }else{
            imgAccItem6.setImageResource(R.drawable.accessibility_type6_normal);
        }
    }

}
