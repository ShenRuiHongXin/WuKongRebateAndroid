package com.shenrui.wukongrebate.contents;

import android.app.Application;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.shenrui.wukongrebate.utils.LogUtil;

/**
 * Created by heikki on 2017/1/3.
 */

public class MyApplication extends Application {
    public static MyApplication application = null;
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        AlibcTradeSDK.asyncInit(this, new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {
                //初始化成功，设置相关的全局配置参数
                LogUtil.i("init sucess");
                // ...
            }

            @Override
            public void onFailure(int code, String msg) {
                //初始化失败，可以根据code和msg判断失败原因，详情参见错误说明
                LogUtil.i("init faild"+"code: "+code+"msg: "+msg);
            }
        });
    }

}
