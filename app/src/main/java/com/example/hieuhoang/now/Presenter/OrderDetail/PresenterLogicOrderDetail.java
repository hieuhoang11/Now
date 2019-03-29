package com.example.hieuhoang.now.Presenter.OrderDetail;


import android.content.Context;

import com.example.hieuhoang.now.Model.Account.ModelAccount;
import com.example.hieuhoang.now.Model.ObjectClass.Account;
import com.example.hieuhoang.now.Model.ObjectClass.Order;
import com.example.hieuhoang.now.Model.ObjectClass.OrderDetail;
import com.example.hieuhoang.now.Model.ObjectClass.Store;
import com.example.hieuhoang.now.Model.Order.ModelOrder;
import com.example.hieuhoang.now.Model.Store.ModelStore;
import com.example.hieuhoang.now.Model.Store.Product.ModelProduct;
import com.example.hieuhoang.now.View.OrderDetail.ViewOrderDetail;
import com.example.hieuhoang.now.View.SubmitOrder.ViewSubmitOrder;

import java.util.List;

public class PresenterLogicOrderDetail implements IPresenterOrderDetail{
    private ModelStore modelStore;
    private ModelOrder modelOrder;
    private ModelProduct modelProduct;
    private ModelAccount modelAccount;
    private ViewOrderDetail viewOrderDetail;
    String TAG = "kiemtra";

    public PresenterLogicOrderDetail( Context context, ViewOrderDetail viewOrderDetail) {
        modelStore = new ModelStore();
        modelOrder = new ModelOrder();
        modelProduct = new ModelProduct();
        modelAccount = new ModelAccount();
        this.viewOrderDetail = viewOrderDetail;
    }

    @Override
    public void getOrder(String idOrder) {
        Order order = modelOrder.getOrderById(idOrder);
        if (order == null) return;
        viewOrderDetail.setInfoOrder(order);

        getOrderDetail(idOrder);

        getStoreName(order.getIdStore());
        getCustomerInfo(order.getIdCustomer());
    }

    @Override
    public void getOrderDetail(String idOrder) {
        List<OrderDetail> list = modelOrder.getListOrderDetail(idOrder);
        if (list.size() > 0) {
            viewOrderDetail.loadOrderDetail(list);
        }
    }

    @Override
    public void getStoreName(String idStore) {
        Store store = modelStore.getStoreByID(idStore);
        if (store != null) {
            viewOrderDetail.setStoreName(store.getStoreName());
        }
    }

    @Override
    public void getCustomerInfo(String idCustomer) {
        Account account = modelAccount.getAccountById(idCustomer);
        if (account == null) return;
        viewOrderDetail.setCustomerInfo(account);
    }
}
