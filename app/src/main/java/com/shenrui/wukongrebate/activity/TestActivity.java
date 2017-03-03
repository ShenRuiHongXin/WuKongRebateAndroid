package com.shenrui.wukongrebate.activity;

import android.app.Activity;
import android.util.Log;
import android.widget.RelativeLayout;
import com.duomai.tools.GetResolution;
import com.duomai.util.AdSize;
import com.duomai.view.AdView;
import com.shenrui.wukongrebate.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_test)
public class TestActivity extends Activity {
    /*@ViewById(R.id.webView)
    WebView webView;*/
    @ViewById(R.id.layoutAds)
    RelativeLayout layoutAds;

    @AfterViews
    void init(){
        /*WebSettings settings = webView.getSettings();
        settings.setSupportZoom(true);          //支持缩放
        settings.setJavaScriptEnabled(true);    //启用JS脚本

        webView.loadUrl(Constants.SERVICE_URL+"article.html");
        webView.requestFocus();*/

        //获取屏幕的分辨率
        int width = GetResolution.setResolution(this).getWidth();
        int height = GetResolution.setResolution(this).getHeight();
        Log.e("DeDiWang","width="+width+" height="+height);
        //调用接口
        AdSize as = GetResolution.getResolution(width, height);
        Log.e("DeDiWang","width="+as.getAdWidth()+" height="+as.getAdHeight());
        AdView adView = new AdView(this, as, true);
        layoutAds.addView(adView);//加载广告控件

    }
}
