package com.example.hieuhoang.now.Presenter.Brand;


import com.example.hieuhoang.now.Model.ModelBrand.ModelBrand;
import com.example.hieuhoang.now.Model.ObjectClass.Store;
import com.example.hieuhoang.now.Model.Store.ModelStore;
import com.example.hieuhoang.now.View.Brand.ViewBrand;

import java.util.List;

public class PresenterLogicBrand implements IPresenterBrand {
    ViewBrand viewBrand;
    ModelBrand modelBrand;

    public PresenterLogicBrand(ViewBrand viewBrand) {
        this.viewBrand = viewBrand;
        modelBrand = new ModelBrand();
    }

    @Override
    public void getListStore(String idBrand) {
        List<Store> list = modelBrand.getListStoreByIdBrand(idBrand);
        if (list.size() > 0) {
            viewBrand.loadListStore(list);
        }
    }

    @Override
    public void getBrandName(String idBrand) {
        String brandName = modelBrand.getBrandName(idBrand);
        if (brandName != null) {
            viewBrand.setBrandName(brandName);
        }
    }
}
