package com.example.hieuhoang.now.Model.ObjectClass;

public class Store {
    int ID_Store , ID_ThuongHieu ;
    String image , storeName ,storeAddress , price , khuyenMai ;

    public Store() {
    }

    public int getID_Store() {
        return ID_Store;
    }

    public void setID_Store(int ID_Store) {
        this.ID_Store = ID_Store;
    }

    public int getID_ThuongHieu() {
        return ID_ThuongHieu;
    }

    public void setID_ThuongHieu(int ID_ThuongHieu) {
        this.ID_ThuongHieu = ID_ThuongHieu;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }
}
