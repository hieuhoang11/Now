package com.example.hieuhoang.now.View.Main.Account;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.hieuhoang.now.Model.LoginRegister.ModelLogin;
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

        Mapping(view);

        //begin google
        if (googleApiClient == null)
            googleApiClient = modelLogin.getGoogleApiClient(getActivity(), this);
        //end google
        return view;
    }

    private void Mapping(View view) {
        //Begin Mapping
        btnLoginAccount = (Button) view.findViewById(R.id.btnLoginAccount);
        btnLogoutAccount = (Button) view.findViewById(R.id.btnLogoutAccount);
        btnImgAccount = (CircleImageView) view.findViewById(R.id.btnImgAccount);

        //End Mapping

        btnLoginAccount.setOnClickListener(this);
        btnLogoutAccount.setOnClickListener(this);
        btnImgAccount.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        //Begin get AccessToken
        accessToken = modelLogin.getAccessTokenFacebook();
        //End get AccessToken
        googleSignInResult = modelLogin.getInformationGoogle(googleApiClient);
        //get information Login

        account = modelLogin.getAccount();

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
                dialogConfirmLogout();
                break;
        }
    }

    private boolean isLogged() {
        accessToken = modelLogin.getAccessTokenFacebook();
        googleSignInResult = modelLogin.getInformationGoogle(googleApiClient);
        account = modelLogin.getAccount();
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
            modelLogin.logoutAccount();
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

    private void dialogConfirmLogout() {
        final Dialog dialog = new Dialog(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.custom_dialog, null);
        TextView tvContentDialog = view.findViewById(R.id.tvContentDialog);
        Button btnYes = view.findViewById(R.id.btnYes);
        Button btnCancel = view.findViewById(R.id.btnCancel);
        tvContentDialog.setText(getResources().getString(R.string.do_you_want_to_logout));
        btnYes.setText(getResources().getString(R.string.logout));
        btnCancel.setText(getResources().getString(R.string.cancel));
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                logoutAccount();
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


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}