package com.example.hieuhoang.now.Presenter.Main.Bill.OnGoing;

public interface IPresenterOnGoing {
    void getDetailOrderStatus(String idOrder);

    void getListOrder();

    void cancelOrder(String idOrder);
}
