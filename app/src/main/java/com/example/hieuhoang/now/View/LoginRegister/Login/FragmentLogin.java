package com.example.hieuhoang.now.View.LoginRegister.Login;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hieuhoang.now.Presenter.LoginRegister.Login.IPresenterLogin;
import com.example.hieuhoang.now.Presenter.LoginRegister.Login.PresenterLogicLogin;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.View.LoginRegister.Register.FragmentRegister;


public class FragmentLogin extends Fragment implements ViewLogin, View.OnClickListener {
    private ImageButton btnBackLogin;
    private TextView txtRegister;
    private EditText edEmailLogin, edPasswordLogin;
    private Button btnLogin;
    TextInputLayout input_edtEmail, input_edtPassword;
    private IPresenterLogin presenterLogicLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_fragment_login, container, false);

        presenterLogicLogin = new PresenterLogicLogin(this, getContext());
        btnLogin = view.findViewById(R.id.btnLogin);
        btnBackLogin = view.findViewById(R.id.btnBackLoginRegister);
        txtRegister = view.findViewById(R.id.txtRegister);
        edEmailLogin = view.findViewById(R.id.edEmailLogin);
        edPasswordLogin = view.findViewById(R.id.edPasswordLogin);
        input_edtEmail = view.findViewById(R.id.input_edtEmail);
        input_edtPassword = view.findViewById(R.id.input_edtPassword);
        btnLogin.setOnClickListener(this);
        btnBackLogin.setOnClickListener(this);
        txtRegister.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                if (!checkEmail() | !checkPassword()) break;
                String email = edEmailLogin.getText().toString().trim();
                String password = edPasswordLogin.getText().toString().trim();
                presenterLogicLogin.loginAccount(email, password);
                break;
            case R.id.btnBackLoginRegister:
                getActivity().finish();
                break;
            case R.id.txtRegister:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.container_login_register, new FragmentRegister(), null);
                fragmentTransaction.commit();
                break;
        }
    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(getContext(), R.string.login_success, Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }

    @Override
    public void onLoginFail() {
        Toast.makeText(getContext(), R.string.login_fail, Toast.LENGTH_SHORT).show();
    }

    private boolean checkEmail() {
        String email = edEmailLogin.getText().toString().trim();
        if (email.equals("")) {
            input_edtEmail.setErrorEnabled(true);
            input_edtEmail.setError("Bạn cần nhập email!");
            return false;
        }
        input_edtEmail.setErrorEnabled(false);
        input_edtEmail.setError("");
        return true;
    }

    private boolean checkPassword() {
        String password = edPasswordLogin.getText().toString().trim();
        if (password.equals("")) {
            input_edtPassword.setErrorEnabled(true);
            input_edtPassword.setError("Bạn cần nhập mật khẩu!");
            return false;
        }
        input_edtPassword.setErrorEnabled(false);
        input_edtPassword.setError("");
        return true;
    }
}