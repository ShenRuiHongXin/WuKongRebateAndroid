package com.shenrui.wukongrebate.entities;

/**
 * Created by Administrator on 2017/1/14.
 */

public class ResponseResult {
    private Result result;
    private UserInfo userInfo;
    private UserAuths userAuths;

    public ResponseResult() {
    }

    @Override
    public String toString() {
        return "{" +
                "result=" + result +
                ", userInfo=" + userInfo +
                ", userAuths=" + userAuths +
                '}';
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserAuths getUserAuths() {
        return userAuths;
    }

    public void setUserAuths(UserAuths userAuths) {
        this.userAuths = userAuths;
    }
}
