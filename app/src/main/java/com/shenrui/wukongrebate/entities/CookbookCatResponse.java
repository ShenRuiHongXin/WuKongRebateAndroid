package com.shenrui.wukongrebate.entities;

import java.util.List;

/**
 * Created by heikki on 2017/4/1.
 */

public class CookbookCatResponse {
    private int status;
    private String msg;
    private List<CookbookCats> result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<CookbookCats> getResult() {
        return result;
    }

    public void setResult(List<CookbookCats> result) {
        this.result = result;
    }
}
