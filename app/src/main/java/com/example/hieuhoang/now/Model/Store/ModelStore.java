package com.example.hieuhoang.now.Model.Store;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.hieuhoang.now.ConnectInternet.DownloadJSON;
import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.ObjectClass.GroupProduct;
import com.example.hieuhoang.now.Model.ObjectClass.HotProduct;
import com.example.hieuhoang.now.Model.ObjectClass.Order;
import com.example.hieuhoang.now.Model.ObjectClass.Product;
import com.example.hieuhoang.now.Model.ObjectClass.Store;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ModelStore {

    public List<HotProduct> getAllStoreHasPromoProduct(){
        return getHotProducts(AppConstant.GET_ALL_STORE_HAS_PROMO_PRODUCT) ;
    }

    public List<HotProduct> getStoreHasPromoProduct(){
        return getHotProducts(AppConstant.GET_STORE_HAS_PROMO_PRODUCT) ;
    }

    public List<HotProduct> getHotProducts (String functionName) {

        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();
        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION,functionName );

        attrs.add(hsFunction);
        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();
        List<HotProduct> list = new ArrayList<>() ;
        try {
            String dataJson = downloadJSON.get();
            Log.i("kiemtra", dataJson);
            JSONArray jsonArray = new JSONArray(dataJson) ;
            int count = jsonArray.length() ;
            for(int i =0 ; i < count ; i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String idStore = jsonObject.getString("ID_cuahang");
                String imageProduct = jsonObject.getString("hinhanhsanpham");
                String storeName = jsonObject.getString("ten_cuahang");
                String quantityDiscount = jsonObject.getString("sogiamgia");
                String productName = jsonObject.getString("tensanpham");
                String oldPrice= jsonObject.getString("dongia");
                String newPrice= jsonObject.getString("giamoi");

                HotProduct p = new HotProduct();
                p.setIdStore(idStore);
                p.setImageProduct(imageProduct);
                p.setNewPrice(newPrice);
                p.setOldPrice(oldPrice);
                p.setProductName(productName);
                p.setQuantityDiscount(quantityDiscount);
                p.setStoreName(storeName);
                list.add(p) ;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list ;
    }

    public List<Store> getRecommendStore () {
        List<Store> list = new ArrayList<>() ;

        for(int i = 0 ; i< 2 ; i++ ) {
            list.add(new Store());
        }
        return list ;
    }

    public List<Store> getJustOrderStore () {
        List<Store> list = new ArrayList<>() ;

        for(int i = 0 ; i< 3 ; i++ ) {
            list.add(new Store());
        }
        return list ;
    }

    public List<Store> getNearbyStore () {
        List<Store> list = new ArrayList<>() ;

        for(int i = 0 ; i< 4 ; i++ ) {
            list.add(new Store());
        }
        return list ;
    }

    public List<Store> getNewStore () {
        List<Store> list = new ArrayList<>() ;

        for(int i = 0 ; i< 23 ; i++ ) {
            list.add(new Store());
        }
        return list ;
    }

    public List<Store> getPreferentialStore () {
        List<Store> list = new ArrayList<>() ;

        for(int i = 0 ; i< 23 ; i++ ) {
            list.add(new Store());
        }
        return list ;
    }

    public Store getStoreByID(String idStore){
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();
        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.GET_STORE_BY_ID);
        HashMap<String, String> hsID = new HashMap<>();
        hsID.put(AppConstant.ID_STORE, idStore);

        attrs.add(hsFunction);
        attrs.add(hsID);
        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();
        try {
            String dataJSON = downloadJSON.get();
            if(dataJSON.trim().equals("null")){
                return null;
            }

            Log.i("kiemtra", "getStoreByID: " + dataJSON);

            JSONObject jsonObject = new JSONObject(dataJSON);
            String storeName = jsonObject.getString("ten_cuahang").trim();
            String storeAddress = jsonObject.getString("vitri_cuahang").trim();
            int ID_thuonghieu = jsonObject.getInt("ID_thuonghieu");
            String img = jsonObject.getString("hinhanh_cuahang").trim();
            Store store = new Store();
            store.setID_Store(Integer.parseInt(idStore));
            store.setID_ThuongHieu(ID_thuonghieu);
            store.setStoreName(storeName);
            store.setStoreAddress(storeAddress);
            store.setImage(img);
            return store ;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<GroupProduct> getListGroupProductByIDStore(String idStore){
        List<GroupProduct> list = new ArrayList<>();
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();
        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.GET_PRODUCTS_BY_ID_STORE);
        HashMap<String, String> hsID = new HashMap<>();
        hsID.put(AppConstant.ID_STORE, idStore);

        attrs.add(hsFunction);
        attrs.add(hsID);
        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();
        try {
            String dataJSON = downloadJSON.get();
            Log.i("kiemtra", "getListGroupProductByIDStore: " + dataJSON);
            if(dataJSON.trim().equals("null")){
                return null;
            }

            JSONArray jsonArray = new JSONArray(dataJSON);
            int count = jsonArray.length();
            for(int i = 0 ; i < count ; i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String nameGroup = jsonObject.getString("TENDANHMUC");
                JSONArray array = jsonObject.getJSONArray("DSSANPHAM");
                int c = array.length() ;
                List<Product> listProducts = new ArrayList<>();
                for(int j = 0 ; j < c ; j++) {
                    Product p = new Product() ;
                    JSONObject object = array.getJSONObject(j);
                    String imgProduct = object.getString("HINHANHSANPHAM").trim();
                    String nameProduct = object.getString("TENSANPHAM").trim();
                    String idProduct = object.getString("ID_SANPHAM").trim();
                    int quantity = object.getInt("SOLUONG");
                    float price = (float) object.getDouble("DONGIA");
                    float disCount = (float) object.getDouble("GIAMGIA");
                    long quantityPurchased = object.getLong("SOLUONGBAN");
                    p.setId(idProduct);
                    p.setImage(imgProduct);
                    p.setNameProduct(nameProduct);
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
            return list ;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addProductsToCart (String idAccount ,String idStore ,String idProduct , int quality) {

        Order order = addNewOrder(idAccount,idStore) ;
        if(order == null) {
            return false;
        }
        return addDetailOrder(order.getIdOrder(),idProduct,quality) ;
    }

    public Order addNewOrder (String idAccount , String idStore) {
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();
        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.ADD_NEW_ORDER);
        HashMap<String, String> hsIDAccount = new HashMap<>();
        hsIDAccount.put(AppConstant.ID_ACCOUNT, idAccount);

        HashMap<String, String> hsIDStore = new HashMap<>();
        hsIDStore.put(AppConstant.ID_STORE, idStore);

        attrs.add(hsFunction);
        attrs.add(hsIDAccount);
        attrs.add(hsIDStore);
        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();
        try {
            String dataJSON = downloadJSON.get();
            JSONObject jsonObject = new JSONObject(dataJSON) ;
            String idOrder = jsonObject.getString(AppConstant.ID_ORDER);
            Order order = new Order();
            order.setIdCustomer(idAccount);
            order.setIdOrder(idOrder);
            order.setIdStore(idStore);
            return order ;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addDetailOrder (String idOrder ,String idProduct , int quantity) {
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.ADD_DETAIL_ORDER);

        HashMap<String, String> hsIDOrder = new HashMap<>();
        hsIDOrder.put(AppConstant.ID_ORDER,idOrder);

        HashMap<String, String> hsIDProduct = new HashMap<>();
        hsIDProduct.put(AppConstant.ID_PRODUCT, idProduct);

        HashMap<String, String> hsQuantity = new HashMap<>();
        hsQuantity.put(AppConstant.QUANTITY, String.valueOf(quantity));

        attrs.add(hsFunction);
        attrs.add(hsIDOrder);
        attrs.add(hsIDProduct);
        attrs.add(hsQuantity);
        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();
        try {
            String dataJson = downloadJSON.get();
            JSONObject jsonObject = new JSONObject(dataJson) ;
            return jsonObject.getBoolean(AppConstant.RESULT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false ;
    }

    public int getSumQuantityProduct(String idStore , String idAccount) {
        String path = AppConstant.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> hsFunction = new HashMap<>();
        hsFunction.put(AppConstant.FUNCTION, AppConstant.GET_SUM_QUALITY_PRODUCT_IN_ORDER);

        HashMap<String, String> hsIDStore = new HashMap<>();
        hsIDStore.put(AppConstant.ID_STORE,idStore);

        HashMap<String, String> hsIDAccount = new HashMap<>();
        hsIDAccount.put(AppConstant.ID_ACCOUNT, idAccount);

        attrs.add(hsFunction);
        attrs.add(hsIDStore);
        attrs.add(hsIDAccount);

        DownloadJSON downloadJSON = new DownloadJSON(path, attrs);
        downloadJSON.execute();

        try {
            String dataJson = downloadJSON.get();
            JSONObject jsonObject = new JSONObject(dataJson) ;
            return jsonObject.getInt(AppConstant.QUANTITY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0 ;
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
}
