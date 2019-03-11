package com.example.hieuhoang.now.Presenter.Store;

import com.example.hieuhoang.now.Model.ObjectClass.Order;

/**
 * Created by Hieu Hoang on 09/03/2019.
 */

public interface IPresenterListProductInStore {
    void getListProduct(String idStore);
    void setIsGrid(boolean b);
    void showQuantityProductInCraftOrder(Order order);
}
