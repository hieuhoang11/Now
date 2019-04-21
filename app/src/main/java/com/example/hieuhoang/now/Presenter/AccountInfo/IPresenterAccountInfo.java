package com.example.hieuhoang.now.Presenter.AccountInfo;

public interface IPresenterAccountInfo {
    void getAccountInfo(String idAccount) ;
    void changePassword (String idAccount , String newPassword) ;
    void checkOldPassword (String email , String password) ;
}
