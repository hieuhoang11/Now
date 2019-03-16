package com.example.hieuhoang.now.View.Store;

import com.example.hieuhoang.now.Model.ObjectClass.OrderDetail;
import com.example.hieuhoang.now.Model.ObjectClass.Order;
import com.example.hieuhoang.now.Model.ObjectClass.Product;
import com.example.hieuhoang.now.Model.ObjectClass.Store;

import java.util.List;
import java.util.Map;

public interface ViewStore {
    void loadInformationStore(Store store);

    void showCartDetail(List<OrderDetail> orderDetailList);

    void closeCartAndCartDetail();

    void showBottomSheetAddToCart(Product product);

    void startLoginActivity();

    void showCart(Order order);

    void onResetDraftOrderSuccess();

    void disPlayQuantityOfProductInCraftOrder (Map<String,Integer> map) ;

    void showSheetEditNote (String idProduct  ,String note) ;

    void deleteItemOrderDetail (String idOrder , String idProduct);
}
