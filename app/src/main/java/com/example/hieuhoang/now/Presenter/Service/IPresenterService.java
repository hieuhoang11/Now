package com.example.hieuhoang.now.Presenter.Service;

public interface IPresenterService {
    void loadPreferential();
    void loadHotProducts();
    void loadRecommendStores();
    void loadJustOrderStores(String idService);
    void loadNearbyStores();
    void loadNewStores();
}
