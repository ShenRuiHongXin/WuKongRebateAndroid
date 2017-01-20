package com.shenrui.wukongrebate.entities;

/**
 * Created by Administrator on 2017/1/19.
 */

//用户签到信息
public class UserSignInfo {
    private int id;
    private int user_id;
    private String last_sign_time;//最近一次签到时间
    private int continuity_sign_days;//连续签到天数

    public UserSignInfo() {
    }

    @Override
    public String toString() {
        return "UserSignInfo{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", last_sign_time='" + last_sign_time + '\'' +
                ", continuity_sign_days=" + continuity_sign_days +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getLast_sign_time() {
        return last_sign_time;
    }

    public void setLast_sign_time(String last_sign_time) {
        this.last_sign_time = last_sign_time;
    }

    public int getContinuity_sign_days() {
        return continuity_sign_days;
    }

    public void setContinuity_sign_days(int continuity_sign_days) {
        this.continuity_sign_days = continuity_sign_days;
    }
}
