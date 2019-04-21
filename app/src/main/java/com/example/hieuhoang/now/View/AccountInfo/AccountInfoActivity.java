package com.example.hieuhoang.now.View.AccountInfo;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.ObjectClass.Account;
import com.example.hieuhoang.now.Presenter.AccountInfo.IPresenterAccountInfo;
import com.example.hieuhoang.now.Presenter.AccountInfo.PresenterLogicAccountInfo;
import com.example.hieuhoang.now.R;

public class AccountInfoActivity extends AppCompatActivity implements ViewAccountInfo, View.OnClickListener {
    private Button btnChangePassword, btnAction;
    private TextInputLayout input_edtPassword, input_edtRepeatPassword, input_edtOldPassword;
    private EditText edtRepeatPassword, edtPassword, edtFullName, edtPhone, edtEmail, edtOldPassword;
    private ImageButton btnBack;
    private View viewChangePassword;
    private IPresenterAccountInfo presenterAccountInfo;
    private String idAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
        Mapping();
        Intent intent = getIntent();
        idAccount = intent.getStringExtra(AppConstant.ID_ACCOUNT);

        presenterAccountInfo = new PresenterLogicAccountInfo(getApplicationContext(),this);
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
        btnAction.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnChangePassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnChangePassword:
                visibleChangePassword(View.VISIBLE);
                break;
            case R.id.btnAction:
                checkOldPassword() ;
                break;
        }
    }

    @Override
    public void displayAccountInfo(Account account) {
        edtPhone.setText(account.getPhoneNumber());
        edtFullName.setText(account.getFullName());
        edtEmail.setText(account.getEmail());
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
                presenterAccountInfo.changePassword(idAccount, edtPassword.getText().toString().trim());
    }

    private void onChangedPassword(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        visibleChangePassword(View.GONE);
    }

    private void visibleChangePassword(int visible) {
        viewChangePassword.setVisibility(visible);
        btnAction.setVisibility(visible);
    }

    private void checkOldPassword() {
        String password = edtOldPassword.getText().toString().trim();
        if (password.equals("")) {
            input_edtOldPassword.setErrorEnabled(true);
            input_edtOldPassword.setError("Bạn cần nhập mật khẩu!");
        } else {
            presenterAccountInfo.checkOldPassword(edtEmail.getText().toString(),edtOldPassword.getText().toString());
        }
    }

    private boolean checkNewPassword() {
        String password = edtPassword.getText().toString().trim();
        if (password.equals("")) {
            input_edtPassword.setErrorEnabled(true);
            input_edtPassword.setError("Bạn cần nhập mật khẩu!");
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
            input_edtRepeatPassword.setError("Nhập lại mật khẩu!");
            return false;
        }
        String password = edtPassword.getText().toString().trim();
        if (!repeatPassword.equals(password)) {
            input_edtRepeatPassword.setErrorEnabled(true);
            input_edtRepeatPassword.setError("Mật khẩu không trùng khớp!");
            return false;
        }
        input_edtRepeatPassword.setErrorEnabled(false);
        input_edtRepeatPassword.setError("");
        return true;

    }
}
