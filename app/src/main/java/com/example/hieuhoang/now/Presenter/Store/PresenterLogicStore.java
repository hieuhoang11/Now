package com.example.hieuhoang.now.Presenter.Store;

import android.content.Context;

import com.example.hieuhoang.now.Model.ObjectClass.GroupProduct;
import com.example.hieuhoang.now.Model.ObjectClass.Store;
import com.example.hieuhoang.now.Model.Store.ModelStore;
import com.example.hieuhoang.now.Presenter.Service.IPresenterService;
import com.example.hieuhoang.now.View.Store.ViewStore;

import java.util.List;

/**
 * Created by Hieu Hoang on 27/02/2019.
 */

public class PresenterLogicStore implements IPresenterStore {
    ModelStore modelStore ;
    ViewStore viewStore ;
    Context context ;
    public PresenterLogicStore (ViewStore viewStore , Context context) {
        this.viewStore = viewStore;
        modelStore = new ModelStore();
        this.context = context ;
    }
    @Override
    public void getStoreByID(String idStore) {
        Store store = modelStore.getStoreByID(idStore) ;
        if(store != null) {
            viewStore.loadInformationStore(store);
        }
    }

    @Override
    public void getListProduct(String idStore) {
        List<GroupProduct> list = modelStore.getListGroupProductByIDStore(idStore);
        if(list.size() > 0) {
            viewStore.loadListProduct(list,modelStore.getIsGrid(context));
        }
    }

    @Override
    public void addProductsToCart() {

    }

    public void setIsGrid (boolean b) {
        modelStore.setIsGrid(context , b);
    }
}
