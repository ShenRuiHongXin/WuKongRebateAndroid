package com.shenrui.wukongrebate.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.shenrui.wukongrebate.R;


/**
 * Created by Administrator on 2017/2/18.
 * 用于activity跳转，finish的工具类
 */

public class MFGT {

    public static void startActivity(Context context, Class<?> cls, Bundle bundle){
        Intent intent = new Intent();
        intent.setClass(context,cls);
        if (bundle != null){
            intent.putExtras(bundle);
        }
        startActivity(context,intent);
    }

    public static void startActivity(Context context,Class<?> cls){
        startActivity(context,cls,null);
    }

    //activity从屏幕右边进入
    public static void startActivity(Context context,Intent intent){
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.in_from_right,R.anim.out_from_left);
    }

    //activity退出时从屏幕右边退出
    public static void finish(Activity activity){
        if (Build.VERSION.SDK_INT <= 19){
            activity.finish();
            activity.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
        }else{
            activity.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
            activity.finish();
        }
    }
}
