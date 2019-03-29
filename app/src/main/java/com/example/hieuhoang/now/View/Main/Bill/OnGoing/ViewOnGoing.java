package com.example.hieuhoang.now.View.Main.Bill.OnGoing;

import com.example.hieuhoang.now.Model.ObjectClass.Order;

import java.util.List;
import java.util.Map;

public interface ViewOnGoing {
    void loadListOrder(List<Order> mOrders);

    void noHasOrder();

    void showOrderStatus(Map<Integer, String> map, String idOrder);

    void onCancelOrderSuccess();
}
