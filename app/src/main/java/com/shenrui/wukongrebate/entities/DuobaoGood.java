package com.shenrui.wukongrebate.entities;

/**
 * Created by heikki on 2017/4/15.
 */

public class DuobaoGood {
    private int id;
    private String name;
    private int img;
    private String imgUrl;
    private int price;
    private int totalTimes;
    private int curremtTimes;

    public DuobaoGood(int id, String name, int img, String imgUrl, int price, int totalTimes, int curremtTimes) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.imgUrl = imgUrl;
        this.price = price;
        this.totalTimes = totalTimes;
        this.curremtTimes = curremtTimes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotalTimes() {
        return totalTimes;
    }

    public void setTotalTimes(int totalTimes) {
        this.totalTimes = totalTimes;
    }

    public int getCurremtTimes() {
        return curremtTimes;
    }

    public void setCurremtTimes(int curremtTimes) {
        this.curremtTimes = curremtTimes;
    }
}
