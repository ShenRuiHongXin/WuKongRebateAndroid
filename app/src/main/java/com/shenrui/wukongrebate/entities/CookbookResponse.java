package com.shenrui.wukongrebate.entities;

/**
 * Created by heikki on 2017/3/30.
 */

public class CookbookResponse {
    private int status;
    private String msg;
    private CookbookResult result;

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

    public CookbookResult getResult() {
        return result;
    }

    public void setResult(CookbookResult result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "CookbookResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }
}
