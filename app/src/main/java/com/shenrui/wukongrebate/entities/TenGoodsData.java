package com.shenrui.wukongrebate.entities;

/**
 * Created by heikki on 2017/1/5.
 */

public class TenGoodsData {
    private String num_iid;
    private String pict_url;
    private String title;
    private double zk_final_price;

    public String getNum_iid() {
        return num_iid;
    }

    public void setNum_iid(String num_iid) {
        this.num_iid = num_iid;
    }

    public String getPict_url() {
        return pict_url;
    }

    public void setPict_url(String pict_url) {
        this.pict_url = pict_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getZk_final_price() {
        return zk_final_price;
    }

    public void setZk_final_price(double zk_final_price) {
        this.zk_final_price = zk_final_price;
    }

    @Override
    public String toString() {
        return "TenGoodsData{" +
                "num_iid='" + num_iid + '\'' +
                ", pict_url='" + pict_url + '\'' +
                ", title='" + title + '\'' +
                ", zk_final_price=" + zk_final_price +
                '}';
    }
}
