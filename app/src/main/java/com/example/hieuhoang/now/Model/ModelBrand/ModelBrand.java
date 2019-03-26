package com.example.hieuhoang.now.Model.ModelBrand;


import android.util.Log;

import com.example.hieuhoang.now.ConnectInternet.DownloadJSON;
import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.ObjectClass.Store;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ModelBrand {
    public List<Store> getListStoreByIdBrand(String idBrand) {
        List<Store> list = new ArrayList<>();
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();
        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.GET_LIST_STORE_BY_ID_BRAND);
        HashMap<String, String> hsID = new HashMap<>();
        hsID.put(AppConstant.ID_BRAND, idBrand);

        attrs.add(hsFunction);
        attrs.add(hsID);
        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();
        try {
            String dataJson = downloadJSON.get();
            Log.i("kiemtra", "getListStoreByIdBrand: " + dataJson);
            JSONArray jsonArray = new JSONArray(dataJson);
            int count = jsonArray.length();
            for (int i = 0; i < count; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String idStore = jsonObject.getString(AppConstant.ID_STORE);
                String storeName = jsonObject.getString(AppConstant.STORE_NAME);
                String image = jsonObject.getString(AppConstant.IMAGE);
                String address = jsonObject.getString(AppConstant.STORE_ADDRESS);
                Store store = new Store();
                store.setIdStore(idStore);
                store.setStoreName(storeName);
                store.setStoreAddress(address);
                store.setImage(image);
                list.add(store);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public String getBrandName(String idBrand) {

        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();
        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.GET_BRAND_NAME);
        HashMap<String, String> hsID = new HashMap<>();
        hsID.put(AppConstant.ID_BRAND, idBrand);

        attrs.add(hsFunction);
        attrs.add(hsID);
        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();
        try {
            String dataJson = downloadJSON.get();
            JSONObject jsonObject = new JSONObject(dataJson);
            return jsonObject.getString(AppConstant.BRAND_NAME);
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
