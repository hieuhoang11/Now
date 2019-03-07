package com.example.hieuhoang.now.Presenter.Store;

/**
 * Created by Hieu Hoang on 27/02/2019.
 */

public interface IPresenterStore {
    void getStoreByID(String idStore);
    void getListProduct(String idStore);
    void addProductsToCart();
    void setIsGrid(boolean b);
}
