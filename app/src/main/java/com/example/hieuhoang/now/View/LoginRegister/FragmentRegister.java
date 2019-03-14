package com.example.hieuhoang.now.View.LoginRegister;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.ObjectClass.Account;
import com.example.hieuhoang.now.Presenter.LoginRegister.Register.PresenterLogicRegister;
import com.example.hieuhoang.now.R;


public class FragmentRegister extends Fragment implements ViewRegister , View.OnClickListener ,View.OnFocusChangeListener {
    private TextInputLayout input_edtFullName, input_edtEmail, input_edtPassword, input_edtRepeatPassword;
    private EditText edtFullName, edtEmail, edtPassword, edtRepeatPassword;
    private Button  btnRegisterAccount;

    private PresenterLogicRegister presenterLogicRegister;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_register, container, false);

        presenterLogicRegister = new PresenterLogicRegister(this,getContext());

        btnRegisterAccount = (Button) view.findViewById(R.id.btnRegisterAccount);
        input_edtFullName = (TextInputLayout) view.findViewById(R.id.input_edtFullName);
        input_edtEmail = (TextInputLayout) view.findViewById(R.id.input_edtEmail);
        input_edtPassword = (TextInputLayout) view.findViewById(R.id.input_edtPassword);
        input_edtRepeatPassword = (TextInputLayout) view.findViewById(R.id.input_edtRepeatPassword);
        edtFullName = (EditText) view.findViewById(R.id.edtFullName);
        edtEmail = (EditText) view.findViewById(R.id.edtEmail);
        edtPassword = (EditText) view.findViewById(R.id.edtPassword);
        edtRepeatPassword = (EditText) view.findViewById(R.id.edtRepeatPassword);

        btnRegisterAccount.setOnClickListener(this);

        edtFullName.setOnFocusChangeListener(this);
        edtEmail.setOnFocusChangeListener(this);
        edtRepeatPassword.setOnFocusChangeListener(this);
        edtRepeatPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(input_edtRepeatPassword.getError() == null)
                    return;
                if( input_edtRepeatPassword.getError().equals(""))
                    return;
                if(s.toString().equals(edtPassword.getText().toString().trim())) {
                    input_edtRepeatPassword.setErrorEnabled(false);
                    input_edtRepeatPassword.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegisterAccount:
                registerAccount();
                break;
        }

    }

    private void registerAccount() {

        if(!checkFullName() | !checkEmail() | !checkPassword() | !checkRepeatPassword())
            return;
        if(input_edtPassword.isErrorEnabled()) return;



        Account account = new Account();
        account.setFullName(edtFullName.getText().toString());
        account.setEmail(edtEmail.getText().toString());
        account.setPassword(edtPassword.getText().toString());
        account.setAccountType(AppConstant.ACCOUNT_TYPE_CUSTOMER);
        presenterLogicRegister.RegisterAccount(account);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.edtFullName:
                if (!hasFocus) {
                    checkFullName();
                }
                break;
            case R.id.edtEmail:
                if (!hasFocus) {
                    checkEmail();
                }
                break;
            case R.id.edtRepeatPassword:
                if (!hasFocus) {
                    checkRepeatPassword();
                }
                break;
        }
    }

    private boolean checkFullName() {
        String fullName = edtFullName.getText().toString().trim();
        if (fullName.equals("")) {
            input_edtFullName.setErrorEnabled(true);
            input_edtFullName.setError("Bạn cần nhập họ tên!");
            return false;
        }
        input_edtFullName.setErrorEnabled(false);
        input_edtFullName.setError("");
        return true;

    }

    private boolean checkEmail() {
        String email = edtEmail.getText().toString().trim();
        if (email.equals("")) {
            input_edtEmail.setErrorEnabled(true);
            input_edtEmail.setError("Bạn cần nhập email!");
            return false;
        }
        Boolean checkEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        if (!checkEmail) {
            input_edtEmail.setErrorEnabled(true);
            input_edtEmail.setError("Sai định dạng email!");
            return false;
        }
        input_edtEmail.setErrorEnabled(false);
        input_edtEmail.setError("");
        return true;
    }

    private boolean checkPassword() {
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

    @Override
    public void onRegisterSuccess() {
        Toast.makeText(getContext(),R.string.register_success ,Toast.LENGTH_LONG).show();

        getActivity().finish();
    }

    @Override
    public void onRegisterFail() {
        Toast.makeText(getContext(),R.string.register_fail ,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onEmailExists() {
        input_edtEmail.setErrorEnabled(true);
        input_edtEmail.setError("địa chỉ email đã tồn tại");
    }

}