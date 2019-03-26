package com.example.hieuhoang.now.Model.Store.Product;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.hieuhoang.now.ConnectInternet.DownloadJSON;
import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.ObjectClass.GroupProduct;
import com.example.hieuhoang.now.Model.ObjectClass.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ModelProduct {
    private final String TAG = "kiemtra" ;

    public List<GroupProduct> getListGroupProductByIDStore(String idStore) {
        List<GroupProduct> list = new ArrayList<>();
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();
        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.GET_PRODUCTS_BY_ID_STORE);
        HashMap<String, String> hsID = new HashMap<>();
        hsID.put(AppConstant.ID_STORE, idStore);

        HashMap<String, String> hsIDStatus = new HashMap<>();
        hsIDStatus.put(AppConstant.ORDER_STATUS, String.valueOf(AppConstant.COMPLETE_ORDER_STATUS));

        attrs.add(hsFunction);
        attrs.add(hsID);
        attrs.add(hsIDStatus);
        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();
        try {
            String dataJSON = downloadJSON.get();
            JSONArray jsonArray = new JSONArray(dataJSON);
            int count = jsonArray.length();
            for (int i = 0; i < count; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String nameGroup = jsonObject.getString("TENDANHMUC");
                JSONArray array = jsonObject.getJSONArray("DSSANPHAM");
                int c = array.length();
                List<Product> listProducts = new ArrayList<>();
                for (int j = 0; j < c; j++) {
                    Product p = new Product();
                    JSONObject object = array.getJSONObject(j);
                    String imgProduct = object.getString(AppConstant.IMAGE).trim();
                    String nameProduct = object.getString(AppConstant.PRODUCT_NAME).trim();
                    String idProduct = object.getString(AppConstant.ID_PRODUCT).trim();
                    int quantity = object.getInt(AppConstant.QUANTITY);
                    float price = (float) object.getDouble(AppConstant.PRODUCT_PRICE);
                    float disCount = (float) object.getDouble(AppConstant.PRODUCT_DISCOUNT);
                    long quantityPurchased = object.getLong(AppConstant.QUANTITY_PURCHASED);
                    p.setId(idProduct);
                    p.setImage(imgProduct);
                    p.setProductName(nameProduct);
                    p.setQuantity(quantity);
                    p.setPrice(price);
                    p.setDiscount(disCount);
                    p.setQuantityPurchase(quantityPurchased);
                    listProducts.add(p);
                }
                if (listProducts.size() > 0) {
                    GroupProduct groupProduct = new GroupProduct();
                    groupProduct.setNameGroup(nameGroup);
                    groupProduct.setListProducts(listProducts);
                    list.add(groupProduct);
                }
            }
            return list;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void setIsGrid(Context context, boolean b) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstant.IS_GRID_IN_STORE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(AppConstant.IS_GRID, b);
        editor.apply();

    }

    public boolean getIsGrid(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstant.IS_GRID_IN_STORE, Context.MODE_PRIVATE);
        if (sharedPreferences == null) {
            return false;
        }
        boolean b = sharedPreferences.getBoolean(AppConstant.IS_GRID, false);
        return b;
    }

    public int getQuantityProduct(String idProduct) {
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.GET_QUANTITY_PRODUCT);

        HashMap<String, String> hsIDProduct = new HashMap<>();
        hsIDProduct.put(AppConstant.ID_PRODUCT, idProduct);

        attrs.add(hsFunction);
        attrs.add(hsIDProduct);

        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();

        try {
            String dataJson = downloadJSON.get();
            JSONObject jsonObject = new JSONObject(dataJson) ;
            int quantity = jsonObject.getInt(AppConstant.QUANTITY) ;
            return quantity ;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean updateQuantityProduct (String idProduct , String quantity) {
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.UPDATE_QUANTITY_PRODUCT);

        HashMap<String, String> hsIDProduct = new HashMap<>();
        hsIDProduct.put(AppConstant.ID_PRODUCT, idProduct);

        HashMap<String, String> hsQuantity = new HashMap<>();
        hsQuantity.put(AppConstant.QUANTITY, quantity);

        attrs.add(hsFunction);
        attrs.add(hsIDProduct);
        attrs.add(hsQuantity);

        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();
        try {
            String dataJson = downloadJSON.get();
            Log.i(TAG, "updateQuantityProduct: " + dataJson);
            JSONObject jsonObject = new JSONObject(dataJson) ;
            return jsonObject.getBoolean(AppConstant.RESULT);
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
