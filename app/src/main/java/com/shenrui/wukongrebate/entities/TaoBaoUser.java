package com.shenrui.wukongrebate.entities;

/**
 * Created by Administrator on 2017/1/13.
 */

public class TaoBaoUser {
    private String nick;
    private String ava;
    private String openId;
    private String openSid;

    public TaoBaoUser() {
    }

    @Override
    public String toString() {
        return "TaoBaoUser{" +
                "nick='" + nick + '\'' +
                ", ava='" + ava + '\'' +
                ", openId='" + openId + '\'' +
                ", openSid='" + openSid + '\'' +
                '}';
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAva() {
        return ava;
    }

    public void setAva(String ava) {
        this.ava = ava;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOpenSid() {
        return openSid;
    }

    public void setOpenSid(String openSid) {
        this.openSid = openSid;
    }
}
