package com.example.hieuhoang.now.Presenter.Main.Account;


import com.example.hieuhoang.now.Model.Account.ModelAccount;

public class PresenterAccount implements IPresenterAccount {

    private ModelAccount modelAccount;

    public PresenterAccount() {
        modelAccount = new ModelAccount();
    }

    @Override
    public void checkEmailExitsAndRegister(String email) {
        String idAccount = modelAccount.getIdByEmail(email);
        if(idAccount == null){

        }

    }

    @Override
    public void checkIdFacebookExitsAndRegister(String idFacebook) {

    }
}
