package com.example.hieuhoang.now.View.OrderDetail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hieuhoang.now.Adapter.rvOrderDetailAdapter;
import com.example.hieuhoang.now.Common.Common;
import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.ObjectClass.Account;
import com.example.hieuhoang.now.Model.ObjectClass.Order;
import com.example.hieuhoang.now.Model.ObjectClass.OrderDetail;
import com.example.hieuhoang.now.Presenter.OrderDetail.IPresenterOrderDetail;
import com.example.hieuhoang.now.Presenter.OrderDetail.PresenterLogicOrderDetail;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.View.SubmitOrder.ViewSubmitOrder;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDetailActivity extends AppCompatActivity implements ViewOrderDetail, OnMapReadyCallback, View.OnClickListener{
    private TextView tvStoreName, tvQuantityItem, tvTotalMoney, tvCusInfo, tvLocation, tvNote;
    private RecyclerView rvOrderDetail;
    private Button btnOk;
    private rvOrderDetailAdapter adapter;
    private IPresenterOrderDetail presenterLogicOrderDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        Mapping() ;

        Intent intent = getIntent();
        String idOrder = intent.getStringExtra(AppConstant.ID_ORDER);

        adapter = new rvOrderDetailAdapter(new ArrayList<OrderDetail>(), getApplicationContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rvOrderDetail.setLayoutManager(layoutManager);
        rvOrderDetail.setAdapter(adapter);

        presenterLogicOrderDetail = new PresenterLogicOrderDetail(getApplicationContext(),this) ;
        presenterLogicOrderDetail.getOrder(idOrder);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void Mapping() {
        tvStoreName = findViewById(R.id.tvStoreName);
        tvQuantityItem = findViewById(R.id.tvQuantityItem);
        tvTotalMoney = findViewById(R.id.tvTotalMoney);
        tvCusInfo = findViewById(R.id.tvCusInfo);
        tvLocation = findViewById(R.id.tvLocation);
        rvOrderDetail = findViewById(R.id.rvOrderDetail);
        btnOk = findViewById(R.id.btnOk);
        tvNote = findViewById(R.id.tvNote);
        btnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void setStoreName(String storeName) {
        tvStoreName.setText(storeName);
    }

    @Override
    public void setInfoOrder(Order order) {
        tvQuantityItem.setText(String.valueOf(order.getQuantityProduct()));
        tvTotalMoney.setText(Common.formatNumber(order.getTotalMoney()));
        tvLocation.setText(order.getCustomerAddress());
        String note = getResources().getString(R.string.note) ;
        tvNote.setText(order.getNote().equals("") ? note : order.getNote());
    }

    @Override
    public void setCustomerInfo(Account account) {
        String text = account.getFullName();
        text += " - " + account.getPhoneNumber();
        tvCusInfo.setText(text);
    }

    @Override
    public void loadOrderDetail(List<OrderDetail> mDetailList) {
        adapter.setData(mDetailList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
