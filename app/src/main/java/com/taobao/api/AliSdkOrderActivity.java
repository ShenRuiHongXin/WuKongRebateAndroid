package com.taobao.api;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ali.auth.third.ui.context.CallbackContext;
import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.constants.AlibcConstants;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.AlibcTaokeParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcMyOrdersPage;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.activity.BaseActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EActivity(R.layout.activity_ali_sdk_order)
public class AliSdkOrderActivity extends BaseActivity {
    //标题栏
    @ViewsById({R.id.toolbar_left_text, R.id.toolbar_left_image, R.id.toolbar_title, R.id.toolbar_right_image})
    List<View> listTitleView;

    @ViewById(R.id.wv_my_order)
    WebView webView;

    @ViewById(R.id.ll_progressBar)
    LinearLayout progressBar;
    private Map<String,String> params;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        params = new HashMap<>();
    }
    @AfterViews
    void initView(){
        webView.getSettings().setJavaScriptEnabled(true);

        listTitleView.get(0).setVisibility(View.GONE);
        ((ImageView)listTitleView.get(1)).setImageResource(R.drawable.common_btn_back_n);
        ((TextView) listTitleView.get(2)).setText("我的订单");
        listTitleView.get(3).setVisibility(View.GONE);

        initParams();
    }

    int orderType = 0;//订单类型，默认全部订单
    boolean isAllOrder = true;//分域显示，全部显示
    private void initParams() {
        params.put(AlibcConstants.ISV_CODE,"appisvcode");

        AlibcMyOrdersPage myOrdersPage = new AlibcMyOrdersPage(orderType, isAllOrder);
        AlibcShowParams alibcShowParams = new AlibcShowParams(OpenType.H5, false);
        AlibcTrade.show(this, webView, null, null, myOrdersPage, alibcShowParams, null, params, new DemoTradeCallback());
    }

    @Click({R.id.toolbar_left_image})
    void clickEvent(View view){
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CallbackContext.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else{
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        AlibcTradeSDK.destory();
        super.onDestroy();
    }
}
