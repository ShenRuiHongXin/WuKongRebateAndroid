package com.shenrui.wukongrebate.entities;

import java.util.Arrays;

/**
 * Created by Administrator on 2017/2/14.
 */
//淘宝客商品
public class TbkItem {
    private long num_iid;//商品ID
    private String title;//商品标题
    private String pict_url;//商品主图
    //private String small_images;//商品小图列表
    private String reserve_price;//商品一口价格
    private String zk_final_price;//商品折扣价格
    private int user_type;//卖家类型,0表示集市，1表示商城
    private String provcity;//宝贝所在地
    private String item_url;//商品地址
    private String nick;//卖家昵称
    private long seller_id;//卖家id
    private long volume;//30天销量

    public TbkItem() {
    }

    @Override
    public String toString() {
        return "{" +
                "num_iid=" + num_iid +
                ", title='" + title + '\'' +
                ", pict_url='" + pict_url + '\'' +
                ", reserve_price='" + reserve_price + '\'' +
                ", zk_final_price='" + zk_final_price + '\'' +
                ", user_type=" + user_type +
                ", provcity='" + provcity + '\'' +
                ", item_url='" + item_url + '\'' +
                ", nick='" + nick + '\'' +
                ", seller_id=" + seller_id +
                ", volume=" + volume +
                '}';
    }

    public long getNum_iid() {
        return num_iid;
    }

    public void setNum_iid(long num_iid) {
        this.num_iid = num_iid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPict_url() {
        return pict_url;
    }

    public void setPict_url(String pict_url) {
        this.pict_url = pict_url;
    }

    public String getReserve_price() {
        return reserve_price;
    }

    public void setReserve_price(String reserve_price) {
        this.reserve_price = reserve_price;
    }

    public String getZk_final_price() {
        return zk_final_price;
    }

    public void setZk_final_price(String zk_final_price) {
        this.zk_final_price = zk_final_price;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public String getProvcity() {
        return provcity;
    }

    public void setProvcity(String provcity) {
        this.provcity = provcity;
    }

    public String getItem_url() {
        return item_url;
    }

    public void setItem_url(String item_url) {
        this.item_url = item_url;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public long getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(long seller_id) {
        this.seller_id = seller_id;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }
}
