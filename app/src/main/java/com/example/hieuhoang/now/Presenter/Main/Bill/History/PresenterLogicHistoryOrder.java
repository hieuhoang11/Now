package com.example.hieuhoang.now.Presenter.Main.Bill.History;

import android.content.Context;

import com.example.hieuhoang.now.Model.LoginRegister.ModelLogin;
import com.example.hieuhoang.now.Model.ObjectClass.Account;
import com.example.hieuhoang.now.Model.ObjectClass.Order;
import com.example.hieuhoang.now.Model.Order.ModelOrder;
import com.example.hieuhoang.now.Presenter.Main.Bill.History.IPresenterHistoryOrder;
import com.example.hieuhoang.now.View.Main.Bill.History.ViewHistory;

import java.util.List;
import java.util.Map;

public class PresenterLogicHistoryOrder implements IPresenterHistoryOrder{
    private ModelOrder modelOrder;
    private ModelLogin modelLogin;
    private ViewHistory viewHistory ;

    public PresenterLogicHistoryOrder (Context context , ViewHistory viewHistory) {
        this.viewHistory = viewHistory ;
        modelLogin = new ModelLogin(context);
        modelOrder = new ModelOrder() ;
    }
    @Override
    public void getDetailOrderStatus(String idOrder) {
        Map<Integer,String> map = modelOrder.getOrderStatus(idOrder) ;
        if(map.size()>0) {
            viewHistory.showOrderStatus(map,idOrder);
        }
    }

    @Override
    public void getListOrder(String startDate , String finisDate) {
        if(!modelLogin.isLogged()) return;
        Account customer = modelLogin.getAccountInformation();
        List<Order> list = modelOrder.getListHistoryOrder(customer.getIdAccount(),startDate,finisDate);
        if (list.size() > 0) {
            viewHistory.loadListOrder(list);
        } else viewHistory.noHasOrder();
    }
}