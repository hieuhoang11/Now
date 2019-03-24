package com.example.hieuhoang.now.View.SubmitOrder;

import com.example.hieuhoang.now.Model.ObjectClass.Account;
import com.example.hieuhoang.now.Model.ObjectClass.Order;
import com.example.hieuhoang.now.Model.ObjectClass.OrderDetail;

import java.util.List;
import java.util.Map;

public interface ViewSubmitOrder {
    void addMarker (String address) ;
    void setStoreName (String storeName) ;
    void setInfoOrder (Order order) ;
    void setCustomerName (Account account) ;
    void loadOrderDetail (List<OrderDetail> mDetailList) ;
    void noEnoughQuantity (Map<String,Integer> map) ;
    void onSubmitSuccess () ;
}
