package com.shenrui.wukongrebate.entities;

import java.util.Arrays;
import java.util.List;

/**
 * Created by heikki on 2017/1/5.
 */

//首页菜单数据
public class RebateMenuData {
    private String[] cycleList;    //广告轮播图url

    public RebateMenuData() {

    }

    public String[] getCycleList() {
        return cycleList;
    }

    public void setCycleList(String[] cycleList) {
        this.cycleList = cycleList;
    }

    @Override
    public String toString() {
        return "RebateMenuData{" +
                "cycleList=" + Arrays.toString(cycleList) +
                '}';
    }
}
