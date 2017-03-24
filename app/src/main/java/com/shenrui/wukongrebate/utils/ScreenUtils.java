package com.shenrui.wukongrebate.utils;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
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
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static float getDefaultFontSize(String text){

        Paint sPaint = new Paint();
        float f=sPaint.getTextSize();
        Paint.FontMetrics sF = sPaint.getFontMetrics();
        int fontHeight = (int) Math.ceil(sF.descent - sF.top) + 2;

        Rect rect = new Rect();
        sPaint.getTextBounds(text,0,text.length(), rect);
        int height = rect.height();
        return fontHeight;
    }
}
