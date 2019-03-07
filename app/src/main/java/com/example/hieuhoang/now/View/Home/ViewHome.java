package com.example.hieuhoang.now.View.Home;

import com.example.hieuhoang.now.Model.ObjectClass.HotProduct;
import com.example.hieuhoang.now.Model.ObjectClass.Store;

import java.util.List;

/**
 * Created by Hieu Hoang on 24/02/2019.
 */

public interface ViewHome {

    void loadHotProducts(List<HotProduct> list);
    void loadRecommendStores(List<Store> list);
}
