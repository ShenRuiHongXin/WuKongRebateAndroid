package com.shenrui.wukongrebate;

import android.app.Application;
import android.widget.Toast;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;

/**
 * Created by Administrator on 2016/12/30.
 */

public class WuKongApplication extends Application {
    public static WuKongApplication application;
    @Override
    public void onCreate() {
        super.onCreate();

        application = this;

        AlibcTradeSDK.asyncInit(this, new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {
                //初始化成功，设置相关的全局配置参数
                Toast.makeText(WuKongApplication.this, "初始化成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int code, String msg) {
                //初始化失败，根据code和msg判断失败原因
            }
        });
    }
}
