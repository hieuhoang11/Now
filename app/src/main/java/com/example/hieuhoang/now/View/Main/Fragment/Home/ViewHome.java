package com.example.hieuhoang.now.View.Main.Fragment.Home;

import com.example.hieuhoang.now.Model.ObjectClass.HotProduct;
import com.example.hieuhoang.now.Model.ObjectClass.Store;

import java.util.List;


public interface ViewHome {

    void loadHotProducts(List<HotProduct> list);
    void loadRecommendStores(List<Store> list);
}
