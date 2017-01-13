package com.taobao.api;

import android.widget.Toast;

import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.ResultType;
import com.alibaba.baichuan.android.trade.model.TradeResult;
import com.shenrui.wukongrebate.contents.MyApplication;
import com.shenrui.wukongrebate.utils.LogUtil;

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
        }
    }

    @Override
    public void onFailure(int errCode, String errMsg) {
        Toast.makeText(MyApplication.application, "电商SDK出错,错误码="+errCode+" / 错误消息="+errMsg, Toast.LENGTH_SHORT).show();
        LogUtil.d("电商SDK出错,错误码="+errCode+" / 错误消息="+errMsg);
    }
}
