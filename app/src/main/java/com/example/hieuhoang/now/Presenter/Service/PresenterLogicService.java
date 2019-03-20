package com.example.hieuhoang.now.Presenter.Service;

import com.example.hieuhoang.now.Model.ObjectClass.HotProduct;
import com.example.hieuhoang.now.Model.ObjectClass.Store;
import com.example.hieuhoang.now.Model.Store.ModelStore;
import com.example.hieuhoang.now.View.Main.Home.Service.ViewService;

import java.util.List;

/**
 * Created by Hieu Hoang on 24/02/2019.
 */

public class PresenterLogicService implements IPresenterService {
    private ModelStore modelProduct ;
    private ViewService viewService ;
    public PresenterLogicService(ViewService viewService){
        this.viewService = viewService ;
        modelProduct = new ModelStore();
    }
    @Override
    public void loadPreferential() {
        List<Store> list = modelProduct.getPreferentialStore();
        if(list.size() >0){
            viewService.loadPreferential(list);
        }
    }

    @Override
    public void loadHotProducts() {
        List<HotProduct> list = modelProduct.getStoreHasPromoProduct();
        if(list.size() >0){
            viewService.loadHotProduct(list);
        }
    }

    @Override
    public void loadRecommendStores() {
        List<Store> list = modelProduct.getRecommendStore();
        if(list.size() >0){
            viewService.loadRecommendStore(list);
        }
    }

    @Override
    public void loadJustOrderStores() {
        List<Store> list = modelProduct.getJustOrderStore();
        if(list.size() >0){
            viewService.loadJustOrderStore(list);
        }
    }

    @Override
    public void loadNearbyStores() {
        List<Store> list = modelProduct.getNearbyStore();
        if(list.size() >0){
            viewService.loadNearByStore(list);
        }
    }

    @Override
    public void loadNewStores() {
        List<Store> list = modelProduct.getNewStore();
        if(list.size() >0){
            viewService.loadNewStore(list);
        }
    }
}
