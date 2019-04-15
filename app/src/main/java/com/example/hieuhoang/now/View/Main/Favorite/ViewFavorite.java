package com.example.hieuhoang.now.View.Main.Favorite;


import com.example.hieuhoang.now.Model.ObjectClass.Store;

import java.util.List;

public interface ViewFavorite {
    void noLogged () ;
    void loadListFavorite (List<Store> list) ;
    void noData () ;
}
