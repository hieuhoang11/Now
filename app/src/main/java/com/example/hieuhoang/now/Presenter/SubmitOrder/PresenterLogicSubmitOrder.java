package com.example.hieuhoang.now.Presenter.SubmitOrder;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.hieuhoang.now.Model.Account.ModelAccount;
import com.example.hieuhoang.now.Model.Location.ModelLocation;
import com.example.hieuhoang.now.Model.ObjectClass.Account;
import com.example.hieuhoang.now.Model.ObjectClass.Order;
import com.example.hieuhoang.now.Model.ObjectClass.OrderDetail;
import com.example.hieuhoang.now.Model.ObjectClass.Store;
import com.example.hieuhoang.now.Model.Order.ModelOrder;
import com.example.hieuhoang.now.Model.Store.ModelStore;
import com.example.hieuhoang.now.Model.Store.Product.ModelProduct;
import com.example.hieuhoang.now.View.Main.MainActivity;
import com.example.hieuhoang.now.View.SubmitOrder.ViewSubmitOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PresenterLogicSubmitOrder implements IPresenterSubmitOrder {

    private ModelStore modelStore;
    private ModelOrder modelOrder;
    private ModelProduct modelProduct;
    private ModelAccount modelAccount;
    private ViewSubmitOrder viewSubmitOrder;
    String TAG = "kiemtra";

    public PresenterLogicSubmitOrder(Activity activity, Context context, ViewSubmitOrder viewSubmitOrder) {
        modelStore = new ModelStore();
        modelOrder = new ModelOrder();
        modelProduct = new ModelProduct();
        modelAccount = new ModelAccount();
        this.viewSubmitOrder = viewSubmitOrder;
    }

    @Override
    public void getLocation() {
//        String address = MainActivity.modelLocation.getLocation();
//        if (address != null) {
//            viewSubmitOrder.addMarker(address);
//        }
    }


    @Override
    public void getOrder(String idOrder) {
        Order order = modelOrder.getOrderInformation(idOrder);
        if (order == null) return;
        viewSubmitOrder.setInfoOrder(order);

        getOrderDetail(idOrder);

        getStoreName(order.getIdStore());
        getCustomerInfo(order.getIdCustomer());
    }

    @Override
    public void getOrderDetail(String idOrder) {
        List<OrderDetail> list = modelOrder.getListOrderDetail(idOrder);
        if (list.size() > 0) {
            viewSubmitOrder.loadOrderDetail(list);
        }
    }

    @Override
    public void getStoreName(String idStore) {
        Store store = modelStore.getStoreByID(idStore);
        if (store != null) {
            viewSubmitOrder.setStoreName(store.getStoreName());
        }
    }

    @Override
    public void getCustomerInfo(String idCustomer) {
        Account account = modelAccount.getAccountById(idCustomer);
        if (account == null) return;
        viewSubmitOrder.setCustomerInfo(account);
    }

    @Override
    public void submitOrder(Order order, List<OrderDetail> mDetailList) {
        Map<String, Integer> mapQuantity = isEnoughQuantity(mDetailList) ;
        if (mapQuantity.size() == mDetailList.size()) {
            Log.i(TAG, "submitOrder: 1");
            boolean b = modelOrder.submitOrder(order.getIdOrder(), order.getCustomerAddress(), order.getNote());
            if (b) {
                Log.i(TAG, "submitOrder: 2");
                for(Map.Entry<String,Integer> values : mapQuantity.entrySet()){
                    b = modelProduct.updateQuantityProduct(values.getKey(), String.valueOf(values.getValue()));
                }
                if (b) viewSubmitOrder.onSubmitSuccess();
            }
        }
    }

    private Map<String, Integer> isEnoughQuantity(List<OrderDetail> mDetailList) {
        boolean b = true;
        Map<String, Integer> mapQuantity = new HashMap<>();
        Map<String, Integer> map = new HashMap<>();
        for (OrderDetail detail : mDetailList) {
            int q = modelProduct.getQuantityProduct(detail.getIdProduct());
            if (q < detail.getQuantity()) {
                map.put(detail.getIdProduct(), q);
                b = false;
            } else mapQuantity.put(detail.getIdProduct(), q - detail.getQuantity());
        }
        if (!b) viewSubmitOrder.noEnoughQuantity(map);
        return mapQuantity;
    }

}
