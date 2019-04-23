package com.example.hieuhoang.now.View.AccountInfo;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.ObjectClass.Account;
import com.example.hieuhoang.now.Presenter.AccountInfo.IPresenterAccountInfo;
import com.example.hieuhoang.now.Presenter.AccountInfo.PresenterLogicAccountInfo;
import com.example.hieuhoang.now.R;

public class AccountInfoActivity extends AppCompatActivity implements ViewAccountInfo, View.OnClickListener, TextWatcher {
    private Button btnChangePassword, btnAction,btnCancel;
    private TextInputLayout input_edtPassword, input_edtRepeatPassword, input_edtOldPassword;
    private EditText edtRepeatPassword, edtPassword, edtFullName, edtPhone, edtEmail, edtOldPassword;
    private ImageButton btnBack;
    private View viewChangePassword;
    private IPresenterAccountInfo presenterAccountInfo;
    private String idAccount;
    private boolean isChangeInfo = false;
    private boolean isChangePassword = false;
    private String fullName = "";
    private String phoneNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
        Mapping();
        Intent intent = getIntent();
        idAccount = intent.getStringExtra(AppConstant.ID_ACCOUNT);

        presenterAccountInfo = new PresenterLogicAccountInfo(getApplicationContext(), this);
        presenterAccountInfo.getAccountInfo(idAccount);
    }

    private void Mapping() {
        btnChangePassword = findViewById(R.id.btnChangePassword);
        edtOldPassword = findViewById(R.id.edtOldPassword);
        input_edtPassword = findViewById(R.id.input_edtPassword);
        edtPassword = findViewById(R.id.edtPassword);
        edtEmail = findViewById(R.id.edtEmail);
        edtFullName = findViewById(R.id.edtFullName);
        edtPhone = findViewById(R.id.edtPhone);
        edtRepeatPassword = findViewById(R.id.edtRepeatPassword);
        input_edtOldPassword = findViewById(R.id.input_edtOldPassword);
        input_edtRepeatPassword = findViewById(R.id.input_edtRepeatPassword);
        btnBack = findViewById(R.id.btnBack);
        viewChangePassword = findViewById(R.id.viewChangePassword);
        btnAction = findViewById(R.id.btnAction);
        btnCancel = findViewById(R.id.btnCancel) ;
        btnAction.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnChangePassword.setOnClickListener(this);
        edtFullName.addTextChangedListener(this);
        edtPhone.addTextChangedListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnChangePassword:
                displayChangePassword();
                break;
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnCancel:
                dismissChangePassword() ;
                break;
            case R.id.btnAction:
                if (isChangePassword) {
                    checkOldPassword();
                }
                else if (isChangeInfo) {
                    changeInfo();
                    isChangeInfo = false;
                }
                break;
        }
    }


    @Override
    public void displayAccountInfo(Account account) {
        this.fullName = account.getFullName();
        this.phoneNumber = account.getPhoneNumber();
        edtPhone.setText(this.phoneNumber);
        edtFullName.setText(this.fullName);
        edtEmail.setText(account.getEmail());
        btnAction.setVisibility(View.GONE);
        edtPhone.requestFocus();
        edtFullName.requestFocus();
    }

    @Override
    public void onChangePasswordSuccess() {
        onChangedPassword(getResources().getString(R.string.change_password_success));
    }

    @Override
    public void onChangePasswordFail() {
        onChangedPassword(getResources().getString(R.string.change_password_fail));
    }

    @Override
    public void oldPasswordIncorrect() {
        input_edtOldPassword.setErrorEnabled(true);
        input_edtOldPassword.setError(getResources().getString(R.string.current_password_incorrect));
    }

    @Override
    public void oldPasswordCorrect() {
        input_edtOldPassword.setErrorEnabled(false);
        input_edtOldPassword.setError("");
        if (checkNewPassword())
            if (checkRepeatPassword())
                dialogConfirm();

    }

    @Override
    public void onUpdateInfoSuccess() {
        Toast.makeText(AccountInfoActivity.this,getResources().getString(R.string.edit_account_info_success),Toast.LENGTH_SHORT).show();
        btnAction.setVisibility(View.GONE);
    }

    private void changeInfo() {
        Account account = new Account();
        account.setFullName(edtFullName.getText().toString().trim());
        account.setPhoneNumber(edtPhone.getText().toString().trim());
        account.setIdAccount(idAccount);
        presenterAccountInfo.changeInfo(account);
    }


    private void onChangedPassword(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        visibleChangePassword(View.GONE);
        edtFullName.setEnabled(true);
        edtPhone.setEnabled(true);
        dismissChangePassword();
        if (isChangeInfo) {
            btnAction.setText(getResources().getString(R.string.change_info));
            btnAction.setVisibility(View.VISIBLE);
            isChangInfo ();
        }
    }

    private void dialogConfirm() {
        final Dialog dialog = new Dialog(AccountInfoActivity.this);
        View view = LayoutInflater.from(AccountInfoActivity.this).inflate(R.layout.custom_dialog, null);
        TextView tvContentDialog = view.findViewById(R.id.tvContentDialog);
        Button btnYes = view.findViewById(R.id.btnYes);
        Button btnCancel = view.findViewById(R.id.btnCancel);
        tvContentDialog.setText(getResources().getString(R.string.do_you_want_to_change_password));
        btnYes.setText(getResources().getString(R.string.option_yes));
        btnCancel.setText(getResources().getString(R.string.cancel));
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                presenterAccountInfo.changePassword(idAccount, edtPassword.getText().toString().trim());
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

    private void displayChangePassword () {
        visibleChangePassword(View.VISIBLE);
        isChangePassword = true;
        edtFullName.setEnabled(false);
        edtPhone.setEnabled(false);
        btnCancel.setVisibility(View.VISIBLE);
        //edtOldPassword.setSelected(true);
    }

    private void dismissChangePassword () {
        visibleChangePassword(View.GONE);
        isChangePassword = false;
        edtPassword.setText("");
        edtRepeatPassword.setText("");
        edtFullName.setEnabled(true);
        edtPhone.setEnabled(true);
        btnCancel.setVisibility(View.GONE);
        input_edtOldPassword.setErrorEnabled(false);
        input_edtOldPassword.setError("");
        input_edtPassword.setErrorEnabled(false);
        input_edtPassword.setError("");
        input_edtRepeatPassword.setErrorEnabled(false);
        input_edtRepeatPassword.setError("");
        edtOldPassword.setText("");
    }

    private void visibleChangePassword(int visible) {
        viewChangePassword.setVisibility(visible);
        btnAction.setVisibility(visible);
        btnAction.setText(getResources().getString(R.string.change_password));
    }

    private void checkOldPassword() {
        String password = edtOldPassword.getText().toString().trim();
        if (password.equals("")) {
            input_edtOldPassword.setErrorEnabled(true);
            input_edtOldPassword.setError(getResources().getString(R.string.you_need_enter_current_password));
        } else {
            presenterAccountInfo.checkOldPassword(edtEmail.getText().toString(), edtOldPassword.getText().toString());
        }
    }

    private boolean checkNewPassword() {
        String password = edtPassword.getText().toString().trim();
        if (password.equals("")) {
            input_edtPassword.setErrorEnabled(true);
            input_edtPassword.setError(getResources().getString(R.string.you_need_enter_password));
            return false;
        }
        if (edtOldPassword.getText().toString().trim().equals(edtPassword.getText().toString().trim())) {
            input_edtPassword.setErrorEnabled(true);
            input_edtPassword.setError(getResources().getString(R.string.new_password_must_be_difference_current_password));
            return false;
        }
        input_edtPassword.setErrorEnabled(false);
        input_edtPassword.setError("");
        return true;
    }

    private boolean checkRepeatPassword() {
        String repeatPassword = edtRepeatPassword.getText().toString().trim();
        if (repeatPassword.equals("")) {
            input_edtRepeatPassword.setErrorEnabled(true);
            input_edtRepeatPassword.setError(getResources().getString(R.string.repeat_password));
            return false;
        }
        String password = edtPassword.getText().toString().trim();
        if (!repeatPassword.equals(password)) {
            input_edtRepeatPassword.setErrorEnabled(true);
            input_edtRepeatPassword.setError(getResources().getString(R.string.password_not_match));
            return false;
        }
        input_edtRepeatPassword.setErrorEnabled(false);
        input_edtRepeatPassword.setError("");
        return true;

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.i("kiemtra", "onTextChanged: ");
        isChangInfo ();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void isChangInfo () {
        isChangeInfo = true;
        btnAction.setVisibility(View.VISIBLE);
        btnAction.setText(getResources().getString(R.string.change_info));
        String fullName = edtFullName.getText().toString().trim();
        String phoneNumber = edtPhone.getText().toString().trim();
        if ((fullName.equals(this.fullName)
                && phoneNumber.equals(this.phoneNumber))
                || fullName.equals("") || phoneNumber.equals("")) {
            btnAction.setVisibility(View.GONE);
            isChangeInfo = false;
        }
    }

    private void dialogService() {
        View view = getLayoutInflater().inflate(R.layout.custom_dialog_change_password, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this);

        dialog.setContentView(view);
        dialog.show();
    }
}
