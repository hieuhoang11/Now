package com.example.hieuhoang.now.View.LoginRegister;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.hieuhoang.now.Presenter.Login.IPresenterLogin;
import com.example.hieuhoang.now.Presenter.Login.PresenterLogicLogin;
import com.example.hieuhoang.now.R;


public class FragmentLogin extends Fragment implements ViewLogin ,View.OnClickListener{
    private ImageButton btnBackLogin;
    private TextView txtRegister;
    private EditText edEmailLogin ,edPasswordLogin;
    private Button  btnLogin;
    private IPresenterLogin presenterLogicLogin ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_fragment_login, container, false);

        presenterLogicLogin = new PresenterLogicLogin(this,getActivity().getApplicationContext());
        btnLogin = (Button) view.findViewById(R.id.btnLogin);
        btnBackLogin = (ImageButton) view.findViewById(R.id.btnBackLoginRegister);
        txtRegister = (TextView) view.findViewById(R.id.txtRegister);
        edEmailLogin= (EditText) view.findViewById(R.id.edEmailLogin);
        edPasswordLogin = (EditText) view.findViewById(R.id.edPasswordLogin);

        btnLogin.setOnClickListener(this);
        btnBackLogin.setOnClickListener(this);
        txtRegister.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                String email = edEmailLogin.getText().toString().trim();
                String password = edPasswordLogin.getText().toString().trim();
                presenterLogicLogin.loginAccount(email,password);
                Log.i("kiemtra", "đăng nhập ");
                break;
            case R.id.btnBackLoginRegister:
                getActivity().finish();
                break;
            case R.id.txtRegister:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager() ;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.container_login_register, new FragmentRegister(), null);
                fragmentTransaction.commit();
                break;
        }
    }

    @Override
    public void onLoginSuccess() {
        getActivity().finish();
    }

    @Override
    public void onLoginFail() {

    }
}