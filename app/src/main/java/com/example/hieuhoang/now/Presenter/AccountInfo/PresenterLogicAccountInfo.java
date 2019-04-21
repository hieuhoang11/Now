package com.example.hieuhoang.now.Presenter.AccountInfo;


import android.content.Context;

import com.example.hieuhoang.now.Model.Account.ModelAccount;
import com.example.hieuhoang.now.Model.LoginRegister.ModelLogin;
import com.example.hieuhoang.now.Model.ObjectClass.Account;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.View.AccountInfo.ViewAccountInfo;

public class PresenterLogicAccountInfo implements IPresenterAccountInfo {
    private ViewAccountInfo viewAccountInfo;
    private ModelAccount modelAccount;
    private ModelLogin modelLogin ;

    public PresenterLogicAccountInfo(Context context , ViewAccountInfo viewAccountInfo) {
        this.viewAccountInfo = viewAccountInfo;
        modelAccount = new ModelAccount();
        modelLogin = new ModelLogin(context) ;
    }

    @Override
    public void getAccountInfo(String idAccount) {
        Account account = modelAccount.getAccountById(idAccount);
        if (account != null)
            viewAccountInfo.displayAccountInfo(account);
    }

    @Override
    public void changePassword(String idAccount, String newPassword) {
        boolean b = modelAccount.changePassword(idAccount, newPassword);
        if (b)
            viewAccountInfo.onChangePasswordSuccess();
        else
            viewAccountInfo.onChangePasswordFail();

    }

    @Override
    public void checkOldPassword(String email, String newPassword) {
        boolean b = modelLogin.checkLogin(email,newPassword);
        if (b)
            viewAccountInfo.oldPasswordCorrect();
        else
            viewAccountInfo.oldPasswordIncorrect();

    }


}
