package com.example.hieuhoang.now.Model.LoginRegister;

import android.content.Context;
import android.util.Log;

import com.example.hieuhoang.now.Util.Util;
import com.example.hieuhoang.now.ConnectInternet.DownloadJSON;
import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.ObjectClass.Account;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ModelRegister {

    private ModelLogin modelLogin;

    public ModelRegister(Context context) {
        modelLogin = new ModelLogin(context);
    }

    public boolean checkEmailExists(String email) {
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.CHECK_EMAIL_EXISTS);

        HashMap<String, String> hsEmail = new HashMap<>();
        hsEmail.put(AppConstant.EMAIL, email);

        attrs.add(hsFunction);
        attrs.add(hsEmail);

        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();
        try {
            return Util.parseBooleanJson(downloadJSON.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean RegisterAccount(Account account) {
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.REGISTER_ACCOUNT);

        HashMap<String, String> hsFullName = new HashMap<>();
        hsFullName.put(AppConstant.FULL_NAME, account.getFullName());

        HashMap<String, String> hsEmail = new HashMap<>();
        hsEmail.put(AppConstant.EMAIL, account.getEmail());

        HashMap<String, String> hsPassword = new HashMap<>();
        hsPassword.put(AppConstant.PASSWORD, Util.getMD5(account.getPassword()));

        HashMap<String, String> hsAccount_type = new HashMap<>();
        hsAccount_type.put(AppConstant.ACCOUNT_TYPE, String.valueOf(account.getAccountType()));

        attrs.add(hsFunction);
        attrs.add(hsFullName);
        attrs.add(hsEmail);
        attrs.add(hsPassword);
        attrs.add(hsAccount_type);

        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();
        try {
            String dataJSON = downloadJSON.get();
            JSONObject jsonObject = new JSONObject(dataJSON);

            String id = jsonObject.getString(AppConstant.ID_ACCOUNT);
            account.setIdAccount(id);
            modelLogin.setCacheLogin(account);
            return true;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean registerGoogle (String email,String name) {
        return false;
    }
}
