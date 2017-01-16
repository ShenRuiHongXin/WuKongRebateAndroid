package com.shenrui.wukongrebate.entities;

/**
 * Created by Administrator on 2017/1/16.
 */

public class LoginInfo {
    private String last_login_time;//登录时间
    private String last_login_ip;//登录ip
    private String last_login_device_model;//设备型号
    private String last_login_device_id;//设备id

    public LoginInfo() {
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "last_login_time='" + last_login_time + '\'' +
                ", last_login_ip='" + last_login_ip + '\'' +
                ", last_login_device_model='" + last_login_device_model + '\'' +
                ", last_login_device_id='" + last_login_device_id + '\'' +
                '}';
    }

    public String getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(String last_login_time) {
        this.last_login_time = last_login_time;
    }

    public String getLast_login_ip() {
        return last_login_ip;
    }

    public void setLast_login_ip(String last_login_ip) {
        this.last_login_ip = last_login_ip;
    }

    public String getLast_login_device_model() {
        return last_login_device_model;
    }

    public void setLast_login_device_model(String last_login_device_model) {
        this.last_login_device_model = last_login_device_model;
    }

    public String getLast_login_device_id() {
        return last_login_device_id;
    }

    public void setLast_login_device_id(String last_login_device_id) {
        this.last_login_device_id = last_login_device_id;
    }
}
