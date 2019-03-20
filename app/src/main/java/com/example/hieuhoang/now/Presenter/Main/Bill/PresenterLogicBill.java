package com.example.hieuhoang.now.Presenter.Main.Bill;

import android.content.Context;

import com.example.hieuhoang.now.Model.LoginRegister.ModelLogin;
import com.example.hieuhoang.now.View.Main.Bill.ViewBill;

public class PresenterLogicBill implements IPresenterBill{
private ViewBill viewBill ;
private ModelLogin modelLogin ;
    public PresenterLogicBill(ViewBill viewBill , Context context) {
        this.viewBill = viewBill ;
        modelLogin = new ModelLogin(context) ;
    }

    @Override
    public void checkLogin() {
        if(modelLogin.isLogged())
            viewBill.loadOrder();
        else viewBill.notLogIn();
    }
}
