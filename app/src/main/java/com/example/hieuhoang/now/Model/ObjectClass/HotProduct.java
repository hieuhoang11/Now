package com.example.hieuhoang.now.Model.ObjectClass;

import android.graphics.Bitmap;

/**
 * Created by Hieu Hoang on 22/02/2019.
 */

public class HotProduct {

    String idStore ;
    String imageProduct ;
    String storeName ;
    String discountNumber ;
    String productName ;
    float oldPrice;
    float newPrice;

    public HotProduct() {

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

    public String getDiscountNumber() {
        return discountNumber;
    }

    public void setDiscountNumber(String discountNumber) {
        this.discountNumber = discountNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(float oldPrice) {
        this.oldPrice = oldPrice;
    }

    public float getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(float newPrice) {
        this.newPrice = newPrice;
    }
}
