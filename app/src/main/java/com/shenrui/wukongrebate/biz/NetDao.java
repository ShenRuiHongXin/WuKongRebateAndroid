package com.shenrui.wukongrebate.biz;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.gson.Gson;
import com.shenrui.wukongrebate.contents.Constants;
import com.shenrui.wukongrebate.entities.LoginInfo;
import com.shenrui.wukongrebate.entities.ResponseResult;
import com.shenrui.wukongrebate.entities.UserAuths;
import com.shenrui.wukongrebate.entities.UserInfo;
import com.shenrui.wukongrebate.utils.OkHttpUtils;

import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Enumeration;

import cn.smssdk.gui.layout.Res;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by Administrator on 2017/1/16.
 */

public class NetDao {
    //http://192.168.0.4:8080/WukongServer/
    //用户注册
    public static void register(Context context, String nick, String phoneNumber, String password, OkHttpUtils.OnCompleteListener<ResponseResult> listener){
        UserInfo userInfo = new UserInfo();
        userInfo.setNick_name(nick);
        long l = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(l);
        String time = format.format(date);
        userInfo.setRegist_time(time);
        final Gson gson = new Gson();
        String userInfoJson = gson.toJson(userInfo);

        UserAuths userAuths = new UserAuths();
        userAuths.setIdentity_type("phone");
        userAuths.setIdentifier(phoneNumber);
        userAuths.setCredential(password);
        String userAuthsJson = gson.toJson(userAuths);

        OkHttpUtils<ResponseResult> utils = new OkHttpUtils<>(context);
            utils.url(Constants.SERVICE_URL+"user_register")
                    .post()
                    .addParam("userInfo",userInfoJson)
                    .addParam("userAuths",userAuthsJson)
                    .targetClass(ResponseResult.class)
                    .execute(listener);
    }

    //判断用户是否已经注册
    public static void phoneNumberRegistered(Context context, String phoneNumber, OkHttpUtils.OnCompleteListener<ResponseResult> listener){
        UserInfo userInfo = new UserInfo();
        final Gson gson = new Gson();
        String userInfoJson = gson.toJson(userInfo);

        UserAuths userAuths = new UserAuths();
        userAuths.setIdentity_type("phone");
        userAuths.setIdentifier(phoneNumber);
        String userAuthsJson = gson.toJson(userAuths);

        OkHttpUtils<ResponseResult> utils = new OkHttpUtils<>(context);
        utils.url(Constants.SERVICE_URL+"user_register")
                .post()
                .addParam("userInfo",userInfoJson)
                .addParam("userAuths",userAuthsJson)
                .targetClass(ResponseResult.class)
                .execute(listener);
    }

    //用户登录
    public static void login(Context context,String phoneNumber,String password,OkHttpUtils.OnCompleteListener<ResponseResult> listener){
        UserInfo userInfo = new UserInfo();
        Gson gson = new Gson();
        String userInfoJson = gson.toJson(userInfo);

        UserAuths userAuths = new UserAuths();
        userAuths.setIdentity_type("phone");
        userAuths.setIdentifier(phoneNumber);
        userAuths.setCredential(password);
        String userAuthsJson = gson.toJson(userAuths);
        Log.e("DeDiWang userAuths",userAuthsJson);

        LoginInfo loginInfo = new LoginInfo();
        long l = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(new Date(l));
        loginInfo.setLast_login_time(time);
        loginInfo.setLast_login_device_model(Build.MODEL);
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        loginInfo.setLast_login_device_id(deviceId);
        String ipAddress = getIpAddressString();
        loginInfo.setLast_login_ip(ipAddress);
        String loginInfoJson = gson.toJson(loginInfo);
        Log.e("DeDiWang loginInfo",loginInfoJson);

        OkHttpUtils<ResponseResult> utils = new OkHttpUtils<>(context);
        utils.url(Constants.SERVICE_URL+"user_login")
                .post()
                .addParam("userInfo",userInfoJson)
                .addParam("userAuths",userAuthsJson)
                .addParam("loginInfo",loginInfoJson)
                .targetClass(ResponseResult.class)
                .execute(listener);
    }

    //获取登录ip地址
    public static String getIpAddressString() {
        try {
            for (Enumeration<NetworkInterface> enNetI = NetworkInterface
                    .getNetworkInterfaces(); enNetI.hasMoreElements(); ) {
                NetworkInterface netI = enNetI.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = netI
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }

    //修改用户基本信息
    public static void updateUserInfo(Context context, UserInfo userInfo, OkHttpUtils.OnCompleteListener<ResponseResult> listener){
        Gson gson = new Gson();
        String userInfoJson = gson.toJson(userInfo);
        OkHttpUtils<ResponseResult> utils = new OkHttpUtils<>(context);
        utils.url(Constants.SERVICE_URL+"user_update")
                .post()
                .addParam("userInfo",userInfoJson)
                .targetClass(ResponseResult.class)
                .execute(listener);
    }
    //修改用户认证信息
    public static void updateUserAuths(Context context, UserAuths userAuths, OkHttpUtils.OnCompleteListener<ResponseResult> listener){
        Gson gson = new Gson();
        String userAuthsJson = gson.toJson(userAuths);
        OkHttpUtils<ResponseResult> utils = new OkHttpUtils<>(context);
        utils.url(Constants.SERVICE_URL+"user_update")
                .post()
                .addParam("userAuths",userAuthsJson)
                .targetClass(ResponseResult.class)
                .execute(listener);
    }

    //更新用户头像
    /*public static void updateUserAvatar(Context context, UserInfo userInfo, File avatarFile, OkHttpUtils.OnCompleteListener<ResponseResult> listener){
        Gson gson = new Gson();
        String userInfoJson = gson.toJson(userInfo);
        OkHttpUtils<ResponseResult> utils = new OkHttpUtils<>(context);
        utils.url(Constants.SERVICE_URL+"user_update")
                .post()
                .addParam("userInfo",userInfoJson)
                .addFile2(avatarFile)
                .targetClass(ResponseResult.class)
                .execute(listener);
    }*/
}
