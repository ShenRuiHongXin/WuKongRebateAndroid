package com.taobao.api;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ali.auth.third.ui.context.CallbackContext;
import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.constants.AlibcConstants;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.AlibcTaokeParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.model.TradeResult;
import com.alibaba.baichuan.android.trade.page.AlibcMyCartsPage;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.activity.BaseActivity;
import com.shenrui.wukongrebate.utils.MFGT;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EActivity(R.layout.activity_ali_sdk_my_cart)
public class AliSdkMyCartActivity extends BaseActivity {
    @ViewsById({R.id.toolbar_left_text, R.id.toolbar_left_image, R.id.toolbar_title, R.id.toolbar_right_image})
    List<View> listTitleView;

    @ViewById(R.id.wv_my_cart)
    WebView webView;
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
        ((TextView) listTitleView.get(2)).setText("购物车");
        listTitleView.get(3).setVisibility(View.GONE);

        showCartPage();
    }

    private void showCartPage() {
        params.put(AlibcConstants.ISV_CODE,"appisvcode");
        AlibcMyCartsPage cartsPage = new AlibcMyCartsPage();
        AlibcShowParams showParams = new AlibcShowParams(OpenType.H5,false);
        AlibcTrade.show(this, webView, null, null, cartsPage, showParams,null, params, new DemoTradeCallback());
    }
    @Click({R.id.toolbar_left_image})
    void clickEvent(View view){
        MFGT.finish(this);
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
            MFGT.finish(this);
        }
    }

    @Override
    protected void onDestroy() {
        AlibcTradeSDK.destory();
        super.onDestroy();
    }
}
