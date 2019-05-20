package com.example.hieuhoang.now.Model.Account;

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

public class ModelAccount {
    public Account getAccountById(String idAccount) {
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();
        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.GET_ACCOUNT_BY_ID);
        HashMap<String, String> hsID = new HashMap<>();
        hsID.put(AppConstant.ID_ACCOUNT, idAccount);

        attrs.add(hsFunction);
        attrs.add(hsID);

        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();
        try {
            String dataJson = downloadJSON.get();
            JSONObject jsonObject = new JSONObject(dataJson);

            String fullName = jsonObject.getString(AppConstant.FULL_NAME).trim();
            String phone = jsonObject.getString(AppConstant.PHONE).trim();
            String email = jsonObject.getString(AppConstant.EMAIL).trim();
            if(phone.toLowerCase().equals("null")) phone = "";
            Account account = new Account();
            account.setIdAccount(idAccount);
            account.setPhoneNumber(phone);
            account.setFullName(fullName);
            account.setEmail(email);
            return account;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateAccountInfo(Account account) {
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();
        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.FUNC_UPDATE_ACC_INFO);
        HashMap<String, String> hsID = new HashMap<>();
        hsID.put(AppConstant.ID_ACCOUNT, account.getIdAccount());
        HashMap<String, String> hsName = new HashMap<>();
        hsName.put(AppConstant.FULL_NAME, account.getFullName());
        HashMap<String, String> hsPhone = new HashMap<>();
        hsPhone.put(AppConstant.PHONE, account.getPhoneNumber());
        attrs.add(hsFunction);
        attrs.add(hsID);
        attrs.add(hsName);
        attrs.add(hsPhone);
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

        return false;
    }

    public boolean changePassword(String idAccount, String newPassword) {
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();
        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.FUNC_CHANGE_PASSWORD);
        HashMap<String, String> hsID = new HashMap<>();
        hsID.put(AppConstant.ID_ACCOUNT, idAccount);
        HashMap<String, String> hsPassword = new HashMap<>();
        hsPassword.put(AppConstant.PASSWORD, Util.getMD5(newPassword));
        attrs.add(hsFunction);
        attrs.add(hsID);
        attrs.add(hsPassword);

        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();
        try {
            String dataJson = downloadJSON.get();
            return Util.parseBooleanJson(dataJson);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getIdByEmail (String email) {
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();
        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.FUNC_GET_ID_BY_EMAIL);
        HashMap<String, String> hsEmail = new HashMap<>();
        hsEmail.put(AppConstant.EMAIL, email);
        attrs.add(hsFunction);
        attrs.add(hsEmail);
        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();
        try {
            String dataJson = downloadJSON.get();
            JSONObject jsonObject = new JSONObject(dataJson);
            return  jsonObject.getString(AppConstant.ID_ACCOUNT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
