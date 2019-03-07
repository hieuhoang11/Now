package com.example.hieuhoang.now.Presenter.HotProduct;

import com.example.hieuhoang.now.Model.ObjectClass.HotProduct;
import com.example.hieuhoang.now.Model.Store.ModelStore;
import com.example.hieuhoang.now.View.HotProduct.ViewHotProduct;

import java.util.List;


public class PresenterLogicHotProduct implements IPresenterHotProduct {
    private ModelStore modelProduct ;
    private ViewHotProduct viewHotProduct ;
    public PresenterLogicHotProduct(ViewHotProduct viewHotProduct) {
        modelProduct = new ModelStore();
        this.viewHotProduct = viewHotProduct ;
    }
    @Override
    public void loadListHotProduct() {
        List<HotProduct> hotProductList = modelProduct.getAllStoreHasPromoProduct() ;
        if(hotProductList.size() > 0) {
            viewHotProduct.loadHotProducts(hotProductList);
        }
    }
}
