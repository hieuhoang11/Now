package com.example.hieuhoang.now.Presenter.Service.Fragment;

import com.example.hieuhoang.now.Model.ObjectClass.Store;
import com.example.hieuhoang.now.Model.Store.ModelStore;
import com.example.hieuhoang.now.View.Main.Home.Service.Fragment.ViewFragment;

import java.util.List;
public class PresenterLogicFragmentService implements IPresenterFragmentService {
    ViewFragment viewFragment ;
    ModelStore modelStore ;
    public PresenterLogicFragmentService(ViewFragment viewFragment ){
        this.viewFragment = viewFragment;
        modelStore = new ModelStore();
    }
    @Override
    public void loadRecommendStores() {
        List<Store> list = modelStore.getRecommendStore();
        if(list.size() > 0) {
            viewFragment.loadProducts(list);
        }
    }

    @Override
    public void loadJustOrderStores() {
        List<Store> list = modelStore.getJustOrderStore();
        if(list.size() > 0) {
            viewFragment.loadProducts(list);
        }
    }

    @Override
    public void loadNearbyStores() {
        List<Store> list = modelStore.getNearbyStore();
        if(list.size() > 0) {
            viewFragment.loadProducts(list);
        }
    }

    @Override
    public void loadNewStores() {
        List<Store> list = modelStore.getNewStore();
        if(list.size() > 0) {
            viewFragment.loadProducts(list);
        }
    }
}
