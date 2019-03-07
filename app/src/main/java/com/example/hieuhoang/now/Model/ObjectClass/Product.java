package com.example.hieuhoang.now.Model.ObjectClass;

/**
 * Created by Hieu Hoang on 27/02/2019.
 */

public class Product {
    String image;
    String nameProduct ;
    int quality ;
    float price ;
    float discount ;
    long qualityPurchase ;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public long getQualityPurchase() {
        return qualityPurchase;
    }

    public void setQualityPurchase(long qualityPurchase) {
        this.qualityPurchase = qualityPurchase;
    }
}
