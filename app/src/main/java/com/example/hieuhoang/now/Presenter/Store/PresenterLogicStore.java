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
            order = modelStore.addNewOrder(idAccount, idStore);
        }
        if (order == null) {
            return;
        }
        boolean b = modelStore.addDetailOrder(order.getIdOrder(), idProduct, quantity, note);
        if (!b) return;

        order = modelStore.getOrderInformation(order.getIdOrder());
        if (order == null) return;

        viewStore.showCart(order);

        disPlayQuantityOfProductInCraftOrder(order.getIdOrder());
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
            viewStore.showCartDetail(list);
        } else viewStore.closeCartAndCartDetail();

    }

    @Override
    public void resetOrder(String idOrder) {
        boolean b = modelStore.deleteDraftOrder(idOrder);
        if (b)
            viewStore.onResetDraftOrderSuccess();
    }

    @Override
    public void updateQuantityProductInOrderDetail(String idOrder, String idProduct, int quantity) {
        boolean b;
        if (quantity == 0)
            b = modelStore.deleteDetailOrder(idOrder, idProduct);
        else b = modelStore.updateQuantityProductInOrderDetail(idOrder, idProduct, quantity);
        if (b) {
            List<OrderDetail> list = modelStore.getListOrderDetail(idOrder);
            int s = list.size();
            if (s == 0) {
                if (modelStore.deleteDraftOrder(idOrder)) {
                    viewStore.onResetDraftOrderSuccess();
                    viewStore.disPlayQuantityOfProductInCraftOrder(null);
                }
                return;
            }

            viewStore.showCartDetail(list);

            Order order = modelStore.getOrderInformation(idOrder);
            if (order != null) {
                viewStore.showCart(order);

                disPlayQuantityOfProductInCraftOrder(order.getIdOrder());
            }
        }
    }

    @Override
    public boolean isEnoughItems(String idOrder, String idProduct, int quantity) {
        int q = modelStore.getQuantityProduct(idProduct);
        if (idOrder == null) return q >= quantity ;
        int q2 = modelStore.getQuantityProductInDraftOrder(idOrder, idProduct);
        return (q - q2) >= quantity ;
    }

    public void disPlayQuantityOfProductInCraftOrder(String idOrder) {
        List<OrderDetail> mList = modelStore.getListOrderDetail(idOrder);
        Map<String, Integer> map = null;
        if (mList.size() > 0) {
            map = new HashMap<>();
            for (OrderDetail detail : mList) {
                map.put(detail.getIdProduct(), detail.getQuantity());
            }
        }
        viewStore.disPlayQuantityOfProductInCraftOrder(map);
    }
}
