package com.example.hieuhoang.now.Model.ObjectClass;

/**
 * Created by Hieu Hoang on 08/03/2019.
 */

public class Order {
    String idOrder;
    String idStore;
    String idCustomer;

    public Order() {
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getIdStore() {
        return idStore;
    }

    public void setIdStore(String idStore) {
        this.idStore = idStore;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }
}
