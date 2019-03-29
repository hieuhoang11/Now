package com.example.hieuhoang.now.View.OrderDetail;


import com.example.hieuhoang.now.Model.ObjectClass.Account;
import com.example.hieuhoang.now.Model.ObjectClass.Order;
import com.example.hieuhoang.now.Model.ObjectClass.OrderDetail;

import java.util.List;

public interface ViewOrderDetail {
    void setStoreName (String storeName) ;
    void setInfoOrder (Order order) ;
    void setCustomerInfo (Account account) ;
    void loadOrderDetail (List<OrderDetail> mDetailList) ;
}
