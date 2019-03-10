package com.example.hieuhoang.now.Presenter.Store;

import com.example.hieuhoang.now.Model.ObjectClass.Order;
import com.example.hieuhoang.now.Model.ObjectClass.Product;

/**
 * Created by Hieu Hoang on 27/02/2019.
 */

public interface IPresenterStore {
    void getStoreByID(String idStore);
    void addProductsToCart(Order order ,String idStore,String idProduct , int quantity,String note);
    void checkLogin () ;
    void getDraftOrder(String idStore) ;
    void getOrderDetail(String idOrder);
    void resetOrder(String idOrder);
}
