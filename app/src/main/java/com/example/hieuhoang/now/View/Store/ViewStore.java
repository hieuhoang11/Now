package com.example.hieuhoang.now.View.Store;

import com.example.hieuhoang.now.Model.ObjectClass.OrderDetail;
import com.example.hieuhoang.now.Model.ObjectClass.Order;
import com.example.hieuhoang.now.Model.ObjectClass.Product;
import com.example.hieuhoang.now.Model.ObjectClass.Store;

import java.util.List;
import java.util.Map;

/**
 * Created by Hieu Hoang on 27/02/2019.
 */

public interface ViewStore {
    void loadInformationStore(Store store);

    void showCartDetail(List<OrderDetail> orderDetailList);

    void closeCartDetail();

    void showBottomSheetAddToCart(Product product);

    void startLoginActivity();

    void showCart(Order order);

    void onResetDraftOrderSuccess();

    void showQuantityProductInCraftOrder (Map<String,Integer> map) ;
}
