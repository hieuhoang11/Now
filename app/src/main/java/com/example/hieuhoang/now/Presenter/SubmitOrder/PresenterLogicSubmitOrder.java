package com.example.hieuhoang.now.Presenter.SubmitOrder;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.hieuhoang.now.Model.Account.ModelAccount;
import com.example.hieuhoang.now.Model.Location.ModelLocation;
import com.example.hieuhoang.now.Model.ObjectClass.Account;
import com.example.hieuhoang.now.Model.ObjectClass.Order;
import com.example.hieuhoang.now.Model.ObjectClass.Store;
import com.example.hieuhoang.now.Model.Order.ModelOrder;
import com.example.hieuhoang.now.Model.Store.ModelStore;
import com.example.hieuhoang.now.View.Main.MainActivity;
import com.example.hieuhoang.now.View.SubmitOrder.ViewSubmitOrder;

public class PresenterLogicSubmitOrder implements IPresenterSubmitOrder {

    private ModelStore modelStore ;
    private ModelOrder modelOrder ;
    private ModelAccount modelAccount ;
    private ViewSubmitOrder viewSubmitOrder;

    public PresenterLogicSubmitOrder(Activity activity, Context context, ViewSubmitOrder viewSubmitOrder) {
        modelStore = new ModelStore() ;
        modelOrder = new ModelOrder();
        modelAccount = new ModelAccount() ;
        this.viewSubmitOrder = viewSubmitOrder;
    }

    @Override
    public void getLocation() {
        String address = MainActivity.modelLocation.getLocation();
        if (address != null) {
            viewSubmitOrder.addMarker(address);
        }
    }


    @Override
    public void getOrder(String idOrder) {
        Order order = modelOrder.getOrderInformation(idOrder) ;
        if(order == null) return;
        viewSubmitOrder.setInfoOrder(order);
        getStoreName(order.getIdStore()) ;
        getCustomerName(order.getIdCustomer());
    }

    private void getStoreName(String idStore) {
        Store store = modelStore.getStoreByID(idStore) ;
        if(store !=null) {
            viewSubmitOrder.setStoreName(store.getStoreName());
        }
    }
    private void getCustomerName (String idCustomer) {
        Account account = modelAccount.getAccountById(idCustomer) ;
        if(account == null) return;
        viewSubmitOrder.setCustomerName(account);
    }

}
