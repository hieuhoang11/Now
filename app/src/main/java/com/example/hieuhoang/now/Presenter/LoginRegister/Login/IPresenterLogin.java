package com.example.hieuhoang.now.Presenter.LoginRegister.Login;

import com.facebook.AccessToken;

/**
 * Created by Hieu Hoang on 14/02/2019.
 */

public interface IPresenterLogin {
    AccessToken getTokenFacebook () ;
    void loginAccount(String email , String password);
}
