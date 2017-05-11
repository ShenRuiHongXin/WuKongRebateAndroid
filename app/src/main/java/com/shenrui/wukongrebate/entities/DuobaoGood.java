package com.shenrui.wukongrebate.entities;

/**
 * Created by heikki on 2017/4/15.
 */

public class DuobaoGood {
    private int id;
    private String name;
    private int img;
    private String imgUrl;
    private double price;
    private int totalTimes;
    private int curremtTimes;

    //本地商品
    private boolean beactive;
    private String deal_num;
    private String indiana_code;
    private String indiana_html;
    private String indiana_img;
    private String indiana_name;
    private String indiana_textimg;
    private String indiana_title;
    private int personNum;
    private int personSum;
    private double total_price;

    public DuobaoGood(int id, String name, int img, String imgUrl, int price, int totalTimes, int curremtTimes) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.imgUrl = imgUrl;
        this.price = price;
        this.totalTimes = totalTimes;
        this.curremtTimes = curremtTimes;
    }

    public boolean isBeactive() {
        return beactive;
    }

    public void setBeactive(boolean beactive) {
        this.beactive = beactive;
    }

    public String getDeal_num() {
        return deal_num;
    }

    public void setDeal_num(String deal_num) {
        this.deal_num = deal_num;
    }

    public String getIndiana_code() {
        return indiana_code;
    }

    public void setIndiana_code(String indiana_code) {
        this.indiana_code = indiana_code;
    }

    public String getIndiana_html() {
        return indiana_html;
    }

    public void setIndiana_html(String indiana_html) {
        this.indiana_html = indiana_html;
    }

    public String getIndiana_img() {
        return indiana_img;
    }

    public void setIndiana_img(String indiana_img) {
        this.indiana_img = indiana_img;
    }

    public String getIndiana_name() {
        return indiana_name;
    }

    public void setIndiana_name(String indiana_name) {
        this.indiana_name = indiana_name;
    }

    public String getIndiana_textimg() {
        return indiana_textimg;
    }

    public void setIndiana_textimg(String indiana_textimg) {
        this.indiana_textimg = indiana_textimg;
    }

    public String getIndiana_title() {
        return indiana_title;
    }

    public void setIndiana_title(String indiana_title) {
        this.indiana_title = indiana_title;
    }

    public int getPersonNum() {
        return personNum;
    }

    public void setPersonNum(int personNum) {
        this.personNum = personNum;
    }

    public int getPersonSum() {
        return personSum;
    }

    public void setPersonSum(int personSum) {
        this.personSum = personSum;
    }


    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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
