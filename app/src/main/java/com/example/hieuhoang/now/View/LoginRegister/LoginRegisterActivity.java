package com.example.hieuhoang.now.View.LoginRegister;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.LoginRegister.ModelLogin;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.View.LoginRegister.Login.FragmentLogin;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Arrays;

public class LoginRegisterActivity extends AppCompatActivity implements View.OnClickListener , GoogleApiClient.OnConnectionFailedListener {
    private Button btnLoginFacebook, btnLoginGoogle ;

    private CallbackManager callbackManager;
    private GoogleApiClient mGoogleApiClient;
    private final int SIGN_IN_GOOGLE_PLUS = 123;

    private ModelLogin modelLogin;
    private ProgressDialog progressDialog;


    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        modelLogin = new ModelLogin();
        mGoogleApiClient = modelLogin.getGoogleApiClient(LoginRegisterActivity.this, this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                finish();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        setContentView(R.layout.activity_login_register);

        btnLoginFacebook = (Button) findViewById(R.id.btnLoginFacebook);
        btnLoginGoogle = (Button) findViewById(R.id.btnLoginGoogle);

        btnLoginFacebook.setOnClickListener(this);
        btnLoginGoogle.setOnClickListener(this);


        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_login_register, new FragmentLogin(), null);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLoginFacebook:
                LoginManager.getInstance().logInWithReadPermissions(LoginRegisterActivity.this, Arrays.asList(AppConstant.PERMISSION_FACEBOOK));
                break;
            case R.id.btnLoginGoogle:
                Intent iGoogle = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(iGoogle, SIGN_IN_GOOGLE_PLUS);
                //showProcessDialog();
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_GOOGLE_PLUS) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                finish();
            }
        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


}
