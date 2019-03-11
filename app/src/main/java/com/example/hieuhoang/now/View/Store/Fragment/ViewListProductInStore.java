package com.example.hieuhoang.now.View.Store.Fragment;

import com.example.hieuhoang.now.Model.ObjectClass.GroupProduct;

import java.util.List;
import java.util.Map;

/**
 * Created by Hieu Hoang on 09/03/2019.
 */

public interface ViewListProductInStore {

    void loadListProductInStore(List<GroupProduct> mGroupProducts, boolean isGrid) ;
    void showQuantityInCraftOrder (Map<String,Integer> map) ;
}
