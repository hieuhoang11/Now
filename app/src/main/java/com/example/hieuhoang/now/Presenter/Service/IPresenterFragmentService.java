package com.example.hieuhoang.now.Presenter.Service;

import com.example.hieuhoang.now.Model.ObjectClass.Store;

import java.util.List;

/**
 * Created by Hieu Hoang on 25/02/2019.
 */

public interface IPresenterFragmentService {

    void loadRecommendStores() ;
    void loadJustOrderStores() ;
    void loadNearbyStores() ;
    void loadNewStores() ;
}
