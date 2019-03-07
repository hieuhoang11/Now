package com.example.hieuhoang.now.View.Main.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.hieuhoang.now.Model.Login.ModelLogin;
import com.example.hieuhoang.now.Model.ObjectClass.Account;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.View.LoginRegister.LoginRegisterActivity;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Hieu Hoang on 13/02/2019.
 */

public class FragmentAccount extends Fragment implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    Button btnLoginAccount, btnLogoutAccount;
    CircleImageView btnImgAccount;
    private final int REQUEST_CODE_LOGIN = 123;
    AccessToken accessToken;
    GoogleApiClient googleApiClient;
    GoogleSignInResult googleSignInResult;
    ModelLogin modelLogin;
    Account account;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_account, container, false);
        modelLogin = new ModelLogin(getContext());
        //Begin Mapping
        btnLoginAccount = (Button) view.findViewById(R.id.btnLoginAccount);
        btnLogoutAccount = (Button) view.findViewById(R.id.btnLogoutAccount);
        btnImgAccount = (CircleImageView) view.findViewById(R.id.btnImgAccount);

        //End Mapping

        btnLoginAccount.setOnClickListener(this);
        btnLogoutAccount.setOnClickListener(this);
        btnImgAccount.setOnClickListener(this);


        //begin google
        if (googleApiClient == null)
            googleApiClient = modelLogin.getGoogleApiClient(getActivity(), this);
        //end google

        Log.i("life", "onCreate");
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //Begin get AccessToken
        accessToken = modelLogin.getAccessTokenFacebook();
        //End get AccessToken
        googleSignInResult = modelLogin.getInformationGoogle(googleApiClient);
        //get information Login

        account = modelLogin.getAccountInformation(getContext());

        btnLogoutAccount.setVisibility(View.VISIBLE);
        if (account != null && !account.getFullName().equals("")) {
            getInformationAccount();
        } else if (accessToken != null) {
            getInformationFacebook();
        } else if (googleSignInResult != null) {
            // get username google
            getInformationGoogle();
            //end
        } else {
            btnLogoutAccount.setVisibility(View.INVISIBLE);
        }
        //end get information Login

        Log.i("life", "onStart");
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnImgAccount:
            case R.id.btnLoginAccount:
                if (isLogged() == false) {
                    Intent iLogin = new Intent(getContext(), LoginRegisterActivity.class);
                    startActivityForResult(iLogin, REQUEST_CODE_LOGIN);
                }
                break;
            case R.id.btnLogoutAccount:
                showAlertDialog();
                break;
        }
    }

    private boolean isLogged() {
        accessToken = modelLogin.getAccessTokenFacebook();
        googleSignInResult = modelLogin.getInformationGoogle(googleApiClient);
        account = modelLogin.getAccountInformation(getContext());
        if (account != null && !account.getFullName().equals("")) {
            Log.i("kiemtra", "account name: " + account.getFullName());
            return true;
        }
        if (accessToken != null || googleSignInResult != null)
            return true;
        return false;
    }

    private void logoutAccount() {
        if (account != null && !account.getFullName().equals("")) {
            modelLogin.logoutAccount(getContext());
        }
        if (accessToken != null) {
            LoginManager.getInstance().logOut();
        }
        if (googleSignInResult != null) {
            Auth.GoogleSignInApi.signOut(googleApiClient);
        }
        btnLogoutAccount.setVisibility(View.INVISIBLE);
        btnLoginAccount.setText(R.string.login_account);
        btnImgAccount.setImageResource(R.drawable.icon_add_user_32dp);
    }

    private void getInformationFacebook() {
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        try {
                            btnLoginAccount.setText(object.getString("name"));
                            loadImageFromInternet("https://graph.facebook.com/" + object.getString("id") + "/picture?type=normal");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "name,id");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void getInformationGoogle() {
        btnLoginAccount.setText(googleSignInResult.getSignInAccount().getDisplayName());
        if (googleSignInResult.getSignInAccount().getPhotoUrl() != null) {
            loadImageFromInternet(googleSignInResult.getSignInAccount().getPhotoUrl().toString());
            Log.i("img", googleSignInResult.getSignInAccount().getPhotoUrl().toString());
        }

    }

    private void getInformationAccount() {
        btnLoginAccount.setText(account.getFullName());
        btnImgAccount.setImageResource(R.drawable.icon_user_logged_32dp);
    }
    private void loadImageFromInternet(String path) {
        Picasso.with(getContext())
                .load(path)//"https://graph.facebook.com/100010904478016/picture?type=normal")
                .placeholder(R.drawable.icon_user_logged_32dp)
                .into(btnImgAccount);
    }

    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setMessage("Bạn có muốn đăng xuất không?");
        builder.setCancelable(false);
        builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                logoutAccount();
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("life", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("life", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("life", "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("life", "onDestroy");
    }

}