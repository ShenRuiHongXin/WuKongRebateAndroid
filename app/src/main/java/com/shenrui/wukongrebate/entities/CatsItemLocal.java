package com.shenrui.wukongrebate.entities;

import java.util.Arrays;

/**
 * 本地分类
 * Created by heikki on 2017/1/5.
 */

public class CatsItemLocal {
    //分类名称，用于APP首页展示
    private String name ;
    //该分类包含的淘宝分类cid
    private int[] cids;

    public CatsItemLocal(String name, int[] cids) {
        this.name = name;
        this.cids = cids;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getCids() {
        return cids;
    }

    public void setCids(int[] cids) {
        this.cids = cids;
    }

    @Override
    public String toString() {
        return "CatsItemLocal{" +
                "name='" + name + '\'' +
                ", cids=" + Arrays.toString(cids) +
                '}';
    }
}
