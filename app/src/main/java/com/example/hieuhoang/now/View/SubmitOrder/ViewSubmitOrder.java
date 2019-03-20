package com.example.hieuhoang.now.View.SubmitOrder;

import com.example.hieuhoang.now.Model.ObjectClass.Account;
import com.example.hieuhoang.now.Model.ObjectClass.Order;

public interface ViewSubmitOrder {
    void addMarker (String address) ;
    void setStoreName (String storeName) ;
    void setInfoOrder (Order order) ;
    void setCustomerName (Account account) ;
}
