package com.shenrui.wukongrebate.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.contents.Constants;
import com.shenrui.wukongrebate.utils.MFGT;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by heikki on 2017/5/3.
 */

@EActivity(R.layout.activity_test)
public class DuobaoGoodDetailActivity extends BaseActivity {
    @ViewById(R.id.webView)
    WebView webView;

    @AfterViews
    void init() {
        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString("url");
        WebSettings settings = webView.getSettings();
        settings.setSupportZoom(true);          //支持缩放
        settings.setJavaScriptEnabled(true);    //启用JS脚本

        webView.loadUrl(Constants.SERVICE_DEBUG_HOST+url);
        webView.requestFocus();
    }

    @Click(R.id.iv_duo_back)
    void clickEvent(View view){
        MFGT.finish(this);
    }

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }
}
