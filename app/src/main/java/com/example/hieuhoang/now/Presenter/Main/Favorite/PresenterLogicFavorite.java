package com.example.hieuhoang.now.Presenter.Main.Favorite;

import android.content.Context;

import com.example.hieuhoang.now.Model.LoginRegister.ModelLogin;
import com.example.hieuhoang.now.Model.ObjectClass.Account;
import com.example.hieuhoang.now.Model.ObjectClass.Store;
import com.example.hieuhoang.now.Model.Store.ModelStore;
import com.example.hieuhoang.now.View.Main.Favorite.ViewFavorite;

import java.util.List;

public class PresenterLogicFavorite implements IPresenterFavorite{
    private ViewFavorite viewFavorite ;
    private ModelLogin modelLogin ;
    private ModelStore modelStore ;


    public PresenterLogicFavorite(ViewFavorite viewFavorite, Context context) {
        this.viewFavorite = viewFavorite;
        modelLogin = new ModelLogin(context);
        modelStore = new ModelStore() ;
    }

    @Override
    public void getListFavorite() {
        if(modelLogin.isLogged()) {
            Account account =  modelLogin.getAccount();
            List<Store> list = modelStore.getListFavorite(account.getIdAccount()) ;
            if(list.size() > 0) {
                viewFavorite.loadListFavorite(list);
            }else viewFavorite.noData();
        }else viewFavorite.noLogged();
    }

}
