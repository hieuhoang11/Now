package com.example.hieuhoang.now.View.SubmitOrder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.R;

public class SubmitOrderActivity extends AppCompatActivity {
private final String TAG = "kiemtra";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_order);
        Intent intent = getIntent() ;
        String idOrder = intent.getStringExtra(AppConstant.ID_ORDER) ;
        Log.i(TAG, "onCreate: id order" + idOrder);
    }
}
