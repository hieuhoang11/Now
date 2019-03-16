package com.example.hieuhoang.now.Presenter.LoginRegister.Login;

import android.content.Context;

import com.example.hieuhoang.now.Model.LoginRegister.ModelLogin;
import com.example.hieuhoang.now.View.LoginRegister.Login.ViewLogin;
import com.facebook.AccessToken;

public class PresenterLogicLogin implements IPresenterLogin {
    private ModelLogin modelLogin;
    ViewLogin viewLogin ;
    public PresenterLogicLogin (ViewLogin viewLogin , Context context) {
        this.viewLogin = viewLogin ;
        modelLogin = new ModelLogin(context);
    }
    @Override
    public AccessToken getTokenFacebook() {
        AccessToken accessToken = modelLogin.getAccessTokenFacebook();;
        return accessToken ;
    }

    @Override
    public void loginAccount(String email, String password) {
        boolean check = modelLogin.checkLogin(email,password);
        if(check)
            viewLogin.onLoginSuccess();
        else viewLogin.onLoginFail();
    }
}
