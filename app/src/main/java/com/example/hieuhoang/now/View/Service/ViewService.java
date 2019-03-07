package com.example.hieuhoang.now.View.Service;

import com.example.hieuhoang.now.Model.ObjectClass.HotProduct;
import com.example.hieuhoang.now.Model.ObjectClass.Store;

import java.util.List;

/**
 * Created by Hieu Hoang on 24/02/2019.
 */

public interface ViewService {
    void loadPreferential(List<Store> mStores) ;
    void loadHotProduct(List<HotProduct> mHotProducts) ;
    void loadRecommendStore(List<Store> mStores) ;
    void loadJustOrderStore(List<Store> mStores) ;
    void loadNearByStore(List<Store> mStores) ;
    void loadNewStore(List<Store> mStores) ;
}
