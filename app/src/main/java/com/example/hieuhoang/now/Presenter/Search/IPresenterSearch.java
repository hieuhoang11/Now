package com.example.hieuhoang.now.Presenter.Search;

import com.example.hieuhoang.now.Model.ObjectClass.Store;

import java.util.List;

public interface IPresenterSearch {
    void search (List<Store> list, String idService, String condition, int start) ;
}
