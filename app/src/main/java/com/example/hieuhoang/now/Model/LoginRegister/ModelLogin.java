package com.example.hieuhoang.now.Model.LoginRegister;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.hieuhoang.now.ConnectInternet.DownloadJSON;
import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.ObjectClass.Account;
import com.example.hieuhoang.now.Util.Util;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ModelLogin {
    private AccessToken accessToken;
    private AccessTokenTracker accessTokenTracker;
    private Context context;

    public ModelLogin(Context context) {
        this.context = context;
    }

    public boolean checkLogin(String email, String password) {
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();
        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.CHECK_LOGIN);
        HashMap<String, String> hsEmail = new HashMap<>();
        hsEmail.put(AppConstant.EMAIL, email);
        attrs.add(hsFunction);
        attrs.add(hsEmail);
        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();
        try {
            String dataJSON = downloadJSON.get();
            JSONObject jsonObject = new JSONObject(dataJSON);
            Log.i("kiemtra", "checkLogin: " + dataJSON);
            if(Util.getMD5(password).equals(jsonObject.getString(AppConstant.PASSWORD))){
                String fullName = jsonObject.getString(AppConstant.FULL_NAME);
                String id = jsonObject.getString(AppConstant.ID_ACCOUNT);
                Account account = new Account();
                account.setFullName(fullName);
                account.setIdAccount(id);
                setCacheLogin(account);
                return true ;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isLogged() {
        Account account = this.getAccount();
        return !(account.getIdAccount().equals(AppConstant.DEFAULT_ID_ACCOUNT));
    }

    public AccessToken getAccessTokenFacebook() {

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                accessToken = currentAccessToken;
            }
        };

        accessToken = AccessToken.getCurrentAccessToken();
        return accessToken;
    }

    public void destroyTokenTracker() {
        accessTokenTracker.stopTracking();
    }

    public GoogleApiClient getGoogleApiClient(Context context, GoogleApiClient.OnConnectionFailedListener failedListener) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(context)
                .enableAutoManage((AppCompatActivity) context, failedListener)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        return mGoogleApiClient;
    }

    public GoogleSignInResult getInformationGoogle(GoogleApiClient googleApiClient) {
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()) {
            return opr.get();
        }
        return null;
    }

    public void setCacheLogin(Account account) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstant.LOGIN_SHAREDPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppConstant.FULL_NAME, account.getFullName());
        editor.putString(AppConstant.ID_ACCOUNT, account.getIdAccount());
        editor.apply();
    }

    public Account getAccount() {

        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstant.LOGIN_SHAREDPREFERENCES, Context.MODE_PRIVATE);
        if (sharedPreferences == null) {
            return null;
        }
        Account account = new Account();
        String fullName = sharedPreferences.getString(AppConstant.FULL_NAME, "");
        String id = sharedPreferences.getString(AppConstant.ID_ACCOUNT, AppConstant.DEFAULT_ID_ACCOUNT);
        account.setFullName(fullName);
        account.setIdAccount(id);
        return account;
    }

    public void logoutAccount() {
        Account account = new Account();
        account.setFullName("");
        account.setIdAccount(AppConstant.DEFAULT_ID_ACCOUNT);
        setCacheLogin(account);
    }
}
