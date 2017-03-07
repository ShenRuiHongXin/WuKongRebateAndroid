package com.shenrui.wukongrebate.activity;

import android.app.Activity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.contents.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_test)
public class TestActivity extends Activity {
    @ViewById(R.id.webView)
    WebView webView;
    @ViewById(R.id.layoutAds)
    RelativeLayout layoutAds;

    @AfterViews
    void init(){
        WebSettings settings = webView.getSettings();
        settings.setSupportZoom(true);          //支持缩放
        settings.setJavaScriptEnabled(true);    //启用JS脚本

        webView.loadUrl(Constants.SERVICE_URL+"article.html");
        webView.requestFocus();

    }
}
