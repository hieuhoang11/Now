package com.example.hieuhoang.now.View.Main.Bill.OnGoing;

import com.example.hieuhoang.now.Model.ObjectClass.Order;

import java.util.List;

public interface ViewOnGoing {
    void loadListDraftOrder(List<Order> mOrders ) ;
    void noHasDraftOrder( ) ;
}
