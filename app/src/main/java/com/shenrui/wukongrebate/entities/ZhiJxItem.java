package com.shenrui.wukongrebate.entities;

/**
 * Created by heikki on 2017/3/18.
 */

public class ZhiJxItem {
    private int img;
    private String title;
    private String info;
    private boolean isOverseas;

    public ZhiJxItem() {
    }

    public ZhiJxItem(int img, String title, String info, boolean isOverseas) {
        this.img = img;
        this.title = title;
        this.info = info;
        this.isOverseas = isOverseas;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isOverseas() {
        return isOverseas;
    }

    public void setOverseas(boolean overseas) {
        isOverseas = overseas;
    }
}
