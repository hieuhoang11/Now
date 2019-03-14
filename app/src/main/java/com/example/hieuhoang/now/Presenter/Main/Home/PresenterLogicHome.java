package com.example.hieuhoang.now.Presenter.Main.Home;

import com.example.hieuhoang.now.Model.ObjectClass.HotProduct;
import com.example.hieuhoang.now.Model.ObjectClass.Store;
import com.example.hieuhoang.now.Model.Store.ModelStore;
import com.example.hieuhoang.now.View.Home.ViewHome;

import java.util.List;

/**
 * Created by Hieu Hoang on 24/02/2019.
 */

public class PresenterLogicHome implements IPresenterHome {
    private ViewHome viewHome ;
    private ModelStore modelProduct;
    public PresenterLogicHome(ViewHome viewHome) {
        this.viewHome = viewHome ;
        modelProduct = new ModelStore() ;
    }
    @Override
    public void loadListHotProduct() {
        List<HotProduct> list = modelProduct.getStoreHasPromoProduct();
        if(list.size()>0){
            viewHome.loadHotProducts(list);
        }
    }

    @Override
    public void loadListRecommendProduct() {
        List<Store> list = modelProduct.getRecommendStore();
        if(list.size()>0){
            viewHome.loadRecommendStores(list);
        }
    }
}
