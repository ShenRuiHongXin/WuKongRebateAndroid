package com.shenrui.wukongrebate.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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
    private static final String KEY_SEARCH_HISTORY = "searchHistory";
    private static final String KEY_NINE_SEARCH_HISTORY = "nine_search_history";
    private static final String KEY_SUPER_SEARCH_HISTORY = "super_search_history";
    private static final String CURRENT_CITY = "currentCity";
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
    public void putSearthHistory(String q){
        String newStr = q.trim();
        String oldStr = sp.getString(KEY_SEARCH_HISTORY, null);
        if(oldStr==null){
            editor.putString(KEY_SEARCH_HISTORY,newStr);
            editor.commit();
        }else{
            if (!oldStr.contains(newStr)){
                editor.putString(KEY_SEARCH_HISTORY,oldStr.trim()+","+newStr);
                editor.commit();
            }
        }
    }
    public List<String> getSearthHistory(){
        String searthHistory = sp.getString(KEY_SEARCH_HISTORY, null);
        if(searthHistory!=null){
            List<String> strs = new ArrayList<>();
            String[] split = searthHistory.split(",");
            for(int i=split.length-1;i>=0;i--){
                strs.add(split[i]);
            }
            return strs;
        }
        return null;
    }
    //清除一条记录
    public void clearOneHistory(String one){
        String searthHistory = sp.getString(KEY_SEARCH_HISTORY, null);
        if(searthHistory!=null){
            clearAllHistory();
            for(String str : searthHistory.split(",")){
                if(!str.equals(one)){
                    putSearthHistory(str);
                }
            }
        }
    }
    public void clearAllHistory(){
        editor.remove(KEY_SEARCH_HISTORY);
        editor.commit();
    }
    //保存当前城市
    public void putCurrentCity(String city){
        editor.putString(CURRENT_CITY,city);
        editor.commit();
    }
    public String getCurrentCity(){
        return sp.getString(CURRENT_CITY,null);
    }

    //保存九块九搜索历史关键词
    public void putNineSearchHistory(String q){
        String newStr = q.trim();
        String oldStr = sp.getString(KEY_NINE_SEARCH_HISTORY, null);
        if(oldStr==null){
            editor.putString(KEY_NINE_SEARCH_HISTORY,newStr);
            editor.commit();
        }else{
            if (!oldStr.contains(newStr)){
                editor.putString(KEY_NINE_SEARCH_HISTORY,oldStr.trim()+","+newStr);
                editor.commit();
            }
        }
    }
    //得到九块九搜索历史
    public List<String> getNineSearchHistory(){
        String str = sp.getString(KEY_NINE_SEARCH_HISTORY, null);
        if (str!=null){
            List<String> list = new ArrayList<>();
            String[] split = str.split(",");
            for(int i=split.length-1;i>=0;i--){
                list.add(split[i]);
            }
            return list;
        }
        return null;
    }
    //清除九块九搜索历史
    public void clearNineSearchHistory(){
        editor.remove(KEY_NINE_SEARCH_HISTORY);
        editor.commit();
    }
    //保存超级返搜索历史关键词
    public void putSuperSearchHistory(String q){
        String newStr = q.trim();
        String oldStr = sp.getString(KEY_SUPER_SEARCH_HISTORY, null);
        if(oldStr==null){
            editor.putString(KEY_SUPER_SEARCH_HISTORY,newStr);
            editor.commit();
        }else{
            if (!oldStr.contains(newStr)){
                editor.putString(KEY_SUPER_SEARCH_HISTORY,oldStr.trim()+","+newStr);
                editor.commit();
            }
        }
    }
    //得到超级返搜索历史
    public List<String> getSuperSearchHistory(){
        String str = sp.getString(KEY_SUPER_SEARCH_HISTORY, null);
        if (str!=null){
            List<String> list = new ArrayList<>();
            String[] split = str.split(",");
            for(int i=split.length-1;i>=0;i--){
                list.add(split[i]);
            }
            return list;
        }
        return null;
    }
    //清除超级返搜索历史
    public void clearSuperSearchHistory(){
        editor.remove(KEY_SUPER_SEARCH_HISTORY);
        editor.commit();
    }
    //退出帐号时调用
    public void clearAll(){
        editor.clear();
        editor.commit();
    }
}
