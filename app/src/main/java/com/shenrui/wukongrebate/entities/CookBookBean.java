package com.shenrui.wukongrebate.entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created by heikki on 2017/3/30.
 */

public class CookBookBean implements Serializable{
    private int id;
    private int classid;
    private String name;
    private String peoplenum;
    private String preparetime;
    private String cookingtime;
    private String content;
    private String pic;
    private String tag;
    private List<CookbookBeanMaterial> material;
    private List<CookbookBeanProcess> process;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClassid() {
        return classid;
    }

    public void setClassid(int classid) {
        this.classid = classid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPeoplenum() {
        return peoplenum;
    }

    public void setPeoplenum(String peoplenum) {
        this.peoplenum = peoplenum;
    }

    public String getPreparetime() {
        return preparetime;
    }

    public void setPreparetime(String preparetime) {
        this.preparetime = preparetime;
    }

    public String getCookingtime() {
        return cookingtime;
    }

    public void setCookingtime(String cookingtime) {
        this.cookingtime = cookingtime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<CookbookBeanMaterial> getMaterial() {
        return material;
    }

    public void setMaterial(List<CookbookBeanMaterial> material) {
        this.material = material;
    }

    public List<CookbookBeanProcess> getProcess() {
        return process;
    }

    public void setProcess(List<CookbookBeanProcess> process) {
        this.process = process;
    }

    @Override
    public String toString() {
        return "CookBookBean{" +
                "id=" + id +
                ", classid=" + classid +
                ", name='" + name + '\'' +
                ", peoplenum='" + peoplenum + '\'' +
                ", preparetime='" + preparetime + '\'' +
                ", cookingtime='" + cookingtime + '\'' +
                ", content='" + content + '\'' +
                ", pic='" + pic + '\'' +
                ", tag='" + tag + '\'' +
                ", material=" + material +
                ", process=" + process +
                '}';
    }
}
