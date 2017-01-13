package com.shenrui.wukongrebate.activity;

import android.content.Intent;

import com.ali.auth.third.core.model.Session;
import com.ali.auth.third.login.callback.LogoutCallback;
import com.alibaba.baichuan.android.trade.adapter.login.AlibcLogin;
import com.shenrui.wukongrebate.R;
import com.shenrui.wukongrebate.utils.LogUtil;
import com.taobao.api.AliSdkWebViewProxyActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

/**
 * Created by heikki on 2017/1/7.
 */

@EActivity(R.layout.activity_search)
public class SearchActivity extends BaseActivity {
    @AfterViews
    void getUser(){
        Session session = AlibcLogin.getInstance().getSession();
        LogUtil.d(session.toString());
    }

    @Click(R.id.btn_taobao_out)
    void logout(){
        AlibcLogin.getInstance().logout(this, new LogoutCallback(){
            @Override
            public void onFailure(int i, String s) {

            }

            @Override
            public void onSuccess() {

            }
        });
    }

    @Click(R.id.btn_buy_test)
    void buyTest(){
        Intent intent = new Intent(this, AliSdkWebViewProxyActivity_.class);
        intent.putExtra("num_iid","AAEUIoxbAD0bHYrQFRWgBvM9");
        startActivity(intent);
    }
}
