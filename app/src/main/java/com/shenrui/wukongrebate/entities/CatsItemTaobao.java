package com.shenrui.wukongrebate.entities;

/**
 * 淘宝分类，用于解析从淘宝获取的分类json数据
 * Created by heikki on 2017/1/4.
 */

public class CatsItemTaobao {
    private int cid;
    private boolean is_parent;
    private String name;
    private int parent_cid;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public boolean is_parent() {
        return is_parent;
    }

    public void setIs_parent(boolean is_parent) {
        this.is_parent = is_parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParent_cid() {
        return parent_cid;
    }

    public void setParent_cid(int parent_cid) {
        this.parent_cid = parent_cid;
    }

    @Override
    public String toString() {
        return "CatsItemTaobao{" +
                "cid=" + cid +
                ", is_parent=" + is_parent +
                ", name='" + name + '\'' +
                ", parent_cid=" + parent_cid +
                '}';
    }
}
