package com.shenrui.wukongrebate.utils;

import com.shenrui.wukongrebate.contents.Constants;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by heikki on 2017/1/5.
 */

public class TaobaoReqUtil {
    public static String GenerateTaobaoReqStr(String method,Map<String ,String> paramsMap){
        TreeMap<String, String> apiparamsMap = new TreeMap<String, String>();
        apiparamsMap.put("method",method);//API接口名称
        apiparamsMap.put("app_key", Constants.APP_KEY); //appkey
        apiparamsMap.put("format", "json");//以json数据格式返回
        apiparamsMap.put("v", "2.0");//版本
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        apiparamsMap.put("timestamp", timestamp);//时间
        apiparamsMap.put("sign_method",Constants.SIGN_METHOD_MD5);

        if (paramsMap != null){
            for (String key : paramsMap.keySet()) {
                if(key != null && !"".equals(key)){
                    apiparamsMap.put(key,paramsMap.get(key));
                }
            }
        }
        //生成签名
        String sign = null;
        try {
            sign = SignToRequest.signTopRequest(apiparamsMap, Constants.APP_SECRET, Constants.SIGN_METHOD_MD5);
        } catch (IOException e) {
            e.printStackTrace();
        }
        apiparamsMap.put("sign", sign);
        StringBuilder param = new StringBuilder();
        for (Iterator<Map.Entry<String, String>> it = apiparamsMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, String> e = it.next();
            param.append("&").append(e.getKey()).append("=").append(e.getValue());
        }
        return param.toString().substring(1);
    }
}
