package com.shenrui.wukongrebate.entities;

/**
 * Created by heikki on 2017/3/21.
 */

public class FoodFragmentBtnItem {
    private int icon;
    private String name;
    private Class jumpClass;

    public FoodFragmentBtnItem(int icon, String name, Class jumpClass) {
        this.icon = icon;
        this.name = name;
        this.jumpClass = jumpClass;
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

    public Class getJumpClass() {
        return jumpClass;
    }

    public void setJumpClass(Class jumpClass) {
        this.jumpClass = jumpClass;
    }
    @Override
    public String toString() {
        return "FoodFragmentBtnItem{" +
                "icon=" + icon +
                ", name='" + name + '\'' +
                ", jumpClass=" + jumpClass +
                '}';
    }
}
