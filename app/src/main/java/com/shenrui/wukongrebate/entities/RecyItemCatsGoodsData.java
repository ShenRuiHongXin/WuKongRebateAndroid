package com.shenrui.wukongrebate.entities;

import java.util.List;

/**
 * Created by heikki on 2017/1/5.
 */

public class RecyItemCatsGoodsData {
    private CatsItemLocal[] catsList;  //分类数据
//    private List goodsList;   //分类商品数据

    public CatsItemLocal[] getCatsList() {
        return catsList;
    }

    public void setCatsList(CatsItemLocal[] catsList) {
        this.catsList = catsList;
    }

}
