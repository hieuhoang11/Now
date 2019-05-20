package com.example.hieuhoang.now.Presenter.SubmitOrder;

import com.example.hieuhoang.now.Model.ObjectClass.Order;
import com.example.hieuhoang.now.Model.ObjectClass.OrderDetail;

import java.util.List;

public interface IPresenterSubmitOrder {
    void getOrder (String idOrder) ;
    void getOrderDetail (String idOrder) ;
    void getCustomerInfo(String idCustomer);
    void submitOrder (Order order, List<OrderDetail> mDetailList) ;
}
