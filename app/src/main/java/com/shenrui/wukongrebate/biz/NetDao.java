package com.shenrui.wukongrebate.biz;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.gson.Gson;
import com.shenrui.wukongrebate.contents.Constants;
import com.shenrui.wukongrebate.entities.CookbookCatRespBox;
import com.shenrui.wukongrebate.entities.CookbookRecomResp;
import com.shenrui.wukongrebate.entities.CookbookRespBox;
import com.shenrui.wukongrebate.entities.LoginInfo;
import com.shenrui.wukongrebate.entities.ResponseResult;
import com.shenrui.wukongrebate.entities.SignResponseResult;
import com.shenrui.wukongrebate.entities.UserAuths;
import com.shenrui.wukongrebate.entities.UserInfo;
import com.shenrui.wukongrebate.utils.OkHttpUtils;
import com.shenrui.wukongrebate.utils.TaobaoReqUtil;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

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

    //第三方登录
    public static void otherLogin(Context context,String loginType,String number,String nick,String icon,String password,OkHttpUtils.OnCompleteListener<ResponseResult> listener){
        UserInfo userInfo = new UserInfo();
        userInfo.setNick_name(nick);
        userInfo.setAvatar(icon);
        Gson gson = new Gson();
        String userInfoJson = gson.toJson(userInfo);
        Log.e("DeDiWang userInfo",userInfoJson);

        UserAuths userAuths = new UserAuths();
        userAuths.setIdentity_type(loginType);
        userAuths.setIdentifier(number);
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

    //用户签到
    public static void sign(Context context,UserInfo userInfo,OkHttpUtils.OnCompleteListener<SignResponseResult> listener){
        Gson gson = new Gson();
        String userInfoJson = gson.toJson(userInfo);
        OkHttpUtils<SignResponseResult> utils = new OkHttpUtils<>(context);
        utils.url(Constants.SERVICE_URL+"user_sign")
                .post()
                .addParam("userInfo",userInfoJson)
                .targetClass(SignResponseResult.class)
                .execute(listener);
    }

    //下载九块九商品
    //"http://gw.api.taobao.com/router/rest?app_key=23585348&cat=16&end_price=39&fields=num_iid,pict_url,title,zk_final_price" +
    //"&format=json&method=taobao.tbk.item.get&page_no=1&page_size=10&sign=7A11E96A7A4F4FF061C468295D9F05DF" +
    //        "&sign_method=md5&start_price=1&timestamp=2017-01-23 16:21:20&v=2.0"
    public static void downloadNineGoods(Context context,int[] cids,int pageNo,OkHttpUtils.OnCompleteListener<String> listener){
        Map<String, String> map = new HashMap<String, String>();
        map.put("fields", "num_iid,title,pict_url,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick");
        if(cids.length!=0){
            String cidString="";
            for (int integer : cids){
                if(cidString.equals("")){
                    cidString += integer;
                }else{
                    cidString = cidString+","+integer;
                }
            }
            map.put("cat", cidString);
        }else{
            map.put("q","9.9");
        }
        map.put("page_no",String.valueOf(pageNo));
        map.put("page_size", "20");
        map.put("start_price","1");
        map.put("end_price","49");
        map.put("start_tk_rate","2000");//淘宝客佣金比率20%-90%
        map.put("end_tk_rate","9000");
        String url = Constants.ROOT_URL + TaobaoReqUtil.GenerateTaobaoReqStr("taobao.tbk.item.get", map);
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.url(url)
                .targetClass(String.class)
                .execute(listener);
    }



    /**
     * 根据关键字来获取菜谱
     * @param keyword
     * @param num
     */
    public static void getFoodMenuDataByCat(Context context,String keyword,int num,OkHttpUtils.OnCompleteListener<CookbookRespBox> listener){
        OkHttpUtils<CookbookRespBox> utils = new OkHttpUtils<>(context);
        utils.url(Constants.SERVICE_DEBUG_URL+"get_cookbook_search")
                .post()
                .addParam("keyword",keyword)
                .addParam("num",String.valueOf(num))
                .transfromJson(true)
                .targetClass(CookbookRespBox.class)
                .execute(listener);
    }

    public static void getFoodMenuDataBySearch(Context context,String keyword,String classid,int num,OkHttpUtils.OnCompleteListener<CookbookRespBox> listener){
        OkHttpUtils<CookbookRespBox> utils = new OkHttpUtils<>(context);
        if (keyword != null){
            utils.url(Constants.SERVICE_DEBUG_URL+"get_cookbook_search")
                    .post()
                    .addParam("keyword",keyword)
                    .addParam("num",String.valueOf(num))
                    .transfromJson(true)
                    .targetClass(CookbookRespBox.class)
                    .execute(listener);
        }else if (classid != null){
            utils.url(Constants.SERVICE_DEBUG_URL+"get_cookbook_byclass")
                    .post()
                    .addParam("classid",classid)
                    .addParam("num",String.valueOf(num))
                    .addParam("start","0")
                    .transfromJson(true)
                    .targetClass(CookbookRespBox.class)
                    .execute(listener);
        }
    }

    /**
     * 获取菜谱页推荐内容
     * @param context
     * @param listener
     */
    public static void getCookbookRecommend(Context context,OkHttpUtils.OnCompleteListener<CookbookRecomResp> listener){
        OkHttpUtils<CookbookRecomResp> utils = new OkHttpUtils<>(context);
        utils.url(Constants.SERVICE_DEBUG_URL+"get_cookbook_recommend")
                .post()
                .transfromRecomJson(true)
                .targetClass(CookbookRecomResp.class)
                .execute(listener);
    }

    /**
     * 获取所有菜谱分类
     * @param context
     * @param listener
     */
    public static void getCookbookAllCats(Context context,OkHttpUtils.OnCompleteListener<CookbookCatRespBox> listener){
        OkHttpUtils<CookbookCatRespBox> utils = new OkHttpUtils<>(context);
        utils.url(Constants.SERVICE_DEBUG_URL+"get_cookbook_class")
                .post()
                .transfromJson(true)
                .targetClass(CookbookCatRespBox.class)
                .execute(listener);
    }
}
