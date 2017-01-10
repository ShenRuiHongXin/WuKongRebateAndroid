package com.taobao.api;

import android.content.Intent;
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
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcDetailPage;
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

/**
 * 阿里百川电商
 * 项目名称：阿里巴巴电商SDK
 * 开发团队：阿里巴巴百川商业化团队
 * 阿里巴巴电商SDK答疑群号：1200072507
 * <p/>
 * 功能说明：WebView代理页面
 */
@EActivity(R.layout.activity_goods_detial)
public class AliSdkWebViewProxyActivity extends BaseActivity {
    //标题栏
    @ViewsById({R.id.toolbar_left_text, R.id.toolbar_left_image, R.id.toolbar_title, R.id.toolbar_right_image})
    List<View> listTitleView;

    @ViewById(R.id.wv_goods_detial)
    WebView webView;

    @ViewById(R.id.ll_progressBar)
    LinearLayout progressBar;

    private Map<String, String> exParams = new HashMap<>();

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        initParams();
//
//        String cid = getIntent().getStringExtra("cid");
//        AlibcTaokeParams alibcTaokeParams = new AlibcTaokeParams("mm_26632322_6858406_23810104", "mm_26632322_6858406_23810104", null); // 若非淘客taokeParams设置为null即可
////        AlibcBasePage alibcBasePage = new AlibcDetailPage("532128520567");
//        AlibcBasePage alibcBasePage = new AlibcDetailPage(cid);
//        AlibcShowParams alibcShowParams = new AlibcShowParams(OpenType.H5, false);
//        AlibcTrade.show(AliSdkWebViewProxyActivity.this,webView, null, null,alibcBasePage,alibcShowParams,alibcTaokeParams,exParams, new DemoTradeCallback());
//    }


    @AfterViews
    void initView() {
        webView.getSettings().setJavaScriptEnabled(true);

        listTitleView.get(0).setVisibility(View.GONE);
        ((ImageView)listTitleView.get(1)).setImageResource(R.drawable.common_btn_back_n);
        ((TextView) listTitleView.get(2)).setText("商品详情");
        listTitleView.get(3).setVisibility(View.GONE);

        initParams();
    }

    @Click(R.id.toolbar_left_image)
    void clickEvent(View view){
                finish();
    }

    private void initParams(){
        exParams.put(AlibcConstants.ISV_CODE, "appisvcode");
        exParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改

        String cid = getIntent().getStringExtra("cid");
        AlibcTaokeParams alibcTaokeParams = new AlibcTaokeParams("mm_120715455_0_0", "mm_120715455_0_0", null); // 若非淘客taokeParams设置为null即可
//        AlibcBasePage alibcBasePage = new AlibcDetailPage("532128520567");
        AlibcBasePage alibcBasePage = new AlibcDetailPage(cid);
        AlibcShowParams alibcShowParams = new AlibcShowParams(OpenType.H5, false);
        AlibcTrade.show(AliSdkWebViewProxyActivity.this,webView, null, null,alibcBasePage,alibcShowParams,alibcTaokeParams,exParams, new DemoTradeCallback());
    }

    //登录须重写onActivityResult方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        CallbackContext.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        //调用了AlibcTrade.show方法的Activity都需要调用AlibcTradeSDK.destory()
        AlibcTradeSDK.destory();
        super.onDestroy();
    }
}
