package com.shenrui.wukongrebate.entities;

/**
 * Created by heikki on 2017/3/20.
 */

public class FoodContentItem {
    private int type;
    private String title;
    private int img;

    private String shopName;
    private String shopLocation;
    private double shopPrice;
    private boolean isShopPromotion;
    private boolean isShopCombo;
    private boolean isShopVip;

    public FoodContentItem(int type, String title, int img) {
        this.type = type;
        this.title = title;
        this.img = img;
    }

    public FoodContentItem(int type,int img, String shopName, String shopLocation, double shopPrice) {
        this.type = type;
        this.img = img;
        this.shopName = shopName;
        this.shopLocation = shopLocation;
        this.shopPrice = shopPrice;
    }

    public FoodContentItem(int type, String title, int img, String shopName, String shopLocation, double shopPrice) {
        this.type = type;
        this.title = title;
        this.img = img;
        this.shopName = shopName;
        this.shopLocation = shopLocation;
        this.shopPrice = shopPrice;
    }

    public FoodContentItem(int type, int img, String shopName, String shopLocation, double shopPrice, boolean isShopPromotion, boolean isShopCombo, boolean isShopVip) {
        this.type = type;
        this.img = img;
        this.shopName = shopName;
        this.shopLocation = shopLocation;
        this.shopPrice = shopPrice;
        this.isShopPromotion = isShopPromotion;
        this.isShopCombo = isShopCombo;
        this.isShopVip = isShopVip;
    }

    public boolean isShopPromotion() {
        return isShopPromotion;
    }

    public void setShopPromotion(boolean shopPromotion) {
        isShopPromotion = shopPromotion;
    }

    public boolean isShopCombo() {
        return isShopCombo;
    }

    public void setShopCombo(boolean shopCombo) {
        isShopCombo = shopCombo;
    }

    public boolean isShopVip() {
        return isShopVip;
    }

    public void setShopVip(boolean shopVip) {
        isShopVip = shopVip;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopLocation() {
        return shopLocation;
    }

    public void setShopLocation(String shopLocation) {
        this.shopLocation = shopLocation;
    }

    public double getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(double shopPrice) {
        this.shopPrice = shopPrice;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
