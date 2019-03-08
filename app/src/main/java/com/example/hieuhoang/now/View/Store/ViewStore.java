package com.example.hieuhoang.now.View.Store;

import com.example.hieuhoang.now.Model.ObjectClass.GroupProduct;
import com.example.hieuhoang.now.Model.ObjectClass.Product;
import com.example.hieuhoang.now.Model.ObjectClass.Store;

import java.util.List;

/**
 * Created by Hieu Hoang on 27/02/2019.
 */

public interface ViewStore {
    void loadInformationStore (Store store) ;
    void loadListProduct (List<GroupProduct> mProducts,boolean isGrid) ;
    void addToCartSuccess () ;
    void showBottomSheet() ;
    void startLoginActivity() ;
    void showCart(int quality);
}
