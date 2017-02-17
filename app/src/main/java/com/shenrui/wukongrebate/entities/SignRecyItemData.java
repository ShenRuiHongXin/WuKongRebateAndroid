package com.shenrui.wukongrebate.entities;

/**
 * Created by heikki on 2017/1/4.
 */

public class SignRecyItemData<T> {
    //签到页面
    public static final int ITEM_USER = 0;
    public static final int ITEM_SPLITLINE = 1;
    public static final int ITEM_TASK = 2;
    //分类栏
    public static final int CATS = 999;
    //积分兑换商品
    public static final int JF_EXCHANG = 998;
    //item首页
    public static final int MAIN_ITEM = 1000;
    //10点上新
    public static final int TEN_NEW = 10;
    //content首页
    public static final int MAIN_CONTENT = 1002;
    //分类商品
    public static final int CATS_GOODS_ITEM = 1001;

    //用来装载不同类型的item数据bean
    T t;
    //item数据bean的类型
    int dataType;

    public SignRecyItemData(T t, int dataType) {
        this.t = t;
        this.dataType = dataType;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }
}
