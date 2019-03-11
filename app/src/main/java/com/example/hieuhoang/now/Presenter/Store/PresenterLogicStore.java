package com.example.hieuhoang.now.Presenter.Store;

import android.content.Context;
import android.util.Log;

import com.example.hieuhoang.now.Common.Common;
import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.LoginRegister.ModelLogin;
import com.example.hieuhoang.now.Model.ObjectClass.Account;
import com.example.hieuhoang.now.Model.ObjectClass.GroupProduct;
import com.example.hieuhoang.now.Model.ObjectClass.Order;
import com.example.hieuhoang.now.Model.ObjectClass.OrderDetail;
import com.example.hieuhoang.now.Model.ObjectClass.Product;
import com.example.hieuhoang.now.Model.ObjectClass.Store;
import com.example.hieuhoang.now.Model.Store.ModelStore;
import com.example.hieuhoang.now.Presenter.Service.IPresenterService;
import com.example.hieuhoang.now.View.Store.ViewStore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hieu Hoang on 27/02/2019.
 */

public class PresenterLogicStore implements IPresenterStore {
    ModelStore modelStore;
    ModelLogin modelLogin;
    ViewStore viewStore;
    Context context;

    public PresenterLogicStore(ViewStore viewStore, Context context) {
        this.viewStore = viewStore;
        modelStore = new ModelStore();
        modelLogin = new ModelLogin(context);
        this.context = context;
    }

    @Override
    public void getStoreByID(String idStore) {
        Store store = modelStore.getStoreByID(idStore);
        if (store != null) {
            viewStore.loadInformationStore(store);
        }
    }

    @Override
    public void addProductsToCart(Order order, String idStore, String idProduct, int quantity, String note) {
        if (order == null) {
            String idAccount = String.valueOf(modelLogin.getAccountInformation(context).getID_Account());
            order = modelStore.addOrder(idAccount, idStore);
        }
        if (order == null) {
            return;
        }
        boolean b = modelStore.addDetailOrder(order.getIdOrder(), idProduct, quantity, note);
        if (!b) return;

        order = modelStore.getOrderInformation(order.getIdOrder());
        if (order == null) return;

        viewStore.showCart(order);

        List<OrderDetail> mList = modelStore.getListOrderDetail(order.getIdOrder()) ;
        Map<String,Integer> map = null ;
        if(mList.size() > 0) {
            map = new HashMap<>();
            for(OrderDetail detail : mList) {
                map.put(detail.getIdProduct(),detail.getQuantity()) ;
            }
        }
        viewStore.showQuantityProductInCraftOrder(map);
    }

    @Override
    public void showSheetAddToCart(Product product) {
        Account account = modelLogin.getAccountInformation(context);
        if (account.getID_Account() != AppConstant.DEFAULT_ID_ACCOUNT) {
            viewStore.showBottomSheetAddToCart(product);
        } else {
            viewStore.startLoginActivity();
        }
    }

    @Override
    public void getDraftOrder(String idStore) {
        int idAccount = modelLogin.getAccountInformation(context).getID_Account();
        Order order = modelStore.getDraftOrder(idStore, String.valueOf(idAccount));
        if (order == null) return;
        viewStore.showCart(order);

    }

    @Override
    public void getOrderDetail(String idOrder) {
        List<OrderDetail> list = modelStore.getListOrderDetail(idOrder);
        int s = list.size();
        if (s > 0) {
//            int items = 0 ;
//            float money = 0;
//            for(OrderDetail detail : list) {
//                items += detail.getQuantity() ;
//                money += detail.getQuantity() * (detail.getDisCount() == 0 ? detail.getProductPrice() : detail.getDisCount()) ;
//            }
            viewStore.showCartDetail(list);
        } else viewStore.closeCartDetail();

    }

    @Override
    public void resetOrder(String idOrder) {
        boolean b = modelStore.deleteDraftOrder(idOrder);
        if (b)
            viewStore.onResetDraftOrderSuccess();
    }

    @Override
    public void updateQuantityProductInOrderDetail(String idOrder, String idProduct, int quantity) {
        boolean b = modelStore.updateQuantityProductInOrderDetail(idOrder, idProduct, quantity);
        if (b) {
            getOrderDetail(idOrder);
            Order order = modelStore.getDraftOrderByIdOrder(idOrder);
            if (order != null) {
                viewStore.showCart(order);
            }
        }
    }
}
