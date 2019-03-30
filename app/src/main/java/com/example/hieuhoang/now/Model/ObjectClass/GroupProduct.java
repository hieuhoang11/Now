package com.example.hieuhoang.now.Model.ObjectClass;

import java.util.List;

public class GroupProduct {
    private int idGroup ;
    private String nameGroup ;
    private List<Product> listProducts ;

    public GroupProduct() {
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public List<Product> getListProducts() {
        return listProducts;
    }

    public void setListProducts(List<Product> listProducts) {
        this.listProducts = listProducts;
    }
}
