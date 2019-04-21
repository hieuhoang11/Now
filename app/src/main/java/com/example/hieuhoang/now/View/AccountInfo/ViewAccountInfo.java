package com.example.hieuhoang.now.View.AccountInfo;


import com.example.hieuhoang.now.Model.ObjectClass.Account;

public interface ViewAccountInfo {

    void displayAccountInfo (Account account) ;

    void onChangePasswordSuccess () ;

    void onChangePasswordFail () ;

    void oldPasswordIncorrect () ;

    void oldPasswordCorrect () ;
}
