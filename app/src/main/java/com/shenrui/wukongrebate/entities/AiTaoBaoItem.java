package com.shenrui.wukongrebate.entities;

/**
 * Created by Administrator on 2017/1/23.
 */

public class AiTaoBaoItem {
    private String open_iid;//开放商品id
    private int seller_id;
    private String nick;
    private String title;//商品名称
    private String price;//商品价格
    private String item_location;
    private int seller_credit_score;
    private String pic_url;//图片url
    private String coupon_rate;
    private String coupon_price;
    private String coupon_start_time;
    private String coupon_end_time;
    private String commission_rate;//淘宝客佣金比率1234.00代表12.34%
    private String commission;//淘宝客佣金
    private String commission_num;
    private String commission_volume;
    private int volume;//30天内交易量
    private String shop_type;//店铺类型 B:商城，C:集市
    private String promotion_price;

    public AiTaoBaoItem() {
    }

    @Override
    public String toString() {
        return "AiTaoBaoItem{" +
                "open_iid=" + open_iid +
                ", seller_id=" + seller_id +
                ", nick='" + nick + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", item_location='" + item_location + '\'' +
                ", seller_credit_score=" + seller_credit_score +
                ", pic_url='" + pic_url + '\'' +
                ", coupon_rate='" + coupon_rate + '\'' +
                ", coupon_price='" + coupon_price + '\'' +
                ", coupon_start_time='" + coupon_start_time + '\'' +
                ", coupon_end_time='" + coupon_end_time + '\'' +
                ", commission_rate='" + commission_rate + '\'' +
                ", commission='" + commission + '\'' +
                ", commission_num='" + commission_num + '\'' +
                ", commission_volume='" + commission_volume + '\'' +
                ", volume=" + volume +
                ", shop_type='" + shop_type + '\'' +
                ", promotion_price='" + promotion_price + '\'' +
                '}';
    }

    public String getOpen_iid() {
        return open_iid;
    }

    public void setOpen_iid(String open_iid) {
        this.open_iid = open_iid;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public int getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(int seller_id) {
        this.seller_id = seller_id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getItem_location() {
        return item_location;
    }

    public void setItem_location(String item_location) {
        this.item_location = item_location;
    }

    public int getSeller_credit_score() {
        return seller_credit_score;
    }

    public void setSeller_credit_score(int seller_credit_score) {
        this.seller_credit_score = seller_credit_score;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getCoupon_rate() {
        return coupon_rate;
    }

    public void setCoupon_rate(String coupon_rate) {
        this.coupon_rate = coupon_rate;
    }

    public String getCoupon_price() {
        return coupon_price;
    }

    public void setCoupon_price(String coupon_price) {
        this.coupon_price = coupon_price;
    }

    public String getCoupon_start_time() {
        return coupon_start_time;
    }

    public void setCoupon_start_time(String coupon_start_time) {
        this.coupon_start_time = coupon_start_time;
    }

    public String getCoupon_end_time() {
        return coupon_end_time;
    }

    public void setCoupon_end_time(String coupon_end_time) {
        this.coupon_end_time = coupon_end_time;
    }

    public String getCommission_rate() {
        return commission_rate;
    }

    public void setCommission_rate(String commission_rate) {
        this.commission_rate = commission_rate;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getCommission_num() {
        return commission_num;
    }

    public void setCommission_num(String commission_num) {
        this.commission_num = commission_num;
    }

    public String getCommission_volume() {
        return commission_volume;
    }

    public void setCommission_volume(String commission_volume) {
        this.commission_volume = commission_volume;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getShop_type() {
        return shop_type;
    }

    public void setShop_type(String shop_type) {
        this.shop_type = shop_type;
    }

    public String getPromotion_price() {
        return promotion_price;
    }

    public void setPromotion_price(String promotion_price) {
        this.promotion_price = promotion_price;
    }
}
