package com.ecom.khawadawa_main;

public class ModelShop {
    private String uid1,shopName,vendorMail,vendorPhone;

    public ModelShop() {
    }

    public ModelShop(String uid, String shopName, String vendorMail, String vendorPhone) {
        this.uid1 = uid;
        this.shopName = shopName;
        this.vendorMail = vendorMail;
        this.vendorPhone = vendorPhone;
    }

    public String getUid1() {
        return uid1;
    }

    public void setUid1(String uid) {
        this.uid1 = uid;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getVendorMail() {
        return vendorMail;
    }

    public void setVendorMail(String vendorMail) {
        this.vendorMail = vendorMail;
    }

    public String getVendorPhone() {
        return vendorPhone;
    }

    public void setVendorPhone(String vendorPhone) {
        this.vendorPhone = vendorPhone;
    }
}
