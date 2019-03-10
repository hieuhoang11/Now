package com.example.hieuhoang.now.Presenter.Store;

import android.content.Context;

import com.example.hieuhoang.now.Model.ObjectClass.GroupProduct;
import com.example.hieuhoang.now.Model.Store.ModelStore;
import com.example.hieuhoang.now.View.Store.Fragment.ViewListProductInStore;

import java.util.List;


public class PresenterLogicListProductInStore implements IPresenterListProductInStore {
    ViewListProductInStore viewListProductInStore ;
    ModelStore modelStore ;
    Context context ;
    public PresenterLogicListProductInStore(ViewListProductInStore viewListProductInStore,Context context){
        this.viewListProductInStore = viewListProductInStore ;
        modelStore = new ModelStore() ;
        this.context = context ;
    }
    @Override
    public void getListProduct(String idStore) {
        List<GroupProduct> list = modelStore.getListGroupProductByIDStore(idStore);
        if (list.size() > 0) {
            viewListProductInStore.loadListProductInStore(list, modelStore.getIsGrid(context));
        }
    }
    @Override
    public void setIsGrid(boolean b) {
        modelStore.setIsGrid(context, b);
    }
}
