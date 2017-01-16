package com.taobao.api;

import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.ali.auth.third.core.model.Session;
import com.alibaba.baichuan.android.trade.adapter.login.AlibcLogin;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.ResultType;
import com.alibaba.baichuan.android.trade.model.TradeResult;
import com.shenrui.wukongrebate.contents.MyApplication;
import com.shenrui.wukongrebate.entities.PaidInfoBean;
import com.shenrui.wukongrebate.entities.TestBean;
import com.shenrui.wukongrebate.utils.LogUtil;
import com.shenrui.wukongrebate.utils.OkHttpUtils;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by fenghaoxiu on 16/8/23.
 */
public class DemoTradeCallback implements AlibcTradeCallback {

    @Override
    public void onTradeSuccess(TradeResult tradeResult) {
        //当addCartPage加购成功和其他page支付成功的时候会回调

        if(tradeResult.resultType.equals(ResultType.TYPECART)){
            //加购成功
            Toast.makeText(MyApplication.application, "加购成功", Toast.LENGTH_SHORT).show();
            LogUtil.d("加购成功");
        }else if (tradeResult.resultType.equals(ResultType.TYPEPAY)){
            //支付成功
            Toast.makeText(MyApplication.application, "支付成功,成功订单号为"+tradeResult.payResult.paySuccessOrders, Toast.LENGTH_SHORT).show();
            LogUtil.d("支付成功,成功订单号为"+tradeResult.payResult.paySuccessOrders);
            //支付成功后将订单信息上传服务器
            afterPaidSuccess(tradeResult.payResult.paySuccessOrders.toString());
        }
    }

    private void afterPaidSuccess(String paidId) {
        //淘宝用户id
        Session session = AlibcLogin.getInstance().getSession();
        String str = session.toString();
        String openId = str.substring(str.indexOf("openId")+7, str.indexOf("openSid") - 2);
        //支付成功时间
        long l = System.currentTimeMillis();
        Date date = new Date(l);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        //订单信息
        PaidInfoBean bean = new PaidInfoBean(111,openId,paidId,time, Build.MODEL);
        Log.e("paidInfo",bean.toString());
        String value = bean.toString();
        //http://192.168.0.4:8080/WukongServer/paidInfo_add
        OkHttpUtils<TestBean> utils = new OkHttpUtils<>(MyApplication.application);
        utils.url("http://192.168.0.4:8080/WukongServer/paidInfo_add")
                .addParam("paidInfo",value)
                .targetClass(TestBean.class)
                .execute(new OkHttpUtils.OnCompleteListener<TestBean>() {
                    @Override
                    public void onSuccess(TestBean testBean) {

                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }

    @Override
    public void onFailure(int errCode, String errMsg) {
        Toast.makeText(MyApplication.application, "电商SDK出错,错误码="+errCode+" / 错误消息="+errMsg, Toast.LENGTH_SHORT).show();
        LogUtil.d("电商SDK出错,错误码="+errCode+" / 错误消息="+errMsg);
    }

}
