package com.example.hieuhoang.now.Common;

import android.content.Context;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.widget.ImageView;

import com.example.hieuhoang.now.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class Common {

    public static void loadImageFromInternet(String path, Context context , ImageView imageView) {
        Picasso.with(context)
                .load(path)
                .placeholder(R.drawable.image_placeholder)
                .into(imageView);
    }

    public static String formatNumber (float number) {
        NumberFormat numberFormat = new DecimalFormat("###,###");
        return numberFormat.format(number) + "đ";
    }

    public static SpannableStringBuilder oldPriceFormat (String oldPrice) {
        SpannableStringBuilder spanBuilder = new SpannableStringBuilder(oldPrice);
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        spanBuilder.setSpan(strikethroughSpan, 0, oldPrice.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanBuilder;
    }
    public static String qualityPurchased (long quality) {
        if(quality >= 100000)
            return " 100k+" ;
        if(quality >= 50000)
            return " 50k+" ;
        if(quality >= 10000)
            return " 10k+" ;
        if(quality >= 5000)
            return " 5k+" ;
        if(quality >= 1000)
            return " 1k+" ;
        if(quality >= 500)
            return " 500+" ;
        if(quality >= 100)
            return " 100+" ;
        if(quality >= 50)
            return " 50+" ;
        if(quality >= 10)
            return " 10+" ;
        if(quality >= 5)
            return " 5+" ;
        if(quality >= 1)
            return " 1+" ;
        return " 0";
    }
}
