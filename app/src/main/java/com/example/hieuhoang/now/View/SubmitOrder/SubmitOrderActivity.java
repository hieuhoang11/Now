package com.example.hieuhoang.now.View.SubmitOrder;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hieuhoang.now.Adapter.rvSubmitOrderAdapter;
import com.example.hieuhoang.now.Common.Common;
import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.ObjectClass.Account;
import com.example.hieuhoang.now.Model.ObjectClass.Order;
import com.example.hieuhoang.now.Model.ObjectClass.OrderDetail;
import com.example.hieuhoang.now.Presenter.SubmitOrder.IPresenterSubmitOrder;
import com.example.hieuhoang.now.Presenter.SubmitOrder.PresenterLogicSubmitOrder;
import com.example.hieuhoang.now.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SubmitOrderActivity extends AppCompatActivity implements ViewSubmitOrder, OnMapReadyCallback, View.OnClickListener {
    private final String TAG = "kiemtra";
    private TextView tvStoreName, tvQuantityItem, tvTotalMoney, tvCustomer, tvLocation, tvNote;
    private RecyclerView rvSubmit;
    private Button btnSubmit;
    private GoogleMap map;
    private IPresenterSubmitOrder presenterLogicSubmitOrder;
    private rvSubmitOrderAdapter adapter;
    private List<OrderDetail> mDetails;
    private String idOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_order);

        Mapping();

        Intent intent = getIntent();
        idOrder = intent.getStringExtra(AppConstant.ID_ORDER);

        adapter = new rvSubmitOrderAdapter(new ArrayList<OrderDetail>(), getApplicationContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rvSubmit.setLayoutManager(layoutManager);
        rvSubmit.setAdapter(adapter);

        presenterLogicSubmitOrder = new PresenterLogicSubmitOrder(this, getApplicationContext(), this);
        presenterLogicSubmitOrder.getOrder(idOrder);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void Mapping() {
        tvStoreName = findViewById(R.id.tvStoreName);
        tvQuantityItem = findViewById(R.id.tvQuantityItem);
        tvTotalMoney = findViewById(R.id.tvTotalMoney);
        tvCustomer = findViewById(R.id.tvCustomer);
        tvLocation = findViewById(R.id.tvLocation);
        rvSubmit = findViewById(R.id.rvSubmit);
        btnSubmit = findViewById(R.id.btnSubmit);
        tvNote = findViewById(R.id.tvNote);
        tvNote.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
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

    @Override
    public void loadOrderDetail(List<OrderDetail> mDetailList) {
        adapter.setData(mDetailList);
        adapter.notifyDataSetChanged();
        this.mDetails = mDetailList;
    }

    @Override
    public void noEnoughQuantity(Map<String, Integer> map) {
        Toast.makeText(SubmitOrderActivity.this, getResources().getString(R.string.msg_out_of_stock), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSubmitSuccess() {
        Toast.makeText(SubmitOrderActivity.this, getResources().getString(R.string.submit_order_success), Toast.LENGTH_SHORT).show();
        this.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                submitOrder();
                break;
            case R.id.tvNote:
                showDialogEditNote();
                break;
        }
    }

    private void submitOrder() {
        Order order = new Order();
        String note = tvNote.getText().toString();
        String address = "11 Cổ nhuế Hà nội";
        order.setIdOrder(this.idOrder);
        order.setNote(note);
        order.setCustomerAddress(address);
        presenterLogicSubmitOrder.submitOrder(order, this.mDetails);
    }

    private void showDialogEditNote() {
        final Dialog dialog = new Dialog(SubmitOrderActivity.this);
        View view = LayoutInflater.from(SubmitOrderActivity.this).inflate(R.layout.custom_dialog_edit_note, null);
        final TextView tvContentDialog = view.findViewById(R.id.tvContentDialog);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        tvTitle.setText(getResources().getString(R.string.add_note));
        Button btnYes = view.findViewById(R.id.btnYes);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        btnYes.setText(getResources().getString(R.string.agree));
        btnCancel.setText(getResources().getString(R.string.cancel));
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                String text = tvContentDialog.getText().toString().trim();
                if (!text.equals("")) tvNote.setText(text);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.show();
    }
}
