package com.example.hieuhoang.now.Presenter.Main.Home;

import com.example.hieuhoang.now.Model.Home.ModelHome;
import com.example.hieuhoang.now.Model.ObjectClass.HotProduct;
import com.example.hieuhoang.now.Model.ObjectClass.Store;
import com.example.hieuhoang.now.Model.Store.ModelStore;
import com.example.hieuhoang.now.View.Main.Home.ViewHome;

import java.util.List;

public class PresenterLogicHome implements IPresenterHome {
    private ViewHome viewHome ;
    private ModelHome modelHome ;
    private ModelStore modelProduct;
    public PresenterLogicHome(ViewHome viewHome) {
        this.viewHome = viewHome ;
        modelProduct = new ModelStore() ;
        modelHome = new ModelHome() ;
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

    @Override
    public void loadBanner() {
        List<Store> list = modelHome.getListImageBanner() ;
        if(list.size() > 0 ) viewHome.loadBanner(list);
        else viewHome.disappearBanner();
    }
}
