package com.shenrui.wukongrebate.entities;

import java.util.List;

/**
 * Created by heikki on 2017/1/5.
 */

public class RecyItemIndexData {
    private CatsItemLocal[] catsList;  //分类数据
    private String[] cycleList;    //广告轮播图url
    private List cjfanList; //超级返数据
    private List signList;  //签到数据
    private List activiList;    //活动数据
    private List tenList;   //早10点上新商品数据

    public CatsItemLocal[] getCatsList() {
        return catsList;
    }

    public void setCatsList(CatsItemLocal[] catsList) {
        this.catsList = catsList;
    }

    public String[] getCycleList() {
        return cycleList;
    }

    public void setCycleList(String[] cycleList) {
        this.cycleList = cycleList;
    }

    public List getCjfanList() {
        return cjfanList;
    }

    public void setCjfanList(List cjfanList) {
        this.cjfanList = cjfanList;
    }

    public List getSignList() {
        return signList;
    }

    public void setSignList(List signList) {
        this.signList = signList;
    }

    public List getActiviList() {
        return activiList;
    }

    public void setActiviList(List activiList) {
        this.activiList = activiList;
    }

    public List getTenList() {
        return tenList;
    }

    public void setTenList(List tenList) {
        this.tenList = tenList;
    }

    @Override
    public String toString() {
        return "RecyItemIndexData{" +
                "catsList=" + catsList +
                ", cycleList=" + cycleList +
                ", cjfanList=" + cjfanList +
                ", signList=" + signList +
                ", activiList=" + activiList +
                ", tenList=" + tenList +
                '}';
    }
}
