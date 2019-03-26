package com.example.hieuhoang.now.View.SubmitOrder.EditInfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.CustomView.ClearEditText;
import com.example.hieuhoang.now.Model.ObjectClass.Account;
import com.example.hieuhoang.now.Presenter.SubmitOrder.EditInfo.PresenterLogicEditInfo;
import com.example.hieuhoang.now.R;

public class EditInfoActivity extends AppCompatActivity implements ViewEditInfo, View.OnClickListener {
    private EditText tvName, tvPhone, tvAddress;
    private Button btnDone;
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
    }

    private void addOnClick() {
        btnDone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDone:
                if(!checkInfo()) {
                    break;
                }
                account.setPhoneNumber(tvPhone.getText().toString());
                account.setFullName(tvName.getText().toString());
                presenterLogicEditInfo.updateCusInfo(account);
                break;
        }
    }

    private boolean checkInfo() {
        if(tvAddress.getText().toString().equals("")) {
            return false ;
        }
        if(tvPhone.getText().toString().equals("")) {
            return false ;
        }
        if(tvName.getText().toString().equals("")) {
            return false ;
        }
        return true ;
    }

    @Override
    public void onUpdateCusInfoSuccess() {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.FULL_NAME, tvName.getText().toString());
        bundle.putString(AppConstant.PHONE, tvPhone.getText().toString());
        bundle.putString(AppConstant.ADDRESS, tvAddress.getText().toString());
        Intent iResult = new Intent();
        iResult.putExtra(AppConstant.DATA, bundle);
        setResult(RESULT_OK, iResult);
        finish();
    }
}
