package com.example.hieuhoang.now.View.EditInfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.ObjectClass.Account;
import com.example.hieuhoang.now.Presenter.SubmitOrder.EditInfo.PresenterLogicEditInfo;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.Util.Util;

public class EditInfoActivity extends AppCompatActivity implements ViewEditInfo, View.OnClickListener {
    private EditText tvName, tvPhone, tvAddress;
    private Button btnDone;
    private ImageButton btnBack;
    private PresenterLogicEditInfo presenterLogicEditInfo;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        Mapping();

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(AppConstant.DATA);
        String fullName = "", phoneNumber = "", address = "", idCus = "";
        if (bundle != null) {
            idCus = bundle.getString(AppConstant.ID_ACCOUNT);
            fullName = bundle.getString(AppConstant.FULL_NAME);
            phoneNumber = bundle.getString(AppConstant.PHONE);
            address = bundle.getString(AppConstant.ADDRESS);
            account = new Account();
            account.setIdAccount(idCus);
            account.setFullName(fullName);
            account.setPhoneNumber(phoneNumber);
        }
        tvName.setText(fullName);
        tvPhone.setText(phoneNumber);
        tvAddress.setText(address);

        addOnClick();

        presenterLogicEditInfo = new PresenterLogicEditInfo(this);
    }

    private void Mapping() {
        tvName = findViewById(R.id.tvName);
        tvPhone = findViewById(R.id.tvPhone);
        tvAddress = findViewById(R.id.tvAddress);
        btnDone = findViewById(R.id.btnDone);
        btnBack = findViewById(R.id.btnBack);
    }

    private void addOnClick() {
        btnDone.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDone:
                if (!checkInfo()) {
                    Toast.makeText(EditInfoActivity.this, getResources().getString(R.string.please_complete_all_info), Toast.LENGTH_SHORT).show();
                    break;
                }
                String phoneNumber = tvPhone.getText().toString();
                String fullName = Util.standardizeString(tvName.getText().toString());
                if (account.getPhoneNumber().equals(phoneNumber) && account.getFullName().equals(fullName)) {
                    onUpdateCusInfoSuccess();
                    break;
                }
                account.setPhoneNumber(phoneNumber);
                account.setFullName(fullName);
                presenterLogicEditInfo.updateCusInfo(account);
                break;
            case R.id.btnBack:
                finish();
                break;
        }
    }

    private boolean checkInfo() {
        if (tvAddress.getText().toString().equals("")) {
            return false;
        }
        if (tvPhone.getText().toString().equals("")) {
            return false;
        }
        if (tvName.getText().toString().equals("")) {
            return false;
        }
        return true;
    }

    @Override
    public void onUpdateCusInfoSuccess() {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.FULL_NAME, Util.standardizeString(tvName.getText().toString()));
        bundle.putString(AppConstant.PHONE, tvPhone.getText().toString());
        bundle.putString(AppConstant.ADDRESS, Util.standardizeString(tvAddress.getText().toString()));
        Intent iResult = new Intent();
        iResult.putExtra(AppConstant.DATA, bundle);
        setResult(RESULT_OK, iResult);
        finish();
    }
}
