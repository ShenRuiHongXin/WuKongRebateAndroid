package com.shenrui.wukongrebate.entities;

/**
 * Created by Administrator on 2017/1/14.
 */

public class UserInfo {
    private int id;//用户在数据库中的唯一标识
    private String nick_name;//用户名
    private int sex;//用户性别
    private String avatar;//头像（URL）
    private double balance;//余额
    private int integral;//积分
    private String invite_code;//邀请码
    private String regist_time;//注册时间
    public UserInfo() {
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", nick_name='" + nick_name + '\'' +
                ", sex=" + sex +
                ", avatar='" + avatar + '\'' +
                ", balance=" + balance +
                ", integral=" + integral +
                ", invite_code='" + invite_code + '\'' +
                ", regist_time='" + regist_time + '\'' +
                '}';
    }

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public String getRegist_time() {
        return regist_time;
    }

    public void setRegist_time(String regist_time) {
        this.regist_time = regist_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }
}
