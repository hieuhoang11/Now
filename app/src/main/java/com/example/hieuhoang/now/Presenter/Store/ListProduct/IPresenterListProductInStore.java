package com.example.hieuhoang.now.Presenter.Store.ListProduct;

import com.example.hieuhoang.now.Model.ObjectClass.Order;
import com.example.hieuhoang.now.Model.ObjectClass.Store;


public interface IPresenterListProductInStore {
    void getListProduct(Store store);
    void setIsGrid(boolean b);
    void showQuantityProductInCraftOrder(Order order);
}
