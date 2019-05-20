package com.example.hieuhoang.now.View.Test.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hieuhoang.now.Util.Util;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.Locale;
import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.location.Address;
import android.location.Geocoder;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.io.IOException;
import java.util.Locale;

import com.example.hieuhoang.now.Model.Location.ModelLocation;
import com.example.hieuhoang.now.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
//import com.graphhopper.GHRequest;
//import com.graphhopper.GHResponse;
//import com.graphhopper.GraphHopper;
//import com.graphhopper.routing.util.EncodingManager;

public class MapActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
//    TextView longitude, latitude, loca, longitude2, latitude2, kc;
//    EditText vitri2;
//    Button getLocation;
//    private myLocation location;
//    private GoogleApiClient gac;
//    private ModelLocation modelLocation;
//    String TAG = "kiemtra";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_map);
//
//        longitude = (TextView) findViewById(R.id.longitude);
//        latitude = (TextView) findViewById(R.id.latitude);
//        loca = (TextView) findViewById(R.id.loc);
//        getLocation = findViewById(R.id.getLocation);
//        longitude2 = findViewById(R.id.longitude2);
//        latitude2 = findViewById(R.id.latitude2);
//        kc = findViewById(R.id.kc);
//        vitri2 = findViewById(R.id.vitri);
//        //modelLocation = new ModelLocation(this,getApplicationContext()) ;
//        getLocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //String add = modelLocation.getLocation() ;
////                if(add != null) {
////                    loca.setText(add);
////                }
//                getLocation();
//                // calculateDistance();
//            }
//        });
//        // Trước tiên chúng ta cần phải kiểm tra play services
//        if (checkPlayServices()) {
//            // Building the GoogleApi client
//            buildGoogleApiClient();
//        }
//    }
//
//    private void calculateDistance() {
//        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//
//        try {
//            List<Address> addresses = geocoder.getFromLocationName(
//                    this.vitri2.getText().toString(), 5);
//            if (addresses.size() > 0) {
//                float[] re = new float[1];
//
//                // String kc = getDistance(location.getLatitude(),location.getLongitude(),addresses.get(0).getLatitude(),addresses.get(0).getLongitude());
//                myLocation.distanceBetween(location.getLatitude(), location.getLongitude(), addresses.get(0).getLatitude(), addresses.get(0).getLongitude(), re);
//                this.latitude2.setText("latitude 2 : " + addresses.get(0).getLatitude());
//                this.longitude2.setText("longitude 2 : " + addresses.get(0).getLongitude());
//                this.kc.setText("khoang cach :" + re[0]);
////  p = new GeoPoint(
////                        (int) (addresses.get(0).getLatitude() * 1E6),
////                        (int) (addresses.get(0).getLongitude() * 1E6));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void getLocation() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // Kiểm tra quyền hạn
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
//        } else {
//            location = LocationServices.FusedLocationApi.getLastLocation(gac);
//
//            if (location != null) {
//
//                double latitude = location.getLatitude();
//                double longitude = location.getLongitude();
//                // Hiển thị
//                this.latitude.setText("latitude :" + latitude);
//                this.longitude.setText("longitude :" + longitude);
//
//                Geocoder geoCoder = new Geocoder(
//                        this, Locale.getDefault());
//                List<Address> addresses = null;
//                try {
//                    addresses = geoCoder.getFromLocation(
//                            location.getLatitude(),
//                            location.getLongitude(), 1);
//                    String add = "";
//                    if (addresses.size() > 0) {
//                        Address address = addresses.get(0);
//                        String addressText = "";
//                        addressText += address.getAddressLine(0);
//                        this.loca.setText("vi tri :" + addressText);
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Log.i("kiemtra", "getLocation: fail");
//                }
//                return;
//
//            }
//            this.latitude.setText("(Không thể hiển thị vị trí. " +
//                    "Bạn đã kích hoạt location trên thiết bị chưa?)");
//
//        }
//    }
//
//    protected synchronized void buildGoogleApiClient() {
//        if (gac == null) {
//            gac = new GoogleApiClient.Builder(this)
//                    .addConnectionCallbacks(this)
//                    .addOnConnectionFailedListener(this)
//                    .addApi(LocationServices.API).build();
//            Log.i(TAG, "buildGoogleApiClient: ");
//        }
//    }
//
//    /**
//     * Phương thức kiểm chứng google play services trên thiết bị
//     */
//    private boolean checkPlayServices() {
//        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
//        if (resultCode != ConnectionResult.SUCCESS) {
//            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
//                GooglePlayServicesUtil.getErrorDialog(resultCode, this, 1000).show();
//            } else {
//                Toast.makeText(this, "Thiết bị này không hỗ trợ.", Toast.LENGTH_LONG).show();
//                finish();
//            }
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//        // Đã kết nối với google api, lấy vị trí
//        Log.i(TAG, "onConnected: ");
//        getLocation();
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//        Log.i(TAG, "onConnectionSuspended: ");
//        //gac.connect();
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        Log.i(TAG, "onConnectionFailed: ");
//        Toast.makeText(this, "Lỗi kết nối: " + connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
//    }
//
//    protected void onStart() {
//        gac.connect();
//        super.onStart();
//    }
//
//    protected void onStop() {
//        //gac.disconnect();
//        super.onStop();
//    }


    public static final String TAG = "kiemtra";
    private static final long UPDATE_INTERVAL = 5000;
    private static final long FASTEST_INTERVAL = 5000;
    private static final int REQUEST_LOCATION_PERMISSION = 100;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private boolean mIsAutoUpdateLocation;

    private TextView mTvCurrentLocation;
    private Button mBtnGetLocation;
    private Switch mSwAutoUpdateLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Log.d(TAG, "onCreate");
        initViews();

        requestLocationPermissions();

        if (isPlayServicesAvailable()) {
            setUpLocationClientIfNeeded();
            buildLocationRequest();
        } else {
            mTvCurrentLocation.setText("Device does not support Google Play services");
        }

        mBtnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGpsOn()) {
                    updateUi();
                } else {
                    Toast.makeText(MapActivity.this, "GPS is OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mSwAutoUpdateLocation.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (!isGpsOn()) {
                            Toast.makeText(MapActivity.this, "GPS is OFF",
                                    Toast.LENGTH_SHORT).show();
                            mSwAutoUpdateLocation.setChecked(false);
                            return;
                        }
                        mIsAutoUpdateLocation = isChecked;
                        if (isChecked) {
                            startLocationUpdates();
                        } else {
                            stopLocationUpdates();
                        }
                    }
                });
    }

    private void initViews() {
        mTvCurrentLocation = (TextView) findViewById(R.id.tv_current_location);
        mBtnGetLocation = (Button) findViewById(R.id.btn_get_location);
        mSwAutoUpdateLocation = (Switch) findViewById(R.id.sw_auto_update_location);
    }

    private void updateUi() {
        if (mLastLocation != null) {
//            mTvCurrentLocation.setText(String.format(Locale.getDefault(), "%f, %f",
//                    mLastLocation.getLatitude(), mLastLocation.getLongitude()));
            ModelLocation modelLocation = new ModelLocation(getApplicationContext());
            mTvCurrentLocation.setText(Util.getAddress(getApplicationContext(),mLastLocation));
        }
    }

    private void requestLocationPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    requestLocationPermissions();
                }
                break;
            default:
                break;
        }
    }

    private boolean isPlayServicesAvailable() {
        return GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
                == ConnectionResult.SUCCESS;
    }

    private boolean isGpsOn() {
        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void setUpLocationClientIfNeeded() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
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

    protected void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (lastLocation != null) {
            mLastLocation = lastLocation;
            updateUi();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, String.format(Locale.getDefault(), "onLocationChanged : %f, %f",
                location.getLatitude(), location.getLongitude()));
        mLastLocation = location;
        if (mIsAutoUpdateLocation) {
            updateUi();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onDestroy() {
        if (mGoogleApiClient != null
                && mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
            mGoogleApiClient.disconnect();
            mGoogleApiClient = null;
        }
        Log.d(TAG, "onDestroy LocationService");
        super.onDestroy();
    }

}
