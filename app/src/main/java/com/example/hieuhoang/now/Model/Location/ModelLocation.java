package com.example.hieuhoang.now.Model.Location;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

public class ModelLocation implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    public static final String TAG = "kiemtra";
    private static final long UPDATE_INTERVAL = 5000;
    private static final long FASTEST_INTERVAL = 5000;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private Context context;

    public ModelLocation(Context context) {
        this.context = context;
        if (isPlayServicesAvailable()) {
            setUpLocationClientIfNeeded();
            buildLocationRequest();
        } else {
            Log.i(TAG, "Device does not support Google Play services");
        }
    }

    public Location getLastLocation() {
        return mLastLocation;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (lastLocation != null) {
            mLastLocation = lastLocation;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private boolean isPlayServicesAvailable() {
        return isPlayServicesAvailable(context);
    }

    private boolean isGpsOn() {
        return isGpsOn(context);
    }

    public static boolean isPlayServicesAvailable(Context context) {
        return GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context)
                == ConnectionResult.SUCCESS;
    }

    public static boolean isGpsOn(Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void setUpLocationClientIfNeeded() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    private void buildLocationRequest() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
    }

    public void desTroy() {
        if (mGoogleApiClient != null
                && mGoogleApiClient.isConnected()) {
            //stopLocationUpdates();
            mGoogleApiClient.disconnect();
            mGoogleApiClient = null;
        }
        Log.d(TAG, "onDestroy LocationService");
    }

    public String getAddress() {
        return getAddress(this.mLastLocation) ;
    }

    public String getAddress(Location location) {
        if (location == null) {
            Log.i(TAG, "getAddress: null");
            return null;
        }
        Geocoder geoCoder = new Geocoder(
                context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geoCoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(), 1);

            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                return address.getAddressLine(0);
            }

        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }
}
