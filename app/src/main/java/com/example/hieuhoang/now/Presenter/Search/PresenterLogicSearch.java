package com.example.hieuhoang.now.Presenter.Search;


import com.example.hieuhoang.now.Constant.AppConstant;
import com.example.hieuhoang.now.Model.ObjectClass.Store;
import com.example.hieuhoang.now.Model.Store.ModelStore;
import com.example.hieuhoang.now.View.Search.ViewSearch;

import java.util.ArrayList;
import java.util.List;

public class PresenterLogicSearch implements IPresenterSearch {
    private ViewSearch viewSearch;
    private ModelStore modelStore;

    public PresenterLogicSearch(ViewSearch viewSearch) {
        this.viewSearch = viewSearch;
        modelStore = new ModelStore();
    }

    @Override
    public void search(List<Store> mStores, String idService, String condition, int start) {
        List<Store> list;
        list = modelStore.search(idService, condition, start);
        if (list.size() == 0) {
            if (mStores.size() == 0) {
                viewSearch.noHasResult();
            }
            viewSearch.dismissProcess();
            return;

        }
        if (list.size() == 0) return;
        mStores.addAll(list);
        viewSearch.displayListStore(mStores);
    }
}
