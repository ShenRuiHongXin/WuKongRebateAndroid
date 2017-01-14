package com.shenrui.wukongrebate.utils;

import android.content.SharedPreferences;

import com.shenrui.wukongrebate.contents.MyApplication;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by Administrator on 2017/1/13.
 */

public class SharedPreferenceUtils {
    private static SharedPreferenceUtils instance = new SharedPreferenceUtils();
    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;
    public static SharedPreferenceUtils getInstance(){
        sp = MyApplication.application.getSharedPreferences("profile",MODE_PRIVATE);
        editor = sp.edit();
        return instance;
    }
    public void putUserName(String value){
        editor.putString("userName",value);
        editor.commit();
    }
    public String getUserName(){
        String userName = sp.getString("userName", null);
        return userName;
    }
    public void removeUserName(){
        editor.remove("userName");
        editor.commit();
    }
    //清除缓存时调用
    public void clearAll(){
        editor.clear();
        editor.commit();
    }
}
