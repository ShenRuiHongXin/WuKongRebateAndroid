package com.shenrui.wukongrebate.entities;

/**
 * Created by heikki on 2017/2/14.
 */

public class JfExchangeGoods {
    private String name;
    private int img_url;
    private int price;

    public JfExchangeGoods(String name, int img_url, int price) {
        this.name = name;
        this.img_url = img_url;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg_url() {
        return img_url;
    }

    public void setImg_url(int img_url) {
        this.img_url = img_url;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
