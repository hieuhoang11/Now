package com.example.hieuhoang.now.Presenter.Store.ListProduct;

import com.example.hieuhoang.now.Model.ObjectClass.Order;


public interface IPresenterListProductInStore {
    void getListProduct(String idStore);
    void setIsGrid(boolean b);
    void showQuantityProductInCraftOrder(Order order);
}
