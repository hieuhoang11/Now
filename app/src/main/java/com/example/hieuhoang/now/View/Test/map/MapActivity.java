package com.example.hieuhoang.now.View.Test.map;


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
import android.location.LocationListener;
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
        GoogleApiClient.OnConnectionFailedListener {
    TextView longitude, latitude, loca, longitude2, latitude2, kc;
    EditText vitri2;
    Button getLocation;
    private Location location;
    private GoogleApiClient gac;
    private ModelLocation modelLocation;
    String TAG = "kiemtra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        longitude = (TextView) findViewById(R.id.longitude);
        latitude = (TextView) findViewById(R.id.latitude);
        loca = (TextView) findViewById(R.id.loc);
        getLocation = findViewById(R.id.getLocation);
        longitude2 = findViewById(R.id.longitude2);
        latitude2 = findViewById(R.id.latitude2);
        kc = findViewById(R.id.kc);
        vitri2 = findViewById(R.id.vitri);
        //modelLocation = new ModelLocation(this,getApplicationContext()) ;
        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String add = modelLocation.getLocation() ;
//                if(add != null) {
//                    loca.setText(add);
//                }
                getLocation();
                // calculateDistance();
            }
        });
        // Trước tiên chúng ta cần phải kiểm tra play services
        if (checkPlayServices()) {
            // Building the GoogleApi client
            buildGoogleApiClient();
        }
    }

    private void calculateDistance() {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocationName(
                    this.vitri2.getText().toString(), 5);
            if (addresses.size() > 0) {
                float[] re = new float[1];

                // String kc = getDistance(location.getLatitude(),location.getLongitude(),addresses.get(0).getLatitude(),addresses.get(0).getLongitude());
                Location.distanceBetween(location.getLatitude(), location.getLongitude(), addresses.get(0).getLatitude(), addresses.get(0).getLongitude(), re);
                this.latitude2.setText("latitude 2 : " + addresses.get(0).getLatitude());
                this.longitude2.setText("longitude 2 : " + addresses.get(0).getLongitude());
                this.kc.setText("khoang cach :" + re[0]);
//  p = new GeoPoint(
//                        (int) (addresses.get(0).getLatitude() * 1E6),
//                        (int) (addresses.get(0).getLongitude() * 1E6));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Kiểm tra quyền hạn
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
        } else {
            location = LocationServices.FusedLocationApi.getLastLocation(gac);

            if (location != null) {

                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                // Hiển thị
                this.latitude.setText("latitude :" + latitude);
                this.longitude.setText("longitude :" + longitude);

                Geocoder geoCoder = new Geocoder(
                        this, Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = geoCoder.getFromLocation(
                            location.getLatitude(),
                            location.getLongitude(), 1);
                    String add = "";
                    if (addresses.size() > 0) {
                        Address address = addresses.get(0);
                        String addressText = "";
                        addressText += address.getAddressLine(0);
                        this.loca.setText("vi tri :" + addressText);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i("kiemtra", "getLocation: fail");
                }
                return;

            }
            this.latitude.setText("(Không thể hiển thị vị trí. " +
                    "Bạn đã kích hoạt location trên thiết bị chưa?)");

        }
    }

    protected synchronized void buildGoogleApiClient() {
        if (gac == null) {
            gac = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API).build();
            Log.i(TAG, "buildGoogleApiClient: ");
        }
    }

    /**
     * Phương thức kiểm chứng google play services trên thiết bị
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, 1000).show();
            } else {
                Toast.makeText(this, "Thiết bị này không hỗ trợ.", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // Đã kết nối với google api, lấy vị trí
        Log.i(TAG, "onConnected: ");
        getLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "onConnectionSuspended: ");
        //gac.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "onConnectionFailed: ");
        Toast.makeText(this, "Lỗi kết nối: " + connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    protected void onStart() {
        gac.connect();
        super.onStart();
    }

    protected void onStop() {
        //gac.disconnect();
        super.onStop();
    }


}
