package com.example.hieuhoang.now.Model.ObjectClass;


public class OrderDetail {

    private String idOrder ;
    private String idProduct;
    private String productName;
    private float productPrice;
    private float disCount;
    private int quantity ;
    private String note;

    public OrderDetail(){}

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public float getDisCount() {
        return disCount;
    }

    public void setDisCount(float disCount) {
        this.disCount = disCount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
