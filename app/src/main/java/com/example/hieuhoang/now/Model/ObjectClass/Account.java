package com.example.hieuhoang.now.Model.ObjectClass;

import android.support.v7.app.AppCompatActivity;

import com.example.hieuhoang.now.Constant.AppConstant;

/**
 * Created by Hieu Hoang on 19/02/2019.
 */

public class Account {

    private String idAccount ;
    private String fullName ;
    private String phoneNumber ;
    private String email;
    private String password;
    private int accountType ;

    public Account() {
        idAccount = AppConstant.DEFAULT_ID_ACCOUNT;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String userName) {
        this.fullName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }
}
