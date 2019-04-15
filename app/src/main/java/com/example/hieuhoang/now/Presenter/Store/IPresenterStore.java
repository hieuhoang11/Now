package com.example.hieuhoang.now.Presenter.Store;

import com.example.hieuhoang.now.Model.ObjectClass.Order;
import com.example.hieuhoang.now.Model.ObjectClass.OrderDetail;
import com.example.hieuhoang.now.Model.ObjectClass.Product;

public interface IPresenterStore {
    void getStoreByID(String idStore);
    void addProductsToCart(Order order ,String idStore,String idProduct , int quantity,String note);
    void showSheetAddToCart (Product product) ;
    void getDraftOrder(String idStore) ;
    void getOrderDetail(String idOrder);
    void resetOrder(String idOrder);
    void updateQuantityProductInOrderDetail (String idOrder , String idProduct ,int quantity) ;
    boolean isEnoughItems (String idOrder,String idProduct, int quantity) ;
    void updateNoteDetailOrder(String idOrder , String idProduct ,String note) ;
    void deleteOrderDetail (String idOrder , String idProduct ) ;
    void addFavorite (String idStore);
    void removeFavorite (String idStore);
    void checkIsFavorite(String idStore) ;
}
