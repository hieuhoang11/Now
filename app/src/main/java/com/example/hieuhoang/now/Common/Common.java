package com.example.hieuhoang.now.Common;

import android.app.Dialog;
import android.content.Context;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.R;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


public class Common {

    public static void loadImageFromInternet(String path, Context context, ImageView imageView) {
        Picasso.with(context)
                .load(path)
                .placeholder(R.drawable.image_placeholder)
                .into(imageView);
    }

    public static void loadImageFromServer(String image, Context context, ImageView imageView) {
        loadImageFromInternet(AppConstant.SERVER_NAME_IMG + image, context, imageView);
    }

    public static String formatNumber(float number) {
        NumberFormat numberFormat = new DecimalFormat("###,###");
        return numberFormat.format(number) + "đ";
    }

    public static SpannableStringBuilder oldPriceFormat(String oldPrice) {
        SpannableStringBuilder spanBuilder = new SpannableStringBuilder(oldPrice);
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        spanBuilder.setSpan(strikethroughSpan, 0, oldPrice.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanBuilder;
    }

    public static String qualityPurchased(long quality) {
        if (quality >= 100000)
            return " 100k+";
        if (quality >= 50000)
            return " 50k+";
        if (quality >= 10000)
            return " 10k+";
        if (quality >= 5000)
            return " 5k+";
        if (quality >= 1000)
            return " 1k+";
        if (quality >= 500)
            return " 500+";
        if (quality >= 100)
            return " 100+";
        if (quality >= 50)
            return " 50+";
        if (quality >= 10)
            return " 10+";
        if (quality >= 5)
            return " 5+";
        if (quality >= 1)
            return " 1+";
        return " 0";
    }

    public static LatLng getCoordinates(Context context , String address){
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(address, 5);
            if (addresses.size() > 0) {
                return new LatLng(addresses.get(0).getLatitude(),addresses.get(0).getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null ;
    }

    public static String createKey(String prefix) {
//        String key = prefix;
//        String[] partsDay;
//            partsDay = DateTime.Now.ToShortDateString().Split('/');
////Ví dụ 07/08/2009
//        String d = String.Format("{0}{1}{2}", partsDay[0], partsDay[1], partsDay[2]);
//            key = key + d;
//        String[] partsTime;
//            partsTime = DateTime.Now.ToLongTimeString().Split(':');
////Ví dụ 7:08:03 PM hoặc 7:08:03 AM
//            if( partsTime[2].Substring(3, 2) == "PM" )
//                partsTime[0] = ConvertTimeTo24(partsTime[0]);
//            if( partsTime[2].Substring(3, 2) == "AM" )
//                if( partsTime[0].Length == 1 )
//                    partsTime[0] = "0" + partsTime[0];
////Xóa ký t trắng và PM hoặc AM
//            partsTime[2] = partsTime[2].Remove(2, 3);
//        String t;
//            t= String.Format("_{0}{1}{2}", partsTime[0], partsTime[1], partsTime[2]);
//            key = key + t;
//            return key;
        return "";
    }
}
