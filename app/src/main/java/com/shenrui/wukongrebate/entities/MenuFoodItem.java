package com.shenrui.wukongrebate.entities;

/**
 * Created by Administrator on 2017/3/8.
 */

public class MenuFoodItem {
    private String title;//菜名
    private int imageId;//图片id
    private String url;//菜谱链接

    public MenuFoodItem() {
    }

    @Override
    public String toString() {
        return "MenuFoodItem{" +
                "title='" + title + '\'' +
                ", imageId=" + imageId +
                ", url='" + url + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MenuFoodItem(String title, int imageId, String url) {
        this.title = title;
        this.imageId = imageId;
        this.url = url;
    }
}
