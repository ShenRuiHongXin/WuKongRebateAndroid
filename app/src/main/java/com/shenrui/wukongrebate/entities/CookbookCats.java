package com.shenrui.wukongrebate.entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created by heikki on 2017/4/1.
 */

public class CookbookCats implements Serializable{
    private int classid;
    private String name;
    private int parentid;
    private List<CookbookCats> list;


    private int icon;
    private int icon_select;

    public CookbookCats(String name) {
        this.name = name;
    }

    public CookbookCats(int classid,int icon, int icon_select, String name) {
        this.classid = classid;
        this.icon = icon;
        this.icon_select = icon_select;
        this.name = name;
    }

    public int getClassid() {
        return classid;
    }

    public void setClassid(int classid) {
        this.classid = classid;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public List<CookbookCats> getList() {
        return list;
    }

    public void setList(List<CookbookCats> list) {
        this.list = list;
    }

    public int getIcon_select() {
        return icon_select;
    }

    public void setIcon_select(int icon_select) {
        this.icon_select = icon_select;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
