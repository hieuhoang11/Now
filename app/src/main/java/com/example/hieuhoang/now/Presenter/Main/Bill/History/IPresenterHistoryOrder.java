package com.example.hieuhoang.now.Presenter.Main.Bill.History;


public interface IPresenterHistoryOrder {
    void getDetailOrderStatus(String idOrder);

    void getListOrder(String startDay , String finisDay,String idService);
}
