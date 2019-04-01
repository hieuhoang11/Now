package com.example.hieuhoang.now.Model.Store;

import android.util.Log;

import com.example.hieuhoang.now.ConnectInternet.DownloadJSON;
import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.ObjectClass.HotProduct;
import com.example.hieuhoang.now.Model.ObjectClass.Store;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ModelStore {
    private final String TAG = "kiemtra";

    public List<HotProduct> getAllStoreHasPromoProduct() {
        return getHotProducts(AppConstant.GET_ALL_STORE_HAS_PROMO_PRODUCT);
    }

    public List<HotProduct> getStoreHasPromoProduct() {
        return getHotProducts(AppConstant.GET_STORE_HAS_PROMO_PRODUCT);
    }

    private List<HotProduct> getHotProducts(String functionName) {
        List<HotProduct> list = new ArrayList<>();
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();
        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, functionName);

        attrs.add(hsFunction);
        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();

        try {
            String dataJson = downloadJSON.get();
            Log.i("kiemtra", "getHotProducts: " + dataJson);
            JSONArray jsonArray = new JSONArray(dataJson);
            int count = jsonArray.length();
            for (int i = 0; i < count; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String idStore = jsonObject.getString(AppConstant.ID_STORE);
                String imageProduct = jsonObject.getString(AppConstant.IMAGE);
                String storeName = jsonObject.getString(AppConstant.STORE_NAME);
                String discountNumber = jsonObject.getString(AppConstant.DISCOUNT_NUMBER);
                String productName = jsonObject.getString(AppConstant.PRODUCT_NAME);
                float oldPrice = (float) jsonObject.getDouble(AppConstant.PRODUCT_PRICE);
                float newPrice = (float) jsonObject.getDouble(AppConstant.PRODUCT_DISCOUNT);
                HotProduct p = new HotProduct();
                p.setIdStore(idStore);
                p.setImageProduct(imageProduct);
                p.setNewPrice(newPrice);
                p.setOldPrice(oldPrice);
                p.setProductName(productName);
                p.setDiscountNumber(discountNumber);
                p.setStoreName(storeName);
                list.add(p);
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

    public List<Store> getRecommendStore() {
        List<Store> list = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            Store store = new Store();
            store.setStoreName("Tên cửa hàng đó mà");
            store.setStoreAddress("địa chỉ cửa hàng nè");
            list.add(store);
        }
        return list;
    }

    public List<Store> getJustOrderStore() {
        List<Store> list = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Store store = new Store();
            store.setStoreName("Tên cửa hàng đó mà");
            store.setStoreAddress("địa chỉ cửa hàng nè");
            list.add(store);
        }
        return list;
    }

    public List<Store> getNearbyStore() {
        List<Store> list = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            Store store = new Store();
            store.setStoreName("Tên cửa hàng đó mà");
            store.setStoreAddress("địa chỉ cửa hàng nè");
            list.add(store);
        }
        return list;
    }

    public List<Store> getNewStore() {
        List<Store> list = new ArrayList<>();

        for (int i = 0; i < 23; i++) {
            Store store = new Store();
            store.setStoreName("Tên cửa hàng đó mà");
            store.setStoreAddress("địa chỉ cửa hàng nè");
            list.add(store);
        }
        return list;
    }

    public List<Store> getPreferentialStore() {
        List<Store> list = new ArrayList<>();

        for (int i = 0; i < 23; i++) {
            Store store = new Store();
            store.setStoreName("Tên cửa hàng đó mà");
            store.setStoreAddress("địa chỉ cửa hàng nè");
            list.add(store);
        }
        return list;
    }

    public Store getStoreByID(String idStore) {
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();
        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.FUNC_GET_STORE_BY_ID);
        HashMap<String, String> hsID = new HashMap<>();
        hsID.put(AppConstant.ID_STORE, idStore);

        attrs.add(hsFunction);
        attrs.add(hsID);
        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();
        try {
            String dataJSON = downloadJSON.get();
            Log.i("kiemtra", "getStoreByID: " + dataJSON);

            JSONObject jsonObject = new JSONObject(dataJSON);
            String storeName = jsonObject.getString(AppConstant.STORE_NAME).trim();
            String storeAddress = jsonObject.getString(AppConstant.STORE_ADDRESS).trim();
            String idBrand = jsonObject.getString(AppConstant.ID_BRAND);
            String image = jsonObject.getString(AppConstant.IMAGE).trim();
            Store store = new Store();
            store.setIdStore(idStore);
            store.setIdBrand(idBrand);
            store.setStoreName(storeName);
            store.setStoreAddress(storeAddress);
            store.setImage(image);
            return store;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Store> search(String idService, String condition , int start) {
        List<Store> list = new ArrayList<>();

        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();
        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.FUNC_SEARCH_STORE);
        HashMap<String, String> hsId = new HashMap<>();
        hsId.put(AppConstant.ID_SERVICE, idService);
        HashMap<String, String> hsCondition = new HashMap<>();
        hsCondition.put(AppConstant.CONDITION, condition);
        HashMap<String, String> hsStart = new HashMap<>();
        hsStart.put(AppConstant.START, String.valueOf(start));

        attrs.add(hsFunction);
        attrs.add(hsId);
        attrs.add(hsCondition);
        attrs.add(hsStart);

        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();
        try {
            String dataJson = downloadJSON.get();
            Log.i(TAG, "search: " + dataJson);
            JSONArray jsonArray = new JSONArray(dataJson);
            int l = jsonArray.length();
            for (int i = 0; i < l; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String idStore = jsonObject.getString(AppConstant.ID_STORE);
                String storeName = jsonObject.getString(AppConstant.STORE_NAME);
                String img = jsonObject.getString(AppConstant.IMAGE);
                String address = jsonObject.getString(AppConstant.STORE_ADDRESS);

                Store store = new Store();
                store.setImage(img);
                store.setStoreName(storeName);
                store.setIdStore(idStore);
                store.setStoreAddress(address);
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

}
