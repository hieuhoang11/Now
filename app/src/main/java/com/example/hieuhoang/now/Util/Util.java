package com.example.hieuhoang.now.Util;

import android.content.Context;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.widget.ImageView;

import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.R;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class Util {

    // Mang cac ky tu goc co dau
    private static char[] SOURCE_CHARACTERS = {'À', 'Á', 'Â', 'Ã', 'È', 'É',
            'Ê', 'Ì', 'Í', 'Ò', 'Ó', 'Ô', 'Õ', 'Ù', 'Ú', 'Ý', 'à', 'á', 'â',
            'ã', 'è', 'é', 'ê', 'ì', 'í', 'ò', 'ó', 'ô', 'õ', 'ù', 'ú', 'ý',
            'Ă', 'ă', 'Đ', 'đ', 'Ĩ', 'ĩ', 'Ũ', 'ũ', 'Ơ', 'ơ', 'Ư', 'ư', 'Ạ',
            'ạ', 'Ả', 'ả', 'Ấ', 'ấ', 'Ầ', 'ầ', 'Ẩ', 'ẩ', 'Ẫ', 'ẫ', 'Ậ', 'ậ',
            'Ắ', 'ắ', 'Ằ', 'ằ', 'Ẳ', 'ẳ', 'Ẵ', 'ẵ', 'Ặ', 'ặ', 'Ẹ', 'ẹ', 'Ẻ',
            'ẻ', 'Ẽ', 'ẽ', 'Ế', 'ế', 'Ề', 'ề', 'Ể', 'ể', 'Ễ', 'ễ', 'Ệ', 'ệ',
            'Ỉ', 'ỉ', 'Ị', 'ị', 'Ọ', 'ọ', 'Ỏ', 'ỏ', 'Ố', 'ố', 'Ồ', 'ồ', 'Ổ',
            'ổ', 'Ỗ', 'ỗ', 'Ộ', 'ộ', 'Ớ', 'ớ', 'Ờ', 'ờ', 'Ở', 'ở', 'Ỡ', 'ỡ',
            'Ợ', 'ợ', 'Ụ', 'ụ', 'Ủ', 'ủ', 'Ứ', 'ứ', 'Ừ', 'ừ', 'Ử', 'ử', 'Ữ',
            'ữ', 'Ự', 'ự',};

    // Mang cac ky tu thay the khong dau
    private static char[] DESTINATION_CHARACTERS = {'A', 'A', 'A', 'A', 'E',
            'E', 'E', 'I', 'I', 'O', 'O', 'O', 'O', 'U', 'U', 'Y', 'a', 'a',
            'a', 'a', 'e', 'e', 'e', 'i', 'i', 'o', 'o', 'o', 'o', 'u', 'u',
            'y', 'A', 'a', 'D', 'd', 'I', 'i', 'U', 'u', 'O', 'o', 'U', 'u',
            'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A',
            'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'E', 'e',
            'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E',
            'e', 'I', 'i', 'I', 'i', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o',
            'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O',
            'o', 'O', 'o', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u',
            'U', 'u', 'U', 'u',};

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

    public static String formatPriceStore(float number) {
        return String.valueOf((int) (number / 1000)) + "k";
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

//    public static SpannableString  underlined (String string) {
//        SpannableString noidungspanned = new SpannableString(string);
//        noidungspanned.setSpan(new UnderlineSpan(), 24, 33, 0);
//        return  noidungspanned ;
//    }

    public static LatLng getCoordinates(Context context, String address) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(address, 5);
            if (addresses.size() > 0) {
                return new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean parseBooleanJson(String dataJson) throws JSONException {
        JSONObject jsonObject = new JSONObject(dataJson);
        return jsonObject.getBoolean(AppConstant.RESULT);

    }

    public static String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return format.format(date);
    }

    public static char removeAccent(char ch) {
        int index = Arrays.binarySearch(SOURCE_CHARACTERS, ch);
        if (index >= 0) {
            ch = DESTINATION_CHARACTERS[index];
        }
        return ch;
    }

    /**
     * Bo dau 1 chuoi
     *
     * @param s
     * @return
     */
    public static String removeAccent(String s) {
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < sb.length(); i++) {
            sb.setCharAt(i, removeAccent(sb.charAt(i)));
        }
        return sb.toString();
    }

    public static String standardizeString(String str) {
        return str.trim().replaceAll("\\s+", " ");
    }

    public static int getIdColor(Context context, int idColor) {
        int color = 0;
        if (Build.VERSION.SDK_INT > 21)
            color = ContextCompat.getColor(context, idColor);
        else color = context.getResources().getColor(idColor);
        return color;
    }

    public static String getAddress(Context context, Location location) {
        if (location == null) {
            return null;
        }
        return getAddress(context,location.getLatitude(),location.getLongitude()) ;
    }

    public static String getAddress(Context context, Double latitude, Double longitude) {

        Geocoder geoCoder = new Geocoder(
                context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geoCoder.getFromLocation(
                    latitude,
                    longitude, 1);

            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                String data[] = address.getAddressLine(0).split(",");
                String add = "";
                for (int i = 0; i < data.length - 2; i++) {
                    add += data[i] + ",";
                }
                add += data[data.length - 1] ;
                return add;
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }
}
