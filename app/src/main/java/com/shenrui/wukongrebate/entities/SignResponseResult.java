package com.shenrui.wukongrebate.entities;

/**
 * Created by Administrator on 2017/1/19.
 */
//签到返回结果的实体类
public class SignResponseResult {
    private Result result;
    private UserSignInfo userSignInfo;

    public SignResponseResult() {
    }

    @Override
    public String toString() {
        return "SignResponseResult{" +
                "result=" + result +
                ", userSignInfo=" + userSignInfo +
                '}';
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public UserSignInfo getUserSignInfo() {
        return userSignInfo;
    }

    public void setUserSignInfo(UserSignInfo userSignInfo) {
        this.userSignInfo = userSignInfo;
    }
}
