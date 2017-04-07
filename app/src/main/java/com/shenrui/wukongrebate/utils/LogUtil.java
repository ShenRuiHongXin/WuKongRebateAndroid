package com.shenrui.wukongrebate.utils;

import android.util.Log;

import com.shenrui.wukongrebate.contents.Constants;

/**
 * 日志工具
 * Created by heikki on 2017/1/7.
 */

public class LogUtil {
    private static final String LOGTAG = "heikki";

    /**
     * log.i
     * @param str
     */
    public static void i(String str){
        log(0,str);
    }

    /**
     * log.d
     * @param str
     */
    public static void d(String str){
        log(1,str);
    }

    /**
     * log.e
     * @param str
     */
    public static void e(String str){
        log(2,str);
    }

    private static void log(int level,String info){
        if (Constants.IS_DEBUG) {
            switch (level){
                case 0:
                    Log.i(LOGTAG,info);
                    break;
                case 1:
                    d(LOGTAG,info);
                    break;
                case 2:
                    Log.e(LOGTAG,info);
                    break;
            }
        }
    }

    public static void logD(String tag, String content) {
        int p = 2048;
        long length = content.length();
        if (length < p || length == p)
            Log.d(tag, content);
        else {
            while (content.length() > p) {
                String logContent = content.substring(0, p);
                content = content.replace(logContent, "");
                Log.d(tag, logContent);
            }
        }
    }

    //规定每段显示的长度
    private static int LOG_MAXLENGTH = 2000;

    public static void d(String TAG, String msg) {
        int strLength = msg.length();
        int start = 0;
        int end = LOG_MAXLENGTH;
        for (int i = 0; i < 100; i++) {
            //剩下的文本还是大于规定长度则继续重复截取并输出
            if (strLength > end) {
                Log.d(TAG + i, msg.substring(start, end));
                start = end;
                end = end + LOG_MAXLENGTH;
            } else {
                Log.d(TAG, msg.substring(start, strLength));
                break;
            }
        }
    }
}
