package com.example.hieuhoang.now.Presenter.Store;

import android.content.Context;

import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.LoginRegister.ModelLogin;
import com.example.hieuhoang.now.Model.ObjectClass.Account;
import com.example.hieuhoang.now.Model.ObjectClass.GroupProduct;
import com.example.hieuhoang.now.Model.ObjectClass.Product;
import com.example.hieuhoang.now.Model.ObjectClass.Store;
import com.example.hieuhoang.now.Model.Store.ModelStore;
import com.example.hieuhoang.now.Presenter.Service.IPresenterService;
import com.example.hieuhoang.now.View.Store.ViewStore;

import java.util.List;

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
    public void getListProduct(String idStore) {
        List<GroupProduct> list = modelStore.getListGroupProductByIDStore(idStore);
        if (list.size() > 0) {
            viewStore.loadListProduct(list, modelStore.getIsGrid(context));
        }
    }

    @Override
    public void addProductsToCart(String idStore, String idProduct, int quantity) {
        int idAccount = modelLogin.getAccountInformation(context).getID_Account();
        boolean b = modelStore.addProductsToCart(String.valueOf(idAccount), idStore, idProduct, quantity);
        if (b) {
            viewStore.addToCartSuccess();
        }
    }

    public void setIsGrid(boolean b) {
        modelStore.setIsGrid(context, b);
    }

    @Override
    public void showBottomSheet() {
        Account account = modelLogin.getAccountInformation(context);
        if (account.getID_Account() != AppConstant.DEFAULT_ID_ACCOUNT) {
            viewStore.showBottomSheet();
        } else {
            viewStore.startLoginActivity();
        }
    }

    @Override
    public void getSumQuantityProduct(String idStore) {
        int idAccount = modelLogin.getAccountInformation(context).getID_Account();
        int quantity = modelStore.getSumQuantityProduct(idStore,String.valueOf(idAccount)) ;
        if(quantity > 0) {
            viewStore.showCart(quantity);
        }
    }
}
