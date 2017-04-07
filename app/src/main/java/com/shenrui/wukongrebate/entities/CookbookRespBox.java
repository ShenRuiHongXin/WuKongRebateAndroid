package com.shenrui.wukongrebate.entities;

/**
 * Created by heikki on 2017/3/30.
 */

public class CookbookRespBox {
    private CookbookResponse respData;

    public CookbookResponse getRespData() {
        return respData;
    }

    public void setRespData(CookbookResponse respData) {
        this.respData = respData;
    }

    @Override
    public String toString() {
        return "CookbookRespBox{" +
                "respData=" + respData +
                '}';
    }
}
