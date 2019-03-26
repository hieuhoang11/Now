package com.example.hieuhoang.now.Presenter.SubmitOrder.EditInfo;


import com.example.hieuhoang.now.Model.Account.ModelAccount;
import com.example.hieuhoang.now.Model.ObjectClass.Account;
import com.example.hieuhoang.now.View.SubmitOrder.EditInfo.ViewEditInfo;

public class PresenterLogicEditInfo implements IPresenterEditInfo {
    private ModelAccount modelAccount;
    private ViewEditInfo viewEditInfo;

    public PresenterLogicEditInfo(ViewEditInfo viewEditInfo) {
        modelAccount = new ModelAccount();
        this.viewEditInfo = viewEditInfo;
    }

    @Override
    public void updateCusInfo(Account account) {
        boolean b = modelAccount.updateAccountInfo(account);
        if(b) {
            viewEditInfo.onUpdateCusInfoSuccess();
        }
    }
}
