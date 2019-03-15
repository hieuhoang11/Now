package com.example.hieuhoang.now.Presenter.LoginRegister.Register;

import android.content.Context;

import com.example.hieuhoang.now.Model.ObjectClass.Account;
import com.example.hieuhoang.now.Model.LoginRegister.ModelRegister;
import com.example.hieuhoang.now.View.LoginRegister.Register.ViewRegister;


public class PresenterLogicRegister implements IPresenterRegister {
    private ViewRegister viewRegister;
    private ModelRegister modelRegister;

    public PresenterLogicRegister(ViewRegister viewRegister, Context context) {
        this.viewRegister = viewRegister;
        modelRegister = new ModelRegister(context);
    }

    @Override
    public void RegisterAccount(Account account) {
        boolean checkEmail = modelRegister.checkEmailExists(account.getEmail());
        if (checkEmail) {
            viewRegister.onEmailExists();
            return;
        }
        boolean insert = modelRegister.RegisterAccount(account);
        if (insert)
            viewRegister.onRegisterSuccess();
        else viewRegister.onRegisterFail();

    }
}
