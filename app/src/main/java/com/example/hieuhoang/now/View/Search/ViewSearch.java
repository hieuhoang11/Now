package com.example.hieuhoang.now.View.Search;

import com.example.hieuhoang.now.Model.ObjectClass.Store;

import java.util.List;

public interface ViewSearch {

    void displayListStore (List<Store> mStores) ;

    void noHasResult () ;

    void dismissProcess () ;
}
