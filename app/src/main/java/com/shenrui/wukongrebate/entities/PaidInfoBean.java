package com.shenrui.wukongrebate.entities;

/**
 * Created by Administrator on 2017/1/13.
 */

public class PaidInfoBean {
    private int user_id;//用户id
    private String tb_account_id;//淘宝用户id
    private String tb_trade_id;//订单号
    private String paid_time;//支付时间
    private String device_model;//设备型号

    public PaidInfoBean() {
    }

    public PaidInfoBean(int user_id, String tb_account_id, String tb_trade_id, String paid_time, String device_model) {
        this.user_id = user_id;
        this.tb_account_id = tb_account_id;
        this.tb_trade_id = tb_trade_id;
        this.paid_time = paid_time;
        this.device_model = device_model;
    }

    @Override
    public String toString() {
        return "{" +
                "user_id=" + user_id +
                ", tb_account_id='" + tb_account_id + '\'' +
                ", tb_trade_id='" + tb_trade_id + '\'' +
                ", paid_time='" + paid_time + '\'' +
                ", device_model='" + device_model + '\'' +
                '}';
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTb_account_id() {
        return tb_account_id;
    }

    public void setTb_account_id(String tb_account_id) {
        this.tb_account_id = tb_account_id;
    }

    public String getTb_trade_id() {
        return tb_trade_id;
    }

    public void setTb_trade_id(String tb_trade_id) {
        this.tb_trade_id = tb_trade_id;
    }

    public String getPaid_time() {
        return paid_time;
    }

    public void setPaid_time(String paid_time) {
        this.paid_time = paid_time;
    }

    public String getDevice_model() {
        return device_model;
    }

    public void setDevice_model(String device_model) {
        this.device_model = device_model;
    }
}
