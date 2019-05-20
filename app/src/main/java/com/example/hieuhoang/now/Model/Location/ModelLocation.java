package com.example.hieuhoang.now.Model.Location;


import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import com.example.hieuhoang.now.Util.Util;


import static android.content.Context.LOCATION_SERVICE;

public class ModelLocation implements android.location.LocationListener {

    private static final String MYTAG = "MYTAG";

    private Context context;

    public ModelLocation(Context context) {
        this.context = context;
    }

    private String getEnabledLocationProvider() {
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

        // Tiêu chí để tìm một nhà cung cấp vị trí.
        Criteria criteria = new Criteria();

        // Tìm một nhà cung vị trí hiện thời tốt nhất theo tiêu chí trên.
        // ==> "gps", "network",...
        String bestProvider = locationManager.getBestProvider(criteria, true);

        boolean enabled = locationManager.isProviderEnabled(bestProvider);

        if (!enabled) {
            Log.i(MYTAG, "No location provider enabled!");
            return null;
        }
        return bestProvider;
    }

    // Chỉ gọi phương thức này khi đã có quyền xem vị trí người dùng.
    public Location getMyLocation() {
        String address = null;
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

        String locationProvider = this.getEnabledLocationProvider();

        if (locationProvider == null) {
            return null;
        }

        // Millisecond
        final long MIN_TIME_BW_UPDATES = 1000;
        // Met
        final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;

        Location location = null;
        try {

            // Đoạn code nay cần người dùng cho phép (Hỏi ở trên ***).
            locationManager.requestLocationUpdates(
                    locationProvider,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, (android.location.LocationListener) this);

            // Lấy ra vị trí.
            location = locationManager
                    .getLastKnownLocation(locationProvider);
        }
        // Với Android API >= 23 phải catch SecurityException.
        catch (SecurityException e) {
            Log.e(MYTAG, "Show My myLocation Error:" + e.getMessage());
            e.printStackTrace();
            return null;
        }

        if (location != null) {
            address = Util.getAddress(context, location);
            Log.i(MYTAG, "showMyLocation: " + location.getLatitude() + " " + location.getLongitude());
            Log.i(MYTAG, "showMyLocation: " + address);
        } else {

            Log.i(MYTAG, "myLocation not found");
        }

        return location;
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}