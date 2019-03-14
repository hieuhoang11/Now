package com.example.hieuhoang.now.Presenter.Main.Bill;

import android.content.Context;

import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.LoginRegister.ModelLogin;
import com.example.hieuhoang.now.Model.ObjectClass.Account;
import com.example.hieuhoang.now.Model.ObjectClass.Order;
import com.example.hieuhoang.now.Model.Order.ModelOrder;
import com.example.hieuhoang.now.View.Main.Fragment.Bill.Fragment.ViewDraftOrder;

import java.util.List;

public class PresenterLogicDraftOrder implements IPresenterDraftOrder {
    ViewDraftOrder viewDraftOrder;
    ModelOrder modelOrder;
    ModelLogin modelLogin;
    Context context;

    public PresenterLogicDraftOrder(ViewDraftOrder viewDraftOrder, Context context) {
        this.viewDraftOrder = viewDraftOrder;
        modelOrder = new ModelOrder();
        modelLogin = new ModelLogin();
        this.context = context;
    }

    @Override
    public void getListDraftOrder() {
        Account customer = modelLogin.getAccountInformation(context);
        if (customer.getID_Account() == AppConstant.DEFAULT_ID_ACCOUNT)
            return;
        List<Order> list = modelOrder.getListDraftOrderByIdCustomer(String.valueOf(customer.getID_Account()));
        if (list.size() > 0) {
            viewDraftOrder.loadListDraftOrder(list);
        } else {
            viewDraftOrder.noHasDraftOrder();
        }

    }

    @Override
    public void deleteAllDraftOrder() {
        Account customer = modelLogin.getAccountInformation(context);
        if (customer.getID_Account() == AppConstant.DEFAULT_ID_ACCOUNT)
            return;
        boolean b = modelOrder.deleteAllDraftOrder(String.valueOf(customer.getID_Account())) ;
        if(b) {

        }
    }
}
