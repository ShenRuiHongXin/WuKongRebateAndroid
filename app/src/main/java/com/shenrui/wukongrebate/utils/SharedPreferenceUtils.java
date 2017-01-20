package com.shenrui.wukongrebate.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.shenrui.wukongrebate.contents.MyApplication;
import com.shenrui.wukongrebate.entities.UserAuths;
import com.shenrui.wukongrebate.entities.UserInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by Administrator on 2017/1/13.
 */

public class SharedPreferenceUtils {
    private static SharedPreferenceUtils instance;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private List<String> searthHistory = new ArrayList<>();
    public static SharedPreferenceUtils getInstance(Context context){
        if(instance == null){
            instance = new SharedPreferenceUtils(context);
        }
        return instance;
    }

    public SharedPreferenceUtils(Context context) {
        sp = context.getSharedPreferences("profile",MODE_PRIVATE);
        editor = sp.edit();
    }

    //保存用户信息
    public void putUserInfo(UserInfo userInfo){
        Gson gson = new Gson();
        String userInfoJson = gson.toJson(userInfo);
        editor.putString("userInfo",userInfoJson);
        editor.commit();
    }
    //获取用户信息
    public UserInfo getUserInfo(){
        String userInfoJson = sp.getString("userInfo", null);
        Gson gson = new Gson();
        UserInfo userInfo = gson.fromJson(userInfoJson, UserInfo.class);
        return userInfo;
    }

    //保存用户认证信息
    public void putUserAuths(UserAuths userAuths){
        Gson gson = new Gson();
        String userAuthsJson = gson.toJson(userAuths);
        editor.putString("userAuths",userAuthsJson);
        editor.commit();
    }
    //获取用户认证信息
    public UserAuths getUserAuths(){
        String userAuthsJson = sp.getString("userAuths", null);
        Gson gson = new Gson();
        UserAuths userAuths = gson.fromJson(userAuthsJson, UserAuths.class);
        return userAuths;
    }

    //保存用户搜索历史
    public void putSearthHistory(String str){
        searthHistory.add(str);
        editor.putString("searthHistory",searthHistory.toString());
        editor.commit();
    }
    public List<String> getSearthHistory(){
        String searthHistory = sp.getString("searthHistory", null);
        if(searthHistory!=null){
            String[] strs = searthHistory.split(",");
            List<String> list = Arrays.asList(strs);
            return list;
        }
        return null;
    }

    //退出帐号时调用
    public void clearAll(){
        editor.clear();
        editor.commit();
    }
}
