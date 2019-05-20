package com.example.hieuhoang.now.Model.Store;

import android.util.Log;

import com.example.hieuhoang.now.ConnectInternet.DownloadJSON;
import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.ObjectClass.HotProduct;
import com.example.hieuhoang.now.Model.ObjectClass.Store;
import com.example.hieuhoang.now.Util.Util;

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

        for (int i = 0; i < 4; i++) {
            Store store = new Store();
            store.setStoreName("Tên cửa hàng đó mà");
            store.setStoreAddress("địa chỉ cửa hàng nè");
            store.setPriceProduct(0);
            list.add(store);
        }
        return list;
    }

    public List<Store> getJustOrderStore(String idService) {
        List<Store> list = new ArrayList<>();
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();
        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.FUNC_GET_JUST_ORDER);
        HashMap<String, String> hsStats = new HashMap<>();
        hsStats.put(AppConstant.ORDER_STATUS,String.valueOf( AppConstant.COMPLETE_ORDER_STATUS));
        HashMap<String, String> hsIdService = new HashMap<>();
        hsIdService.put(AppConstant.ID_SERVICE, idService);
        attrs.add(hsFunction);
        attrs.add(hsStats);
        attrs.add(hsIdService);

        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();
        try {
            String dataJson = downloadJSON.get();
            Log.i(TAG, "getJustOrderStore: " + dataJson);
            list = parseJSonStore(dataJson) ;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Store> getNearbyStore() {
        List<Store> list = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            Store store = new Store();
            store.setStoreName("Tên cửa hàng đó mà");
            store.setStoreAddress("địa chỉ cửa hàng nè");
            store.setPriceProduct(0);
            list.add(store);
        }
        return list;
    }

    public List<Store> getNewStore(String idService) {
        List<Store> list = new ArrayList<>();
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();
        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.FUNC_GET_NEW_PRODUCT);

        HashMap<String, String> hsIdService = new HashMap<>();
        hsIdService.put(AppConstant.ID_SERVICE, idService);
        attrs.add(hsFunction);

        attrs.add(hsIdService);

        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();
        try {
            String dataJson = downloadJSON.get();
            Log.i(TAG, "getNewStore: " + dataJson);
            list = parseJSonStore(dataJson) ;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Store> getPreferentialStore() {
        List<Store> list = new ArrayList<>();

        for (int i = 0; i < 23; i++) {
            Store store = new Store();
            store.setStoreName("Tên cửa hàng đó mà");
            store.setStoreAddress("địa chỉ cửa hàng nè");
            store.setPriceProduct(0);
            list.add(store);
        }
        return list;
    }

    public List<Store> getListFavorite (String idCustomer) {
        List<Store> list = new ArrayList<>();
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();
        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.FUNC_GET_LIST_FAVORITE);
        HashMap<String, String> hsIdCustomer = new HashMap<>();
        hsIdCustomer.put(AppConstant.ID_ACCOUNT, idCustomer);
        attrs.add(hsFunction);
        attrs.add(hsIdCustomer);

        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();
        try {
            String dataJson = downloadJSON.get();
            Log.i(TAG, "getListFavorite: " +dataJson);
            list = parseJSonStore(dataJson) ;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
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

    public List<Store> search(String idService, String condition, int start) {
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
        HashMap<String, String> hsLimit = new HashMap<>();
        hsLimit.put(AppConstant.LIMIT, String.valueOf(AppConstant.LIMIT_ROW));

        attrs.add(hsFunction);
        attrs.add(hsId);
        attrs.add(hsCondition);
        attrs.add(hsStart);
        attrs.add(hsLimit);

        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();
        try {
            String dataJson = downloadJSON.get();
            Log.i(TAG, "search: " + dataJson);
            list = parseJSonStore(dataJson);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Store> getListStoreByIdBrand(String idBrand) {
        List<Store> list = new ArrayList<>();
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();
        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.FUNC_GET_LIST_STORE_BY_ID_BRAND);
        HashMap<String, String> hsID = new HashMap<>();
        hsID.put(AppConstant.ID_BRAND, idBrand);

        attrs.add(hsFunction);
        attrs.add(hsID);
        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();
        try {
            String dataJson = downloadJSON.get();
            Log.i("kiemtra", "getListStoreByIdBrand: " + dataJson);
            list = parseJSonStore(dataJson);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    private List<Store> parseJSonStore(String dataJson) throws JSONException {
        List<Store> list = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(dataJson);
        int l = jsonArray.length();
        for (int i = 0; i < l; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String idStore = jsonObject.getString(AppConstant.ID_STORE);
            String storeName = jsonObject.getString(AppConstant.STORE_NAME);
            String img = jsonObject.getString(AppConstant.IMAGE);
            String address = jsonObject.getString(AppConstant.STORE_ADDRESS);
            float price = (float) jsonObject.getDouble(AppConstant.PRODUCT_PRICE);
            boolean promo = jsonObject.getBoolean(AppConstant.PROMO);
            Store store = new Store();
            store.setImage(img);
            store.setStoreName(storeName);
            store.setIdStore(idStore);
            store.setStoreAddress(address);
            store.setPriceProduct(price);
            store.setPromo(promo);
            list.add(store);
        }
        return list;
    }

    public boolean addFavorite(String idOrder, String idAccount) {
        return handleFavorite(idOrder, idAccount, AppConstant.FUNC_ADD_FAVORITE);
    }

    public boolean removeFavorite(String idOrder, String idAccount) {
        return handleFavorite(idOrder, idAccount, AppConstant.FUNC_REMOVE_FAVORITE);
    }

    public boolean isFavorite(String idOrder, String idAccount) {
        return handleFavorite(idOrder, idAccount, AppConstant.FUNC_IS_FAVORITE);
    }

    private boolean handleFavorite(String idOrder, String idAccount, String function) {
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();
        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, function);
        HashMap<String, String> hsIdStore = new HashMap<>();
        hsIdStore.put(AppConstant.ID_STORE, idOrder);
        HashMap<String, String> hsIdCus = new HashMap<>();
        hsIdCus.put(AppConstant.ID_ACCOUNT, idAccount);
        attrs.add(hsFunction);
        attrs.add(hsIdStore);
        attrs.add(hsIdCus);
        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();
        try {
            String dataJson = downloadJSON.get();
            Log.i(TAG, "handleFavorite: " + function + " " + dataJson);
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
}
