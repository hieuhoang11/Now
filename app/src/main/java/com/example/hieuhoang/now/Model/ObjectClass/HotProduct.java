package com.example.hieuhoang.now.Model.ObjectClass;

import android.graphics.Bitmap;

/**
 * Created by Hieu Hoang on 22/02/2019.
 */

public class HotProduct {

    String idStore ;
    String imageProduct ;
    String storeName ;
    String quantityDiscount ;
    String productName ;
    String oldPrice;
    String newPrice;

    public HotProduct() {

    }

    public HotProduct(String idStore, String imageProduct, String storeName, String quantityDiscount, String productName, String oldPrice, String newPrice) {
        this.idStore = idStore;
        this.imageProduct = imageProduct;
        this.storeName = storeName;
        this.quantityDiscount = quantityDiscount;
        this.productName = productName;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
    }

    public String getIdStore() {
        return idStore;
    }

    public void setIdStore(String idStore) {
        this.idStore = idStore;
    }

    public String getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(String imageProduct) {
        this.imageProduct = imageProduct;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getQuantityDiscount() {
        return quantityDiscount;
    }

    public void setQuantityDiscount(String quantityDiscount) {
        this.quantityDiscount = quantityDiscount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(String newPrice) {
        this.newPrice = newPrice;
    }
}
