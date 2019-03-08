package com.example.hieuhoang.now.Presenter.Store;

import com.example.hieuhoang.now.Model.ObjectClass.Product;

/**
 * Created by Hieu Hoang on 27/02/2019.
 */

public interface IPresenterStore {
    void getStoreByID(String idStore);
    void getListProduct(String idStore);
    void addProductsToCart(String idStore,String idProduct , int quantity);
    void setIsGrid(boolean b);
    void showBottomSheet () ;
    void getSumQuantityProduct(String idStore) ;
}
