package com.example.hieuhoang.now.View.SubmitOrder;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hieuhoang.now.Adapter.rvOrderDetailAdapter;
import com.example.hieuhoang.now.Util.Util;
import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.ObjectClass.Account;
import com.example.hieuhoang.now.Model.ObjectClass.Order;
import com.example.hieuhoang.now.Model.ObjectClass.OrderDetail;
import com.example.hieuhoang.now.Presenter.SubmitOrder.IPresenterSubmitOrder;
import com.example.hieuhoang.now.Presenter.SubmitOrder.PresenterLogicSubmitOrder;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.View.SubmitOrder.EditInfo.EditInfoActivity;
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
    private TextView tvStoreName, tvQuantityItem, tvTotalMoney, tvCusInfo, tvLocation, tvNote;
    private RecyclerView rvOrderDetail;
    private Button btnSubmit;
    private GoogleMap map;
    private IPresenterSubmitOrder presenterLogicSubmitOrder;
    private rvOrderDetailAdapter adapter;
    private List<OrderDetail> mDetails;
    private Order order;
    private Account customer;
    private final int EDIT_INFO_CODE = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_order);

        Mapping();

        Intent intent = getIntent();
        String idOrder = intent.getStringExtra(AppConstant.ID_ORDER);

        adapter = new rvOrderDetailAdapter(new ArrayList<OrderDetail>(), getApplicationContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rvOrderDetail.setLayoutManager(layoutManager);
        rvOrderDetail.setAdapter(adapter);

        presenterLogicSubmitOrder = new PresenterLogicSubmitOrder(this, getApplicationContext(), this);
        presenterLogicSubmitOrder.getOrder(idOrder);

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
        LatLng location = Util.getCoordinates(SubmitOrderActivity.this, address);
        if (location == null) return;
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
        tvTotalMoney.setText(Util.formatNumber(order.getTotalMoney()));
        this.order = order;
    }

    @Override
    public void setCustomerInfo(Account account) {
        String text = account.getFullName();
        if (!account.getPhoneNumber().equals(""))
            text += " - " + account.getPhoneNumber();
        tvCusInfo.setText(text);
        this.customer = account;
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
        adapter.setMap (map) ;
        adapter.notifyDataSetChanged();
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
                if (!checkInfoCus()) {
                    Toast.makeText(SubmitOrderActivity.this, getResources().getString(R.string.please_complete_all_info), Toast.LENGTH_SHORT).show();
                    startEditActivity();
                    break;
                }
                submitOrder();
                break;
            case R.id.tvNote:
                showDialogEditNote();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_INFO_CODE && resultCode == RESULT_OK && data != null) {
            Bundle bundle = data.getBundleExtra(AppConstant.DATA);
            String phone = bundle.getString(AppConstant.PHONE);
            String fullName = bundle.getString(AppConstant.FULL_NAME) ;
            String cus = fullName + " - " + phone ;
            tvCusInfo.setText(cus);
            tvLocation.setText(bundle.getString(AppConstant.ADDRESS));
            customer.setPhoneNumber(phone);
            customer.setFullName(fullName);
        }
    }

    private void submitOrder() {
        String note = tvNote.getText().toString();
        note = note.equals(getResources().getString(R.string.note)) ? "" : note;
        order.setNote(note);
        order.setCustomerAddress(tvLocation.getText().toString());
        presenterLogicSubmitOrder.submitOrder(order, this.mDetails);
    }

    private void startEditActivity() {
        Intent iEdit = new Intent(SubmitOrderActivity.this, EditInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.ID_ACCOUNT, customer.getIdAccount());
        bundle.putString(AppConstant.FULL_NAME, customer.getFullName());
        bundle.putString(AppConstant.ADDRESS, tvLocation.getText().toString());
        bundle.putString(AppConstant.PHONE, customer.getPhoneNumber());
        iEdit.putExtra(AppConstant.DATA, bundle);
        startActivityForResult(iEdit, EDIT_INFO_CODE);
    }

    private boolean checkInfoCus() {
        if (this.customer.getPhoneNumber().equals(""))
            return false;
        if (this.customer.getFullName().equals(""))
            return false;
        return !(this.tvLocation.getText().toString().equals(""));
    }

    private void showDialogEditNote() {
        final Dialog dialog = new Dialog(SubmitOrderActivity.this);
        View view = LayoutInflater.from(SubmitOrderActivity.this).inflate(R.layout.custom_dialog_edit_note, null);
        final EditText edtNote = view.findViewById(R.id.edtNote);
        String text = tvNote.getText().toString();
        text = text.equals(getResources().getString(R.string.note)) ? "" : text;
        edtNote.setText(text);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        tvTitle.setText(getResources().getString(R.string.add_note));
        Button btnYes = view.findViewById(R.id.btnYes);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                String text = edtNote.getText().toString().trim();
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
