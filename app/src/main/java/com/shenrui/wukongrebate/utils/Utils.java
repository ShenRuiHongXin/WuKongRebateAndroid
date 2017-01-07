package com.shenrui.wukongrebate.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * 通用工具类
 * Created by heikki on 2017/1/7.
 */

public class Utils {
    public static int height = -1;

    public static int getToolbarHeight(Context context, final View view) {

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int height = view.getHeight();
                int width = view.getWidth();
                LogUtil.i("view size: width:" + width + " height: " + height);
                Utils.height = height;
            }
        });

        return Utils.height;
    }

    /**
     * 判断当前是否有网络连接
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
