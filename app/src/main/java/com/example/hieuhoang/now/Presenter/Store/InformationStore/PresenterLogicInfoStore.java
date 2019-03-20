package com.example.hieuhoang.now.Presenter.Store.InformationStore;


import com.example.hieuhoang.now.Model.Store.Info.ModelInfoStore;
import com.example.hieuhoang.now.View.Store.InfoStore.ViewInfoStore;

public class PresenterLogicInfoStore implements IPresenterInfoStore{

    private ViewInfoStore viewInfoStore ;
    private ModelInfoStore modelInfoStore ;
    public PresenterLogicInfoStore (ViewInfoStore viewInfoStore) {
        modelInfoStore = new ModelInfoStore() ;
        this.viewInfoStore = viewInfoStore ;
    }
    @Override
    public void getQuantityBranch(String idBrand) {
        int count = modelInfoStore.countBranch(idBrand) ;
        if(count > 1) {
            viewInfoStore.showQuantityBranch(count);
        }
    }

}
