package com.example.hieuhoang.now.Presenter.Main.Bill.OnGoingHistory;

import android.content.Context;

import com.example.hieuhoang.now.Model.Account.ModelAccount;
import com.example.hieuhoang.now.Model.LoginRegister.ModelLogin;
import com.example.hieuhoang.now.Model.ObjectClass.Account;
import com.example.hieuhoang.now.Model.ObjectClass.Order;
import com.example.hieuhoang.now.Model.Order.ModelOrder;
import com.example.hieuhoang.now.View.Main.Bill.OnGoingHistory.ViewOnGoingHistory;

import java.util.List;
import java.util.Map;

public class PresenterLogicOnGoing implements IPresenterOnGoingHistory {
    private ViewOnGoingHistory viewOnGoingHistory;
    private ModelOrder modelOrder;
    private ModelLogin modelLogin;

    public PresenterLogicOnGoing(ViewOnGoingHistory viewOnGoingHistory, Context context) {
        this.viewOnGoingHistory = viewOnGoingHistory;
        modelOrder = new ModelOrder();
        modelLogin = new ModelLogin(context);
    }

    @Override
    public void getDetailOrderStatus(String idOrder) {
        Map<Integer,String> map = modelOrder.getOrderStatus(idOrder) ;
        if(map.size()>0) {
            viewOnGoingHistory.showOrderStatus(map);
        }

    }

    @Override
    public void getListDraftOrder() {
        Account customer = modelLogin.getAccountInformation();
        List<Order> list = modelOrder.getListOnGoingOrder(customer.getIdAccount());
        if (list.size() > 0) {
            viewOnGoingHistory.loadListOrder(list);
        } else viewOnGoingHistory.noHasOrder();
    }
}
