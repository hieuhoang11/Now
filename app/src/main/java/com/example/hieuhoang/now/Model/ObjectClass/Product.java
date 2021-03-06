package com.example.hieuhoang.now.Model.ObjectClass;

/**
 * Created by Hieu Hoang on 27/02/2019.
 */

public class Product {
    private String id ;
    private String image;
    private String productName ;
    private int quantity ;
    private float price ;
    private float discount ;
    private long quantityPurchase ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public long getQuantityPurchase() {
        return quantityPurchase;
    }

    public void setQuantityPurchase(long quantityPurchase) {
        this.quantityPurchase = quantityPurchase;
    }
}
