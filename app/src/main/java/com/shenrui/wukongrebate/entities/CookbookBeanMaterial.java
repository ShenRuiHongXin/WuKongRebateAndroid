package com.shenrui.wukongrebate.entities;

import java.io.Serializable;

/**
 * Created by heikki on 2017/3/30.
 */

public class CookbookBeanMaterial implements Serializable{
    private String mname;
    private int type;
    private String amount;

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CookbookBeanMaterial{" +
                "mname='" + mname + '\'' +
                ", type=" + type +
                ", amount='" + amount + '\'' +
                '}';
    }
}
