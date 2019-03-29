package com.example.hieuhoang.now.Presenter.Main.Bill.OnGoing;

import android.content.Context;

import com.example.hieuhoang.now.Model.Account.ModelAccount;
import com.example.hieuhoang.now.Model.LoginRegister.ModelLogin;
import com.example.hieuhoang.now.Model.ObjectClass.Account;
import com.example.hieuhoang.now.Model.ObjectClass.Order;
import com.example.hieuhoang.now.Model.Order.ModelOrder;
import com.example.hieuhoang.now.Presenter.Main.Bill.OnGoing.IPresenterOnGoing;
import com.example.hieuhoang.now.View.Main.Bill.OnGoing.ViewOnGoing;

import java.util.List;
import java.util.Map;

public class PresenterLogicOnGoing implements IPresenterOnGoing {
    private ViewOnGoing viewOnGoing;
    private ModelOrder modelOrder;
    private ModelLogin modelLogin;

    public PresenterLogicOnGoing(ViewOnGoing viewOnGoing, Context context) {
        this.viewOnGoing = viewOnGoing;
        modelOrder = new ModelOrder();
        modelLogin = new ModelLogin(context);
    }

    @Override
    public void getDetailOrderStatus(String idOrder) {
        Map<Integer,String> map = modelOrder.getOrderStatus(idOrder) ;
        if(map.size()>0) {
            viewOnGoing.showOrderStatus(map,idOrder);
        }

    }

    @Override
    public void getListOrder() {
        if(!modelLogin.isLogged()) return;
        Account customer = modelLogin.getAccountInformation();
        List<Order> list = modelOrder.getListOnGoingOrder(customer.getIdAccount());
        if (list.size() > 0) {
            viewOnGoing.loadListOrder(list);
        } else viewOnGoing.noHasOrder();
    }

    @Override
    public void cancelOrder(String idOrder) {
        boolean b = modelOrder.cancelOrder(idOrder);
        if(b) {
            getListOrder();
            viewOnGoing.onCancelOrderSuccess();
        }
    }
}
