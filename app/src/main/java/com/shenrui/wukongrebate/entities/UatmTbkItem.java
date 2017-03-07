package com.shenrui.wukongrebate.entities;

import java.util.Arrays;
import java.util.Date;

/**
 * 选品库商品信息
 */

public class UatmTbkItem {
    private long num_iid;
    private String title;
    private String pict_url;//商品主图
    //private Images small_images;//商品小图列表
    private String reserve_price;//商品一口价格
    private String zk_final_price;//商品折扣价格
    private int user_type;//卖家类型，0 集市 1 商城
    private String provcity;//宝贝所在地
    private String item_url;//商品地址
    private String click_url;//淘客地址
    private String nick;//卖家昵称
    private long seller_id;//卖家id
    private double volume;//30天销量
    private String tk_rate;//收入比例 20.00表示20%
    private String zk_final_price_wap;//无线折扣价
    private Date event_start_time;//招商活动开始时间
    private Date event_end_time;//招商活动结束时间

    public UatmTbkItem() {
    }

    /*class Images{
        private String[] string;

        public Images() {
        }

        public Images(String[] string) {
            this.string = string;
        }

        @Override
        public String toString() {
            return "{" +
                    "string=" + Arrays.toString(string) +
                    '}';
        }

        public String[] getString() {
            return string;
        }

        public void setString(String[] string) {
            this.string = string;
        }
    }
*/
    @Override
    public String toString() {
        return "UatmTbkItem{" +
                "num_iid=" + num_iid +
                ", title='" + title + '\'' +
                ", pict_url='" + pict_url + '\'' +
                //", small_images=" + small_images +
                ", reserve_price='" + reserve_price + '\'' +
                ", zk_final_price='" + zk_final_price + '\'' +
                ", user_type=" + user_type +
                ", provcity='" + provcity + '\'' +
                ", item_url='" + item_url + '\'' +
                ", click_url='" + click_url + '\'' +
                ", nick='" + nick + '\'' +
                ", seller_id=" + seller_id +
                ", volume=" + volume +
                ", tk_rate='" + tk_rate + '\'' +
                ", zk_final_price_wap='" + zk_final_price_wap + '\'' +
                ", event_start_time=" + event_start_time +
                ", event_end_time=" + event_end_time +
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

    /*public Images getSmall_images() {
        return small_images;
    }

    public void setSmall_images(Images small_images) {
        this.small_images = small_images;
    }*/

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

    public String getClick_url() {
        return click_url;
    }

    public void setClick_url(String click_url) {
        this.click_url = click_url;
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

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public String getTk_rate() {
        return tk_rate;
    }

    public void setTk_rate(String tk_rate) {
        this.tk_rate = tk_rate;
    }

    public String getZk_final_price_wap() {
        return zk_final_price_wap;
    }

    public void setZk_final_price_wap(String zk_final_price_wap) {
        this.zk_final_price_wap = zk_final_price_wap;
    }

    public Date getEvent_start_time() {
        return event_start_time;
    }

    public void setEvent_start_time(Date event_start_time) {
        this.event_start_time = event_start_time;
    }

    public Date getEvent_end_time() {
        return event_end_time;
    }

    public void setEvent_end_time(Date event_end_time) {
        this.event_end_time = event_end_time;
    }
}
