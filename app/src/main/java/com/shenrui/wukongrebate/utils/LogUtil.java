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
                    Log.d(LOGTAG,info);
                    break;
                case 2:
                    Log.e(LOGTAG,info);
                    break;
            }
        }
    }
}
