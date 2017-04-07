package com.shenrui.wukongrebate.entities;

import java.io.Serializable;

/**
 * Created by heikki on 2017/3/30.
 */

public class CookbookBeanProcess implements Serializable{
    private String pcontent;
    private String pic;

    public String getPcontent() {
        return pcontent;
    }

    public void setPcontent(String pcontent) {
        this.pcontent = pcontent;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "CookbookBeanProcess{" +
                "pcontent='" + pcontent + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }
}
