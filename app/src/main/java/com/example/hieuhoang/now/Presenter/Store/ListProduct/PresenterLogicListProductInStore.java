package com.example.hieuhoang.now.Presenter.Store.ListProduct;

import android.content.Context;

import com.example.hieuhoang.now.Model.ObjectClass.GroupProduct;
import com.example.hieuhoang.now.Model.ObjectClass.Order;
import com.example.hieuhoang.now.Model.ObjectClass.OrderDetail;
import com.example.hieuhoang.now.Model.ObjectClass.Store;
import com.example.hieuhoang.now.Model.Order.ModelOrder;
import com.example.hieuhoang.now.Model.Store.Product.ModelProduct;
import com.example.hieuhoang.now.View.Store.Product.ViewProduct;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PresenterLogicListProductInStore implements IPresenterListProductInStore {
    ViewProduct viewProduct;
    ModelProduct modelProduct ;
    ModelOrder modelOrder ;
    Context context ;
    public PresenterLogicListProductInStore(ViewProduct viewProduct, Context context){
        this.viewProduct = viewProduct;
        modelProduct = new ModelProduct() ;
        modelOrder = new ModelOrder();
        this.context = context ;
    }
    @Override
    public void getListProduct(Store store) {
        if(store == null) return;
        List<GroupProduct> list = modelProduct.getListGroupProductByIDStore(store.getIdStore());
        if (list.size() > 0) {
            viewProduct.loadListProductInStore(list, modelProduct.getIsGrid(context));
        }
    }

    @Override
    public void setIsGrid(boolean b) {
        modelProduct.setIsGrid(context, b);
    }

    @Override
    public void showQuantityProductInCraftOrder(Order order) {
        if(order == null) return;
        List<OrderDetail> mList = modelOrder.getListDraftOrderDetail(order.getIdOrder()) ;
        Map<String,Integer> map = null ;
        if(mList.size() > 0) {
            map = new HashMap<>();
            for(OrderDetail detail : mList) {
                map.put(detail.getIdProduct(),detail.getQuantity()) ;
            }
        }
        viewProduct.displayQuantityInDraftOrder(map);
    }


}
