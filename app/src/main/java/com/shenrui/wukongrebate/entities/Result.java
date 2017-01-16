package com.shenrui.wukongrebate.entities;

/**
 * Created by Administrator on 2017/1/14.
 */

public class Result {
    private int code;//响应码
    private String message;//响应消息

    public Result() {
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message=" + message +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
