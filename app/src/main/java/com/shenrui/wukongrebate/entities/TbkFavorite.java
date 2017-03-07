package com.shenrui.wukongrebate.entities;

/**
 * 淘宝客选品库信息
 */

public class TbkFavorite {
    private int type;//选品库类型，1：普通类型，2高佣金类型
    private long favorites_id;//选品库id
    private String favorites_title;//选品组名称

    public TbkFavorite() {
    }

    public TbkFavorite(int type, long favorites_id, String favorites_title) {
        this.type = type;
        this.favorites_id = favorites_id;
        this.favorites_title = favorites_title;
    }

    @Override
    public String toString() {
        return "TbkFavorite{" +
                "type=" + type +
                ", favorites_id=" + favorites_id +
                ", favorites_title='" + favorites_title + '\'' +
                '}';
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getFavorites_id() {
        return favorites_id;
    }

    public void setFavorites_id(long favorites_id) {
        this.favorites_id = favorites_id;
    }

    public String getFavorites_title() {
        return favorites_title;
    }

    public void setFavorites_title(String favorites_title) {
        this.favorites_title = favorites_title;
    }
}
