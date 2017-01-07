package com.shenrui.wukongrebate.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by heikki on 2017/1/5.
 */

public class ScreenUtils {

    /**
     * 获取手机屏幕宽度 px
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm2 = context.getResources().getDisplayMetrics();

//        LogUtil.i("ScreenUtils width * height: " + dm2.widthPixels + "x" +dm2.heightPixels);
        return dm2.widthPixels;

    }

    /**
     * 获取手机屏幕高度 px
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm2 = context.getResources().getDisplayMetrics();
        return dm2.heightPixels;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
