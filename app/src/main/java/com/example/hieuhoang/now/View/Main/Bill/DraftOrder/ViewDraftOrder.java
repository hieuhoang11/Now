package com.example.hieuhoang.now.View.Main.Bill.DraftOrder;

import com.example.hieuhoang.now.Model.ObjectClass.Order;

import java.util.List;

public interface ViewDraftOrder {
    void loadListDraftOrder(List<Order> mOrders ) ;
    void noHasDraftOrder( ) ;
}
