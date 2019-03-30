package com.example.hieuhoang.now.View.Store.Product;

import com.example.hieuhoang.now.Model.ObjectClass.GroupProduct;

import java.util.List;
import java.util.Map;


public interface ViewProduct {

    void loadListProductInStore(List<GroupProduct> mGroupProducts, boolean isGrid) ;
    void displayQuantityInDraftOrder (Map<String,Integer> map) ;
}
