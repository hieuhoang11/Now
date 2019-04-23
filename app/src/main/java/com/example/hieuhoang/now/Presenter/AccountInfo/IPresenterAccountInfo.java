package com.example.hieuhoang.now.Presenter.AccountInfo;

import com.example.hieuhoang.now.Model.ObjectClass.Account;

public interface IPresenterAccountInfo {
    void getAccountInfo(String idAccount) ;
    void changePassword (String idAccount , String newPassword) ;
    void checkOldPassword (String email , String password) ;
    void changeInfo (Account account) ;
}
