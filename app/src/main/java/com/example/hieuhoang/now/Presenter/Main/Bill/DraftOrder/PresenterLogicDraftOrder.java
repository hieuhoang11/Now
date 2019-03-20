package com.example.hieuhoang.now.Presenter.Main.Bill.DraftOrder;

import android.content.Context;

import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.LoginRegister.ModelLogin;
import com.example.hieuhoang.now.Model.ObjectClass.Account;
import com.example.hieuhoang.now.Model.ObjectClass.Order;
import com.example.hieuhoang.now.Model.Order.ModelOrder;
import com.example.hieuhoang.now.View.Main.Bill.DraftOrder.ViewDraftOrder;

import java.util.List;

public class PresenterLogicDraftOrder implements IPresenterDraftOrder {
    ViewDraftOrder viewDraftOrder;
    ModelOrder modelOrder;
    ModelLogin modelLogin;
    Context context;

    public PresenterLogicDraftOrder(ViewDraftOrder viewDraftOrder, Context context) {
        this.viewDraftOrder = viewDraftOrder;
        modelOrder = new ModelOrder();
        modelLogin = new ModelLogin(context);
        this.context = context;
    }

    @Override
    public void getListDraftOrder() {
        Account customer = modelLogin.getAccountInformation();
        if (customer.getIdAccount() == AppConstant.DEFAULT_ID_ACCOUNT)
            return;
        List<Order> list = modelOrder.getListDraftOrderByIdCustomer(String.valueOf(customer.getIdAccount()));
        if (list.size() > 0) {
            viewDraftOrder.loadListDraftOrder(list);
        } else {
            viewDraftOrder.noHasDraftOrder();
        }

    }

    @Override
    public void deleteAllDraftOrder() {
        Account customer = modelLogin.getAccountInformation();
        if (customer.getIdAccount() == AppConstant.DEFAULT_ID_ACCOUNT)
            return;
        boolean b = modelOrder.deleteAllDraftOrder(String.valueOf(customer.getIdAccount())) ;
        if(b) {

        }
    }

    @Override
    public void deleteDraftOrder(String idOrder) {
        boolean b = modelOrder.deleteDraftOrder(idOrder) ;
        if(b) {
            getListDraftOrder() ;
        }
    }
}
