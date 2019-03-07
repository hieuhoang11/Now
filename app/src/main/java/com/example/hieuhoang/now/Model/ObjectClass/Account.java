package com.example.hieuhoang.now.Model.ObjectClass;

/**
 * Created by Hieu Hoang on 19/02/2019.
 */

public class Account {

    private int ID_Account ;
    private String fullName ;
    private  String email;
    private String password;
    private int accountType ;

    public Account() {
    }

    public Account(int ID_Account, String fullName, String email, String password , int accountType) {
        this.ID_Account = ID_Account;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.accountType = accountType;
    }

    public int getID_Account() {
        return ID_Account;
    }

    public void setID_Account(int ID_Account) {
        this.ID_Account = ID_Account;
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
