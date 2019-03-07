package com.example.hieuhoang.now.Model.Register;

import android.content.Context;
import android.util.Log;

import com.example.hieuhoang.now.ConnectInternet.DownloadJSON;
import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.Login.ModelLogin;
import com.example.hieuhoang.now.Model.ObjectClass.Account;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Hieu Hoang on 19/02/2019.
 */

public class ModelRegister {

    ModelLogin modelLogin ;
    Context context;
    public ModelRegister(Context context) {
        this.context = context ;
        modelLogin = new ModelLogin(context);
    }

    public Boolean checkEmailExists (String email) {
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
            String dataJSON = downloadJSON.get();
            JSONObject jsonObject = new JSONObject(dataJSON);
            String result = jsonObject.getString("result");
            if (result.equals("false")) {
                return false;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Boolean RegisterAccount(Account account) {
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.REGISTER_ACCOUNT);

        HashMap<String, String> hsFullName = new HashMap<>();
        hsFullName.put(AppConstant.FULL_NAME, account.getFullName());

        HashMap<String, String> hsEmail = new HashMap<>();
        hsEmail.put(AppConstant.EMAIL, account.getEmail());

        HashMap<String, String> hsPassword = new HashMap<>();
        hsPassword.put(AppConstant.PASSWORD, account.getPassword());

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
            String result = jsonObject.getString("result");
            if (result.equals("true")) {
                modelLogin.setCacheLogin(context,account.getFullName());
                return true;
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
}
