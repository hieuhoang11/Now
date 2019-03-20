package com.example.hieuhoang.now.View.SubmitOrder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.hieuhoang.now.Common.Common;
import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.ObjectClass.Account;
import com.example.hieuhoang.now.Model.ObjectClass.Order;
import com.example.hieuhoang.now.Presenter.SubmitOrder.IPresenterSubmitOrder;
import com.example.hieuhoang.now.Presenter.SubmitOrder.PresenterLogicSubmitOrder;
import com.example.hieuhoang.now.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class SubmitOrderActivity extends AppCompatActivity implements ViewSubmitOrder, OnMapReadyCallback {
    private final String TAG = "kiemtra";
    private TextView tvStoreName, tvQuantityItem, tvTotalMoney, tvCustomer,tvLocation;
    private GoogleMap map;
    private IPresenterSubmitOrder presenterLogicSubmitOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_order);

        Mapping();

        Intent intent = getIntent();
        String idOrder = intent.getStringExtra(AppConstant.ID_ORDER);

        presenterLogicSubmitOrder = new PresenterLogicSubmitOrder(this, getApplicationContext(), this);
        presenterLogicSubmitOrder.getOrder(idOrder);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void Mapping() {
        tvStoreName = findViewById(R.id.tvStoreName);
        tvQuantityItem = findViewById(R.id.tvQuantityItem);
        tvTotalMoney = findViewById(R.id.tvTotalMoney);
        tvCustomer = findViewById(R.id.tvCustomer);
        tvLocation=findViewById(R.id.tvLocation) ;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        presenterLogicSubmitOrder.getLocation();
    }

    @Override
    public void addMarker(String address) {
        tvLocation.setText(address);
        if (map == null) return;
        LatLng location = Common.getCoordinates(SubmitOrderActivity.this, address);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17));
        map.addMarker(new MarkerOptions().position(location));
    }

    @Override
    public void setStoreName(String storeName) {
        tvStoreName.setText(storeName);
    }

    @Override
    public void setInfoOrder(Order order) {
        tvQuantityItem.setText(String.valueOf(order.getQuantityProduct()));
        tvTotalMoney.setText(Common.formatNumber(order.getTotalMoney()));
    }

    @Override
    public void setCustomerName(Account account) {
        String text = account.getFullName();
        if (account.getPhoneNumber().equals(""))
            text += " - " + account.getPhoneNumber();
        tvCustomer.setText(text);
    }
}
