package com.example.hieuhoang.now.Presenter.Store;

import android.content.Context;

import com.example.hieuhoang.now.Model.LoginRegister.ModelLogin;
import com.example.hieuhoang.now.Model.ObjectClass.Account;
import com.example.hieuhoang.now.Model.ObjectClass.Order;
import com.example.hieuhoang.now.Model.ObjectClass.OrderDetail;
import com.example.hieuhoang.now.Model.ObjectClass.Product;
import com.example.hieuhoang.now.Model.ObjectClass.Store;
import com.example.hieuhoang.now.Model.Order.ModelOrder;
import com.example.hieuhoang.now.Model.Store.Product.ModelProduct;
import com.example.hieuhoang.now.Model.Store.ModelStore;
import com.example.hieuhoang.now.R;
import com.example.hieuhoang.now.View.Store.ViewStore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PresenterLogicStore implements IPresenterStore {
    private ModelStore modelStore;
    private ModelLogin modelLogin;
    private ModelOrder modelOrder;
    private ModelProduct modelProduct;
    private ViewStore viewStore;
    private Context context;

    public PresenterLogicStore(ViewStore viewStore, Context context) {
        this.viewStore = viewStore;
        modelStore = new ModelStore();
        modelLogin = new ModelLogin(context);
        modelOrder = new ModelOrder();
        modelProduct = new ModelProduct();
        this.context = context;
    }

    @Override
    public void getStoreByID(String idStore) {
        Store store = modelStore.getStoreByID(idStore);
        if (store != null) {
            viewStore.loadInformationStore(store);
        }
    }

    @Override
    public void addProductsToCart(Order order, String idStore, String idProduct, int quantity, String note) {
        if (order == null) {
            String idAccount = String.valueOf(modelLogin.getAccount().getIdAccount());
            order = modelOrder.addNewOrder(idAccount, idStore);
        }
        if (order == null) {
            return;
        }
        boolean b = modelOrder.addDetailOrder(order.getIdOrder(), idProduct, quantity, note);
        if (!b) return;

        order = modelOrder.getDraftOrderById(order.getIdOrder());
        if (order == null) return;

        viewStore.showCart(order);

        disPlayQuantityOfProductInCraftOrder(order.getIdOrder());
    }

    @Override
    public void showSheetAddToCart(Product product) {
        if (modelLogin.isLogged()) {
            viewStore.showBottomSheetAddToCart(product);
        } else {
            viewStore.startLoginActivity();
        }
    }

    @Override
    public void getDraftOrder(String idStore) {
        String idAccount = modelLogin.getAccount().getIdAccount();
        Order order = modelOrder.getDraftOrder(idStore, idAccount);
        if (order == null) {
            viewStore.onResetDraftOrderSuccess();
            viewStore.disPlayQuantityOfProductInCraftOrder(null);
            return;
        }
        viewStore.showCart(order);
    }

    @Override
    public void getOrderDetail(String idOrder) {
        List<OrderDetail> list = modelOrder.getListDraftOrderDetail(idOrder);
        int s = list.size();
        if (s > 0) {
            viewStore.showCartDetail(list);
        } else viewStore.closeCartAndCartDetail();

    }

    @Override
    public void resetOrder(String idOrder) {
        boolean b = modelOrder.deleteDraftOrder(idOrder);
        if (b) {
            viewStore.disPlayQuantityOfProductInCraftOrder(null);
            viewStore.onResetDraftOrderSuccess();
        }
    }

    @Override
    public void updateQuantityProductInOrderDetail(String idOrder, String idProduct, int quantity) {
        boolean b;
        if (quantity == 0)
            b = modelOrder.deleteDetailOrder(idOrder, idProduct);
        else b = modelOrder.updateQuantityProductInOrderDetail(idOrder, idProduct, quantity);
        if (b) {
            List<OrderDetail> list = modelOrder.getListDraftOrderDetail(idOrder);
            int s = list.size();
            if (s == 0) {
                if (modelOrder.deleteDraftOrder(idOrder)) {
                    viewStore.onResetDraftOrderSuccess();
                    viewStore.disPlayQuantityOfProductInCraftOrder(null);
                }
                return;
            }

            viewStore.showCartDetail(list);

            Order order = modelOrder.getDraftOrderById(idOrder);
            if (order != null) {
                viewStore.showCart(order);

                disPlayQuantityOfProductInCraftOrder(order.getIdOrder());
            }
        }
    }

    @Override
    public boolean isEnoughItems(String idOrder, String idProduct, int quantity) {
        int q = modelProduct.getQuantityProduct(idProduct);
        if (idOrder == null) return q >= quantity;
        int q2 = modelOrder.getQuantityProductInDraftOrder(idOrder, idProduct);
        return (q - q2) >= quantity;
    }

    @Override
    public void updateNoteDetailOrder(String idOrder, String idProduct, String note) {
        if (modelOrder.updateNoteInOrderDetail(idOrder, idProduct, note)) {
            getOrderDetail(idOrder);
        }
    }

    @Override
    public void deleteOrderDetail(String idOrder, String idProduct) {
        if (modelOrder.deleteDetailOrder(idOrder, idProduct)) {
            List<OrderDetail> list = modelOrder.getListDraftOrderDetail(idOrder);
            int s = list.size();
            if (s == 0) {
                if (modelOrder.deleteDraftOrder(idOrder)) {
                    viewStore.onResetDraftOrderSuccess();
                    viewStore.disPlayQuantityOfProductInCraftOrder(null);
                }
                return;
            }

            viewStore.showCartDetail(list);

            Order order = modelOrder.getDraftOrderById(idOrder);
            if (order != null) {
                viewStore.showCart(order);

                disPlayQuantityOfProductInCraftOrder(order.getIdOrder());
            }
        }
    }

    @Override
    public void addFavorite(String idStore) {
        if (!modelLogin.isLogged()) {
            viewStore.showMessage(context.getResources().getString(R.string.you_need_login));
            return;
        }
        Account account = modelLogin.getAccount();
        boolean b = modelStore.addFavorite(idStore, account.getIdAccount());
        if (b) viewStore.addFavoriteSuccess();
        else viewStore.showMessage(context.getResources().getString(R.string.add_favorite_fail));
    }

    @Override
    public void removeFavorite(String idStore) {
//        if (!modelLogin.isLogged()) {
//            viewStore.showMessage(context.getResources().getString(R.string.you_need_login));
//            return;
//        }
        Account account = modelLogin.getAccount();
        boolean b = modelStore.removeFavorite(idStore, account.getIdAccount());
        if (b) viewStore.removeFavoriteSuccess();
        else viewStore.showMessage(context.getResources().getString(R.string.remove_favorite_fail));
    }

    @Override
    public void checkIsFavorite(String idStore) {
        if (!modelLogin.isLogged()) {
            return ;
        }
        Account account = modelLogin.getAccount();
        boolean b = modelStore.isFavorite (idStore,account.getIdAccount()) ;
        if(b) viewStore.isFavorite();
    }

    private void disPlayQuantityOfProductInCraftOrder(String idOrder) {
        List<OrderDetail> mList = modelOrder.getListDraftOrderDetail(idOrder);
        Map<String, Integer> map = null;
        if (mList.size() > 0) {
            map = new HashMap<>();
            for (OrderDetail detail : mList) {
                map.put(detail.getIdProduct(), detail.getQuantity());
            }
        }
        viewStore.disPlayQuantityOfProductInCraftOrder(map);
    }
}
