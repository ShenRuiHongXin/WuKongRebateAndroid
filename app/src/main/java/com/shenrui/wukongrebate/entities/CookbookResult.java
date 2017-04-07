package com.shenrui.wukongrebate.entities;

import java.util.List;

/**
 * Created by heikki on 2017/3/30.
 */

public class CookbookResult {
    private int num;
    private List<CookBookBean> list;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<CookBookBean> getList() {
        return list;
    }

    public void setList(List<CookBookBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "CookbookResult{" +
                "num=" + num +
                ", list=" + list +
                '}';
    }
}
