package com.example.hieuhoang.now.View.Store;

import com.example.hieuhoang.now.Model.ObjectClass.OrderDetail;
import com.example.hieuhoang.now.Model.ObjectClass.Order;
import com.example.hieuhoang.now.Model.ObjectClass.Store;

import java.util.List;

/**
 * Created by Hieu Hoang on 27/02/2019.
 */

public interface ViewStore {
    void loadInformationStore(Store store);

    void showCartDetail(List<OrderDetail> orderDetailList);

    void showBottomSheet();

    void startLoginActivity();

    void showCart(Order order);

    void onResetDraftOrderSuccess () ;
}
