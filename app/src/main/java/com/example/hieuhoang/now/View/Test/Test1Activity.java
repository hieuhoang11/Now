package com.example.hieuhoang.now.View.Test;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.hieuhoang.now.Model.Location.ModelLocation;
import com.example.hieuhoang.now.R;


public class Test1Activity extends AppCompatActivity {
    private String TAG = "kiemtra" ;
    private static final int REQUEST_LOCATION_PERMISSION = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        requestLocationPermissions();
        ModelLocation modelLocation = new ModelLocation(getApplicationContext()) ;
//        Log.i(TAG, "vi tri: " + modelLocation.getAddress());
//        Common.mLocation = modelLocation.getLastLocation();
//        Log.i(TAG, "vi tri: " + modelLocation.getAddress(Common.mLocation));
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

}
