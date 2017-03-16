package com.shenrui.wukongrebate.activity;

import android.app.Activity;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.shenrui.wukongrebate.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_test)
public class TestActivity extends Activity {
    @ViewById(R.id.webView)
    WebView webView;

    @AfterViews
    void init(){
        WebSettings settings = webView.getSettings();
        settings.setSupportZoom(true);          //支持缩放
        settings.setJavaScriptEnabled(true);    //启用JS脚本

        webView.loadUrl("http://192.168.0.4:8080/WKBackstageSys/page/20170314132619.html");
        webView.requestFocus();

        /*DisplayMetrics dm = getResources().getDisplayMetrics();
        float density = dm.density;
        int densityDpi = dm.densityDpi;
        String s = dm.toString();
        int widthPixels = dm.widthPixels;
        int heightPixels = dm.heightPixels;
        Log.e("DeDiWang","density "+density);
        Log.e("DeDiWang","densityDpi "+densityDpi);
        Log.e("DeDiWang","dm.toString() "+s);
        Log.e("DeDiWang","widthPixels "+widthPixels);
        Log.e("DeDiWang","heightPixels "+heightPixels);

        Display d = getWindowManager().getDefaultDisplay();
        Log.e("DeDiWang",d.toString());
        int width = d.getWidth();
        int height = d.getHeight();
        Log.e("DeDiWang",width+","+height);*/

        ViewTreeObserver vto = webView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                webView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Log.e("DeDiWang",webView.getWidth()+","+webView.getHeight());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        int height = webView.getHeight();
        Log.e("DeDiWang",height+"");
    }
    }
